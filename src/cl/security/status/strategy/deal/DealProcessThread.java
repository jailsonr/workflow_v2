package cl.security.status.strategy.deal;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cl.security.mdd.dao.DealDao;
import cl.security.mdd.enums.KGRStatusValueEnum;
import cl.security.mdd.retries.RetryLogic;
import cl.security.model.Deal;
import cl.security.quartz.scheduler.CheckJob;
import cl.security.status.state.KGRStatusState;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.Constants;

public class DealProcessThread implements Runnable {

	StatusStrategy strategy;
	Deal deal;
	private Map<Integer, String> numToWord;

	private void removeDealsFromSet(Set<Deal> processedDealSet) {

		DealDao.dealSet.removeAll(processedDealSet);
		processedDealSet.forEach(e -> System.out.println("Se eliminó deal: " + e.getDealId()));

	}

	public DealProcessThread(StatusStrategy strategy, Deal deal,
			Map<Integer, String> numToWord) {

		this.strategy = strategy;
		this.deal = deal;
		this.numToWord = numToWord;

	}
	
//	public static void main(String[] args) {
//		RetryLogic retryLogic = new RetryLogic(6, 5000, 0);
//		
//		while (retryLogic.shouldRetry()) {
//			System.out.println("Inicio");
//			
//			retryLogic.retryImpl(() -> {
//				int a = new Random().nextInt();
//				System.out.println("a " + a);
//				for (int i = 1; i<200;i++) {
//					if (a % 2 == 0) {
//						retryLogic.stopExecution();
//						System.out.println("Deberia detenerse");
//						System.out.println("Se ejecutó " + i + "x");
//						//break;
//					}
//				}
//			});
//			
//			
//		}
//	}

	@Override
	public void run() {

		Set<Deal> processedDealSet = new HashSet<>();

		strategy = CheckJob.status.get(Constants.MLS);

		int mlsStatusValue = strategy.getStatus(deal);

		RetryLogic retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 5000, deal.getRetries());

		while (retryLogic.shouldRetry()) {
			
			retryLogic.retryImpl(r -> {

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
						r.stopExecution();
						System.out.println("Detiene Reintentos");
						System.out.println("Deal: " + deal.getDealId() + " Ya se ejecutó");
//						System.out.println(String.format("Deal %s VAMOS A REINTENTAR", deal.getDealId()));
					} else {
						System.out.println("Retry Attemps: " + r.getRetryAttempts() + " Deal: "
								+ deal.getDealId() + " todavia se ejecuta");

						if (r.getRetryAttempts() == 0) {
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

//		if (!retryLogic.shouldRetry()) {
//			System.out.println(String.format("A borrar %s de la pila", deal.getDealId()));
//			// Despues de los 6 intentos se elimina objeto de la lista
//			processedDealSet.add(deal);
//
//			strategy = CheckJob.status.get(Constants.KONDOR);
//			strategy.updateStatusDealList(deal);
//
//		}

		retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 3000, 1);

		try {

			removeDealsFromSet(processedDealSet);

		} catch (Exception e) {

			retryLogic.retryImpl(r -> {
				removeDealsFromSet(processedDealSet);
			});

		}

	}

}
