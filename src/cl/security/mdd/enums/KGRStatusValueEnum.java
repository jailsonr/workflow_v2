package cl.security.mdd.enums;

import cl.security.status.state.KGRStatusState;
import cl.security.status.state.KGRStatusValue;
import cl.security.status.state.mls_states.MLSStatusIsNotZero;
import cl.security.status.state.mls_states.MLSStatusIsZero;

public enum KGRStatusValueEnum {

	TWO(2) {
		@Override
		public KGRStatusState setState() {
			return super.setState();
		}
	},
	THREE(3) {
		@Override
		public KGRStatusState setState() {
			return super.setState();
		}
	},
	FOUR(4) {
		@Override
		public KGRStatusState setState() {
			return super.setState();
		}
	};

	public int num;

	KGRStatusValueEnum(int num) {
		this.num = num;
	}

	public KGRStatusState setState() {
		if (num ==0) {
			return new MLSStatusIsZero(new KGRStatusValue());
		} else {
			return new MLSStatusIsNotZero(new KGRStatusValue());
		}
	}

}
