package cl.security.status.state.mls_states;

import cl.security.mdd.dao.DeleteMessage;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;

public class MLSStatusIsNotZero extends KGRStatusState{
	
	private Deal deal;

	public MLSStatusIsNotZero(KGRStatusValue kgrStatusValue) {
		super(kgrStatusValue);
		this.deal = kgrStatusValue.deal;
		p = new Params("Kondor", deal.getKdbTableId(), deal.getDealId());
	}

	@Override
	public void kgrStatusIsTwoExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "N",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		DeleteMessage.deleteMessage(p);

	}

	@Override
	public void kgrStatusIsThreeExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		DeleteMessage.deleteMessage(p);
	}

	@Override
	public void kgrStatusIsFourExecution() {
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"R", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());
		DeleteMessage.deleteMessage(p);
	}

}
