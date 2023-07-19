package cl.security.status.strategy.deal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.DealDao;
import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;
import cl.security.mdd.enums.KGRStatusValueEnum;
import cl.security.mdd.retries.RetryLogic;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.quartz.scheduler.CheckJob;
import cl.security.status.state.KGRStatusState;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;
import cl.security.utils.Constants;

public class ApplicationStatus implements Runnable {

	static int count = 0;
	private Map<Integer, String> numToWord = new HashMap<>();
	private StatusStrategy strategy = null;
	private Params p;
	RetryLogic retryLogic;

	public ApplicationStatus process(StatusStrategy strategy, CheckMessagesDB checkMessages) {
		this.strategy = strategy;
		this.p = checkMessages.getParams();

		numToWord.put(0, "ZERO");
		numToWord.put(1, "ONE");
		numToWord.put(2, "TWO");
		numToWord.put(3, "THREE");
		numToWord.put(4, "FOUR");

		return this;
	}

	@Override
	public void run() {

		if (strategy instanceof KondorStatus) {

			try {
				DealDao.loadDeals();
			} catch (SQLException e1) {
			}

			Thread dealSetThread = new Thread(dealSetProcess());
//			Thread removeDealsThread = new Thread(removeDealFromSet(dealSetThread));

			dealSetThread.start();
//			removeDealsThread.start();

		} else {

			// Reparo puede ser N o R
			String reparo = strategy.statusFromCustomWindow(p);

			strategy.acceptanceLogger(p);

			Repair repair = strategy instanceof KGRStatus ? new RepairKGR().build(p, reparo)
					: new RepairMLS().build(p, reparo);

			RepairEnum.valueOf(reparo).queryUpdateRepair(repair);

			System.out.println("Ejecutando " + strategy.toString());
		}

	}

	private Runnable dealSetProcess() {

		return () -> {

			Set<Deal> processedDealSet = new HashSet<>();

			DealDao.dealSet.forEach(deal -> {

				strategy = CheckJob.status.get(Constants.MLS);

				int mlsStatusValue = strategy.getStatus(deal);

				retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 1);

				while (retryLogic.shouldRetry()) {
					retryLogic.retryImpl(() -> {

						boolean statusReadyMLS = strategy.getStatusReady(deal);

						// Loop de 6 reintentos hasta que el statusReady sea true
						if (statusReadyMLS) {

							strategy = CheckJob.status.get(Constants.KRG);

							String krgStatusValue = numToWord.get(strategy.getStatus(deal));

							KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(krgStatusValue)
									.setState(mlsStatusValue, deal);

							kgrStatusState.executeUpdates(KGRStatusValueEnum.valueOf(krgStatusValue).num);

							processedDealSet.add(deal);

						}
						retryLogic.dealReties += 1;
						;

//						System.out.println(deal.getDealId() + " .");

					});
				}

				if (!retryLogic.shouldRetry()) {
					System.out.println(String.format("A borrar %s", deal.getDealId()));
					// Despues de los 6 intentos se elimina objeto de la lista
					processedDealSet.add(deal);

				}

			});

			System.out.println("Borrados");
			DealDao.processedDealSet.forEach(System.out::println);
			
			retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 1);
			
			try {
				removeDealsFromSet(processedDealSet);
			} catch (Exception e) {
				retryLogic.retryImpl(() -> {
					removeDealsFromSet(processedDealSet);
				});
			}
			

		};
	}
	
	private void removeDealsFromSet(Set<Deal> processedDealSet) {
		DealDao.dealSet.removeAll(processedDealSet);
	}

//	private Runnable removeDealFromSet(Thread t) {
//		return () -> {
//			while (true) {
//				if (!t.isAlive()) {
//					DealDao.processedDealSet.forEach(d -> {
//						DealDao.dealSet.remove(d);
//					});
//				}
//			}
//		};
//	}

}
