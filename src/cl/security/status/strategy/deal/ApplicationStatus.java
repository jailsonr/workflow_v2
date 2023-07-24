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

	public ApplicationStatus process(StatusStrategy strategy, Params p) {
		this.strategy = strategy;
		this.p = p;

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

//			System.out.println("Ejecutando " + strategy.toString());
		}

	}

	private Runnable dealSetProcess() {

		return () -> {

			DealDao.dealSet.forEach(deal -> {

				myThread mThread = new myThread(strategy, deal, retryLogic, numToWord);

				new Thread(mThread).start();

			});

//			System.out.println("Borrados");
//			processedDealSet.forEach(System.out::println);

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

class myThread implements Runnable {

	StatusStrategy strategy;
	Deal deal;
	RetryLogic retryLogic;
	private Map<Integer, String> numToWord;

	private void removeDealsFromSet(Set<Deal> processedDealSet) {
		DealDao.dealSet.removeAll(processedDealSet);
		processedDealSet.forEach(e -> System.out.println("Se eliminó deal: " + e.getDealId()));
	}

	public myThread(StatusStrategy strategy, Deal deal, RetryLogic retryLogic, Map<Integer, String> numToWord) {
		super();
		this.strategy = strategy;
		this.deal = deal;
		this.retryLogic = retryLogic;
		this.numToWord = numToWord;
	}

	@Override
	public void run() {
		
		Set<Deal> processedDealSet = new HashSet<>();

		strategy = CheckJob.status.get(Constants.MLS);

		int mlsStatusValue = strategy.getStatus(deal);

		retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 5000, deal.getRetries());

		while (retryLogic.shouldRetry()) {
			retryLogic.retryImpl(() -> {

				boolean statusReadyMLS = strategy.getStatusReady(deal);

//				System.out.println(String.format("Deal %s con retries ", deal.getDealId(), retryLogic.retryAttempts));
				// Loop de 6 reintentos hasta que el statusReady sea true
				if (statusReadyMLS) {

					strategy = CheckJob.status.get(Constants.KRG);

					String krgStatusValue = numToWord.get(strategy.getStatus(deal));

					KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(krgStatusValue).setState(mlsStatusValue,
							deal);

					int krgStatusValueInt = KGRStatusValueEnum.valueOf(krgStatusValue).num;

					System.out.println("KGR Status = " + krgStatusValueInt + " | MLS Status = " + mlsStatusValue
							+ " para deal " + deal.getDealId());

					kgrStatusState.executeUpdates(krgStatusValueInt);

					if (krgStatusValueInt >= 2) {
						retryLogic.stopExecution();
						System.out.println("Detiene Reintentos");
						System.out.println("Deal: " + deal.getDealId() + " Ya se ejecutó");
//						System.out.println(String.format("Deal %s VAMOS A REINTENTAR", deal.getDealId()));
					} else {
						System.out.println("Retry Attemps: " + retryLogic.getRetryAttempts() + " Deal: "
								+ deal.getDealId() + " todavia se ejecuta");

						if (retryLogic.getRetryAttempts() == 0) {
							// ACTUALIZAR DEAL A P EN LA BD
							strategy = CheckJob.status.get(Constants.KONDOR);
							strategy.updateStatusDealList(deal);
						}

//						System.out.println(String.format("Deal %s ya ha ejecutado", deal.getDealId()));
					}

					processedDealSet.add(deal);
				}

//				retryLogic.dealReties = retryLogic.dealReties + 1;

//				System.out.println(deal.getDealId() + " .");

			});
		}

		if (!retryLogic.shouldRetry()) {
			System.out.println(String.format("A borrar %s de la pila", deal.getDealId()));
			// Despues de los 6 intentos se elimina objeto de la lista
			processedDealSet.add(deal);

		}

		retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 3000, 1);

		try {
			removeDealsFromSet(processedDealSet);
		} catch (Exception e) {
			retryLogic.retryImpl(() -> {
				removeDealsFromSet(processedDealSet);
			});
		}

//		System.out.println("SÍ SE TERMINÓ");

	}

}