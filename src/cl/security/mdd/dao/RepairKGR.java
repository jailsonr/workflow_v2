package cl.security.mdd.dao;

import cl.security.model.Params;

public class RepairKGR extends Repair {

	@Override
	public Repair build(Params p, String reparo) {
		this.p = p;
		this.reparo = reparo;
		return this;
	}

	@Override
	public void createKisFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public Repair queryUpdateRepair(int dealId, int kdbTablesId, String repKGR, String repMLS, String envBO) {
		// TODO Auto-generated method stub
		return null;
	}

}
