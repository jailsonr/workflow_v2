package cl.security.status.state;

public class MLSStatusValue {
	
	private MLSStatusState state;
	
	public MLSStatusValue() {
	}
	
	public void setState(MLSStatusState state) {
		this.state = state;
	}
	
	public boolean queryUpdateRepairKGR(int dealId, int kdbTableId, String repKGR,
			String repMLS, String envBO) {
		return true;
	}
	
	public static boolean queryUpdateWKFDealsList(int dealId,
			int kdbTableId, int transactionId) {
		return true;
		
	}

}
