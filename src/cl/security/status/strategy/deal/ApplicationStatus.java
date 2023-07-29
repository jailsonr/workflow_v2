package cl.security.status.strategy.deal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.DealDao;
import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;
import cl.security.mdd.retries.RetryLogic;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;

public class ApplicationStatus implements Runnable {

	static int count = 0;
	private Map<Integer, String> numToWord = new HashMap<>();
	private StatusStrategy strategy = null;
	private Params p;
	RetryLogic retryLogic;
	Logger log = Logger.getLogger(ApplicationStatus.class);

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
				log.error("No se pudo obtener los Deals");
			}

			DealDao.dealSet.forEach(deal -> {
				
				DealProcessThread process = new DealProcessThread(strategy, deal, retryLogic, numToWord);
				new Thread(process).start();

			});

		} else {

			System.out.println("KGR o MLS");
			// Reparo puede ser N o R
			String reparo = strategy.statusFromCustomWindow(p);

			strategy.acceptanceLogger(p);

			Repair repair = strategy instanceof KGRStatus ? new RepairKGR().build(p, reparo)
					: new RepairMLS().build(p, reparo);

			RepairEnum.valueOf(reparo).queryUpdateRepair(repair);

		}

	}

}
