package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.PropertiesUtil;

public class KondorStatus implements StatusStrategy{


	@Override
	public String statusFromCustomWindow(Params p) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void acceptanceLogger(Params p) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getStatus(Deal deal) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean updateStatusDealList(Deal deal) {

		CallableStatement cs = null;
		int status = 0;
		String storeProcedure = "{call Kustom.." + PropertiesUtil.DEALLISTUPDATESTATUS + "(?,?,?)}";
		
		try {
			cs = getConn().prepareCall(storeProcedure);
			cs.setInt(1, deal.getDealId());
			cs.setInt(2, deal.getKdbTableId());
			cs.setDouble(3, deal.getTransactionId());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status != 0;
	}

}
