package cl.security.status.state.mls_states;

import cl.security.model.Deal;
import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;

public class MLSStatusIsZero extends KGRStatusState {
	
	private Deal deal;

	public MLSStatusIsZero(KGRStatusValue kgrStatusValue) {
		super(kgrStatusValue);
		this.deal = kgrStatusValue.deal;
	}

	@Override
	public void kgrStatusIsTwoExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "N",
				"N", "N");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());

	}

	@Override
	public void kgrStatusIsThreeExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"N", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsFourExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"N", "N");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}
}
