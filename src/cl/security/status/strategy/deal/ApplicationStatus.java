package cl.security.status.strategy.deal;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;
import cl.security.mdd.enums.KGRStatusValueEnum;
import cl.security.model.Params;
import cl.security.observer.listeners.CheckMessagesDB;
import cl.security.status.state.KGRStatusState;
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
			
			// Se busca KGR Status. Vamos a suponer int num = 0
			int kgrStatusValue = 2;
			String number = "";
			if (kgrStatusValue == 2) {
				number = "TWO";
			} else if (kgrStatusValue == 3) {
				number = "THREE";
			} else {
				number = "FOUR";
			}
			
			KGRStatusState kgrStatusState = KGRStatusValueEnum.valueOf(number).setState();
			
			kgrStatusState.executeUpdates(kgrStatusValue);
			
			
		} else {
			
			// Reparo puede ser N o R
			String reparo = strategy.statusFromCustomWindow(p);
			
			strategy.acceptanceLogger(p);
			
			Repair repair = strategy instanceof KGRStatus ? new RepairKGR().build(p, reparo)
					: new RepairMLS().build(p, reparo);

			RepairEnum.valueOf(reparo)
					.queryUpdateRepair(repair);

			System.out.println("Ejecutando " + strategy.toString());
		}

	}

}
