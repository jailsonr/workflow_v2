package cl.security.mdd.dao;

import cl.security.model.Params;
import cl.security.observer.listeners.CheckMessagesDB;

public class RepairKGR extends Repair {

	@Override
	public boolean queryUpdateRepair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Repair build(Params p, String reparo) {
		this.p = p;
		this.reparo = reparo;
		return this;
	}

}
