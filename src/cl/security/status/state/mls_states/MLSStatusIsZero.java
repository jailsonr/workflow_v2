package cl.security.status.state.mls_states;

import cl.security.database.utils.RepairEnum;
import cl.security.mdd.dao.Repair;
import cl.security.mdd.dao.RepairKGR;
import cl.security.model.Deal;
import cl.security.model.Params;
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
		
		System.out.println("Ejecutando kgrStatusIsTwoExecution de " + this.getClass().getName());
		
		String reparo = "N";
		
		Params p = new Params("KGR", deal.getKdbTableId(), deal.getDealId());
		
		Repair repair = new RepairKGR().build(p, reparo);
		
		RepairEnum.valueOf(reparo).queryUpdateRepair(repair);
		
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		
//		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "N",
//				"N", "N");
//		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
//				deal.getTransactionId());

	}

	@Override
	public void kgrStatusIsThreeExecution() {
		System.out.println("Ejecutando kgrStatusIsThreeExecution de " + this.getClass().getName());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"N", "S");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}

	@Override
	public void kgrStatusIsFourExecution() {
		System.out.println("Ejecutando kgrStatusIsFourExecution de " + this.getClass().getName());
		kgrStatusValue.queryUpdateRepairKGR(deal.getDealId(), deal.getKdbTableId(), "R",
				"N", "N");
		kgrStatusValue.queryUpdateWKFDealsList(deal.getDealId(), deal.getKdbTableId(),
				deal.getTransactionId());
		kgrStatusValue.overDraftLogger("KGR", deal.getTransactionId() , deal.getAction(), deal.getKdbTableId(), deal.getDealId());

	}
}
