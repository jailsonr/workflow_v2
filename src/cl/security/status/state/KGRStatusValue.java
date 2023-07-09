package cl.security.status.state;

public class KGRStatusValue {
	
	private KGRStatusState state;
	
	public KGRStatusValue() {
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

}
