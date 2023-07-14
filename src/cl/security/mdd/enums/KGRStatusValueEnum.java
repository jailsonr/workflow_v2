package cl.security.mdd.enums;

import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;
import cl.security.status.state.mls_states.MLSStatusIsNotZero;
import cl.security.status.state.mls_states.MLSStatusIsZero;

public enum KGRStatusValueEnum {

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

	public KGRStatusState setState(int mlsStatusValue, Deal d) {
		if (mlsStatusValue == 0) {
			return new MLSStatusIsZero(new KGRStatusValue(d));
		} else {
			return new MLSStatusIsNotZero(new KGRStatusValue(d));
		}
	}

}
