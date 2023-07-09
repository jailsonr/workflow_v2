package cl.security.mdd.dao;

import cl.security.model.Params;

public class RepairMLS extends Repair {

	@Override
	public Repair queryUpdateRepair() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public Repair build(Params p, String reparo) {
		this.p = p;
		this.reparo = reparo;
		return this;
	}

	@Override
	public void createKisFile() {
		String fileName = null;
        KisFileDAO create = new KisFileDAO();
        int dealsId = create.getKISDealId( p.getKdbTablesId(), p.getDealsId());
        fileName = create.importFile(dealsId, p.getKdbTablesId(), 0, "Y");
        
        deleteMessage();
		
	}
	
	public void deleteMessage() {
		DeleteMessage.deleteMessage();
	}

}
