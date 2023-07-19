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
		System.out.println("Ejecutando kgrStatusIsTwoExecution de " + this.getClass().getName());
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "N",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsThreeExecution() {
		System.out.println("Ejecutando kgrStatusIsThreeExecution de " + this.getClass().getName());
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsFourExecution() {
		System.out.println("Ejecutando kgrStatusIsFourExecution de " + this.getClass().getName());
		//kgrStatusValue.overDraftLogger("MLS", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

}
