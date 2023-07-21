package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.PropertiesUtil;

public class MLSStatus implements StatusStrategy {

	private static final String KGR = "KGR";

	@Override
	public void acceptanceLogger(Params p) {
		final String spCall = "{call Kustom.." + PropertiesUtil.EDAI + "(?,?,?)}";
		CallableStatement callableStatement = null;
		try {
			callableStatement = getConn().prepareCall(spCall);
		} catch (SQLException e) {
		}
		try {
			callableStatement.setString(1, KGR);
			callableStatement.setInt(2, p.getKdbTablesId());
			callableStatement.setInt(3, p.getDealsId());
		} catch (SQLException e) {
		}
		try {
			callableStatement.execute();
		} catch (SQLException e) {
		}

	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public int getStatus(Deal deal) {
		int status = 0;
		String query = QueryEnum.MLS_STATUS_GET.query;

		CallableStatement cs = null;

		try {
			cs = getConn().prepareCall(query);

			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getDealId());
			cs.registerOutParameter(3, 4);

			cs.execute();
			status = cs.getInt(3);
		} catch (SQLException e2) {
		}

		return status;
	}

	@Override
	public boolean updateStatusDealList(Deal deal) {
		// TODO Auto-generated method stub
		return false;
	}

}
