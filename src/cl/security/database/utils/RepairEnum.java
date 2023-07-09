package cl.security.database.utils;

import cl.security.mdd.dao.Repair;

public enum RepairEnum {

	N {
		@Override
		public void queryUpdateRepair(Repair r) {
			r.queryUpdateRepair().createKisFile();
			
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
