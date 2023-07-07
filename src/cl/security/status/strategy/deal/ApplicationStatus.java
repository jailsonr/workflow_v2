package cl.security.status.strategy.deal;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;
import cl.security.model.Params;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;

public class ApplicationStatus implements Runnable {

	private StatusStrategy strategy = null;
	private Params p;

	public ApplicationStatus process(StatusStrategy strategy, CheckMessagesDB checkMessages) {
		this.strategy = strategy;
		this.p = checkMessages.getParams();
		return this;
	}

	@Override
	public void run() {

		if (strategy instanceof KondorStatus) {

		} else {
			
			// Reparo puede ser N o R
			String reparo = strategy.statusFromCustomWindow(p);
			
			strategy.acceptanceLogger(p);

			RepairEnum.valueOf(reparo)
					.queryUpdateRepair(strategy instanceof KGRStatus ? new RepairKGR().build(p, reparo)
							: new RepairMLS().build(p, reparo));

			System.out.println("Ejecutando " + strategy.toString());
		}

	}

}
