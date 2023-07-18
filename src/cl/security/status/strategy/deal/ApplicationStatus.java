package cl.security.status.strategy.deal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.DealDao;
import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;
import cl.security.mdd.enums.KGRStatusValueEnum;
import cl.security.mdd.retries.RetryLogic;
import cl.security.model.Params;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.state.KGRStatusState;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;
import cl.security.utils.Constants;

public class ApplicationStatus implements Runnable {

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

			DealDao.dealSet.forEach(deal -> {

				int mlsStatusValue = strategy.getStatus(deal);

				retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), deal.getRetries() * 1000,
						deal.getRetries());

				retryLogic.retryImpl(() -> {

					boolean statusReadyMLS = false;
					
					// Loop de 6 reintentos hasta que el statusReady sea true
					if (statusReadyMLS) {
						String krgStatusValue = numToWord.get(strategy.getStatus(deal));

						KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(krgStatusValue)
								.setState(mlsStatusValue, deal);

						kgrStatusState.executeUpdates(KGRStatusValueEnum.valueOf(krgStatusValue).num);

						deal.setRetries(Integer.valueOf(Constants.RETRIES) + 1);

						DealDao.dealSet.remove(deal);
//						DealDao.processedDealSet.add(deal);

					}

				});
				// Despues de los 6 intentos se elimina objeto de la lista
				DealDao.dealSet.remove(deal);
			});

		};
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
