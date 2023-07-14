package cl.security.status.state;

public abstract class KGRStatusState {
	
	public KGRStatusValue kgrStatusValue;
	
	public KGRStatusState(KGRStatusValue kgrStatusValue) {
		this.kgrStatusValue = kgrStatusValue;
	}
	
	public void executeUpdates(int kgrStatusValue) {
		if (kgrStatusValue == 2) {
			kgrStatusIsTwoExecution();
		} else if (kgrStatusValue == 3) {
			kgrStatusIsThreeExecution();
		} else {
			kgrStatusIsFourExecution();
		}
	};
	
	public abstract void kgrStatusIsTwoExecution();
	public abstract void kgrStatusIsThreeExecution();
	public abstract void kgrStatusIsFourExecution();


}
