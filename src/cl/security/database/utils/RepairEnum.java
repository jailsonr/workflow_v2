package cl.security.database.utils;

import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.mdd.dao.RepairMLS;

public enum RepairEnum {

	N {
		@Override
		public void queryUpdateRepair(Repair r) {
			
			if (r instanceof RepairKGR) {
				r.queryUpdateRepair(r.p.getDealsId(), r.p.getKdbTablesId(), N.toString(), N.toString(), N.toString()).createKisFile(r.p);
				r.queryUpdateWKFDealsList(0, 0, 0);
			} else if (r instanceof RepairMLS) {
				r.queryUpdateRepair(r.p.getDealsId(), r.p.getKdbTablesId(), N.toString(), N.toString(), N.toString()).createKisFile(r.p);
			}
			
			
		}
	},
	R {
		@Override
		public void queryUpdateRepair(Repair r) {
			if (r instanceof RepairKGR) {
				r.queryUpdateRepair(r.p.getDealsId(), r.p.getKdbTablesId(), N.toString(), R.toString(), "S");
			} else if (r instanceof RepairMLS) {
				r.queryUpdateRepair(r.p.getDealsId(), r.p.getKdbTablesId(), R.toString(), N.toString(), "S");
			}
		}
	};
	

	public void queryUpdateRepair(Repair r) {
	}

}
