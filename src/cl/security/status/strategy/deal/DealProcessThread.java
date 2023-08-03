package cl.security.status.strategy.deal;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cl.security.mdd.enums.KGRStatusValueEnum;
import cl.security.mdd.retries.RetryLogic;
import cl.security.model.Deal;
import cl.security.quartz.scheduler.CheckJob;
import cl.security.status.state.KGRStatusState;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.Constants;

public class DealProcessThread implements Runnable {

	Logger log = Logger.getLogger(DealProcessThread.class);
	StatusStrategy strategy = CheckJob.status.get(Constants.MLS);
	Deal deal;
	private Map<Integer, String> numToWord;

	private void removeDealsFromSet(Set<Deal> processedDealSet) {

		processedDealSet.forEach(e -> System.out.println("Se eliminó deal: " + e.getDealId()));

	}

	public DealProcessThread(Deal deal, Map<Integer, String> numToWord) {

		this.deal = deal;
		this.numToWord = numToWord;

	}

	@Override
	public void run() {

		Set<Deal> processedDealSet = new HashSet<>();

		int mlsStatusValue = strategy.getStatus(deal);

		RetryLogic retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), 5000, deal.getRetries());

		while (retryLogic.shouldRetry()) {

			retryLogic.retryImpl(r -> {
				// boolean statusReadyMLS = strategy.getStatusReady(deal);
				if (true) {

					strategy = CheckJob.status.get(Constants.KRG);
					int kgrStatusInt = strategy.getStatus(deal);
					log.debug("KGRSTATUS de la BD es: " + kgrStatusInt);

					String krgStatusValue = numToWord.get(kgrStatusInt);

					KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(krgStatusValue).setState(mlsStatusValue,
							deal);

					int krgStatusValueInt = KGRStatusValueEnum.valueOf(krgStatusValue).num;

					System.out.println("KGR Status = " + krgStatusValueInt + " | MLS Status = " + mlsStatusValue
							+ " DealId " + deal.getDealId() + " KdbTableId " + deal.getKdbTableId() + " TransActionId "
							+ deal.getTransactionId());

					log.info("KGR Status = " + krgStatusValueInt + " | MLS Status = " + mlsStatusValue + " DealId "
							+ deal.getDealId() + " KdbTableId " + deal.getKdbTableId() + " TransActionId "
							+ deal.getTransactionId());

					kgrStatusState.executeUpdates(krgStatusValueInt);

					if (krgStatusValueInt >= 2) {

						r.stopExecution();
						log.debug("Detiene Reintentos");
						log.info("Deal: " + deal.getDealId() + " Ya se ejecuto");

					} else {

						log.debug("KdbTableId " + deal.getKdbTableId() + " Deal " + deal.getDealId() + " Retries "
								+ deal.getRetries() + " todavia se ejecuta");

						deal.setRetries(r.getDealReties());

						if (r.getRetryAttempts() == 0) {

							strategy = CheckJob.status.get(Constants.KONDOR);
							strategy.updateStatusDealList(deal);

						}

					}

					processedDealSet.add(deal);
				}

//				retryLogic.dealReties = retryLogic.dealReties + 1;

			});
		}

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
