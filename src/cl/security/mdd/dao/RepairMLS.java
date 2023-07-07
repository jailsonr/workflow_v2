package cl.security.mdd.dao;

import cl.security.model.Params;

public class RepairMLS extends Repair {

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
