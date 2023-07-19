package cl.security.mdd.enums;

import cl.security.model.Deal;
import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;
import cl.security.status.state.mls_states.MLSStatusIsNotZero;
import cl.security.status.state.mls_states.MLSStatusIsZero;

public enum KGRStatusValueEnum {
	
	ZERO(0) {
		@Override
		public KGRStatusState setState(int mlsStatusValue, Deal d) {
			return super.setState(mlsStatusValue, d);
		}
	},
	ONE(1) {
		@Override
		public KGRStatusState setState(int mlsStatusValue, Deal d) {
			return super.setState(mlsStatusValue, d);
		}
	},
	TWO(2) {
		@Override
		public KGRStatusState setState(int mlsStatusValue, Deal d) {
			return super.setState(mlsStatusValue, d);
		}
	},
	THREE(3) {
		@Override
		public KGRStatusState setState(int mlsStatusValue, Deal d) {
			return super.setState(mlsStatusValue, d);
		}
	},
	FOUR(4) {
		@Override
		public KGRStatusState setState(int mlsStatusValue, Deal d) {
			return super.setState(mlsStatusValue, d);
		}
	};

	public int num;

	KGRStatusValueEnum(int num) {
		this.num = num;
	}

	public KGRStatusState setState(int mlsStatusValue, Deal deal) {
		if (mlsStatusValue == 0) {
			return new MLSStatusIsZero(new KGRStatusValue(deal));
		} else {
			return new MLSStatusIsNotZero(new KGRStatusValue(deal));
		}
	}

}
