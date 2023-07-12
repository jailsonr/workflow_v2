package cl.security.status.strategy.deal;

import java.sql.SQLException;
import java.util.HashMap;
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

			DealDao.dealSet.forEach(e -> {

				retryLogic = new RetryLogic(Integer.valueOf(Constants.RETRIES), e.getRetries() * 1000, e.getRetries());

				retryLogic.retryImpl(() -> {

					Thread t = new Thread(() -> {
						// Llamar a la base de datos. Es el kgrStatusValue
						String krgStatusValue = numToWord.get(0);

						KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(krgStatusValue).setState();

						kgrStatusState.executeUpdates(KGRStatusValueEnum.valueOf(krgStatusValue).num);
					});

					t.start();

				});

			});

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

}
