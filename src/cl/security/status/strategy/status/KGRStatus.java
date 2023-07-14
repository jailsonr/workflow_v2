package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.PropertiesUtil;

public class KGRStatus implements StatusStrategy{
	
	private static final String MLS = "MLS";

	@Override
	public void acceptanceLogger(Params p) {
		
		final String spCall = "{call Kustom.." + PropertiesUtil.EDAI + "(?,?,?)}";
		CallableStatement callableStatement = null;
		try {
			callableStatement = getConn().prepareCall(spCall);
		} catch (SQLException e) {
		}
		try {
			callableStatement.setString(1, MLS);
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
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public int getStatus(Deal deal) {
		int status = 0;
		String query = QueryEnum.KGR_STATUS_GET.query;

		CallableStatement cs = null;

		try {
			cs = getConn().prepareCall(query);

			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getDealId());
			cs.setInt(3, deal.getTransactionId());
			cs.setString(4, deal.getAction());
			cs.setInt(5, deal.getVersion());
			cs.setInt(6, deal.getRetries());
			cs.registerOutParameter(7, 4);
			cs.registerOutParameter(8, 12);

			cs.execute();
			status = cs.getInt(7);
		} catch (SQLException e2) {
		}

		return status;
	}

}
