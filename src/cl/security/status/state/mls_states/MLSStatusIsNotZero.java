package cl.security.status.state.mls_states;

import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;

public class MLSStatusIsNotZero extends KGRStatusState{

	public MLSStatusIsNotZero(KGRStatusValue kgrStatusValue) {
		super(kgrStatusValue);
	}

	@Override
	public void kgrStatusIsTwoExecution() {
		kgrStatusValue.queryUpdateRepairKGR(0, 0, null, null, null);
		kgrStatusValue.queryUpdateWKFDealsList(0, 0, 0);
		
	}

	@Override
	public void kgrStatusIsThreeExecution() {
		kgrStatusValue.queryUpdateRepairKGR(0, 0, null, null, null);
		kgrStatusValue.queryUpdateWKFDealsList(0, 0, 0);
		
	}

	@Override
	public void kgrStatusIsFourExecution() {
		kgrStatusValue.queryUpdateRepairKGR(0, 0, null, null, null);
		kgrStatusValue.queryUpdateWKFDealsList(0, 0, 0);
		
	}

}
