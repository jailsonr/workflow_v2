package cl.security.status.state;

public abstract class KGRStatusState {
	
	public KGRStatusValue kgrStatusValue;
	
	public KGRStatusState(KGRStatusValue kgrStatusValue) {
		this.kgrStatusValue = kgrStatusValue;
	}
	
	public void executeUpdates(int kgrStatusValue) {
		
		switch (kgrStatusValue) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			kgrStatusIsTwoExecution();
			break;
		case 3:
			kgrStatusIsThreeExecution();
			break;
		case 4:
			kgrStatusIsFourExecution();
			break;

		default:
			break;
		}
	};
	
	public abstract void kgrStatusIsTwoExecution();
	public abstract void kgrStatusIsThreeExecution();
	public abstract void kgrStatusIsFourExecution();


}
