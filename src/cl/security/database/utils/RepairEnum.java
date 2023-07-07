package cl.security.database.utils;

import cl.security.mdd.dao.KisFileDAO;
import cl.security.mdd.dao.Repair;

public enum RepairEnum {

	N {
		@Override
		public void queryUpdateRepair(Repair r) {
			r.queryUpdateRepair();
			String fileName = null;
            KisFileDAO create = new KisFileDAO();
            int dealsId = create.getKISDealId( r.p.getKdbTablesId(), r.p.getDealsId());
            fileName = create.importFile(dealsId, r.p.getKdbTablesId(), 0, "Y");
		}
	},
	R {
		@Override
		public void queryUpdateRepair(Repair r) {
			r.queryUpdateRepair();
		}
	};
	

	public void queryUpdateRepair(Repair r) {
	}

}
