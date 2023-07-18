package cl.security.status.state.mls_states;

import cl.security.model.Deal;
import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;

public class MLSStatusIsNotZero extends KGRStatusState{
	
	private Deal deal;

	public MLSStatusIsNotZero(KGRStatusValue kgrStatusValue) {
		super(kgrStatusValue);
		this.deal = kgrStatusValue.deal;
	}

	@Override
	public void kgrStatusIsTwoExecution() {
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "N",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsThreeExecution() {
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsFourExecution() {
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

}
