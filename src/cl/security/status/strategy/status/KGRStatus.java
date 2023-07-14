package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

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
	public int getStatus(int kdbTablesId, int dealId, int transactionId, String action, int version, int retries) {
		// TODO Auto-generated method stub
		return 0;
	}

}
