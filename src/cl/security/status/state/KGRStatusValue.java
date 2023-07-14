package cl.security.status.state;

import cl.security.model.Deal;

public class KGRStatusValue {
	
	private KGRStatusState state;
	public Deal deal;
	
	public KGRStatusValue() {
	}
	
	public KGRStatusValue(Deal deal) {
		this.deal = deal;
	}

	public void setState(KGRStatusState state) {
		this.state = state;
	}
	
	public boolean queryUpdateRepairKGR(int dealId, int kdbTableId, String repKGR,
			String repMLS, String envBO) {
		return true;
	}
	
	public boolean queryUpdateWKFDealsList(int dealId,
			int kdbTableId, int transactionId) {
		return true;
		
	}
	
	public void overDraftLogger(String application, int transactionId, String action,
			int kdbTablesId, int dealsId) {
		
	}

}
