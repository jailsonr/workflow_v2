package cl.security.mdd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;

public final class DealDao {
	
	public static Set<Deal> dealSet = new HashSet<>();
	
	public static void loadDeals() throws SQLException {
		Connection con = DatabaseConnection.getInstance().getConnection();
		
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY,
				ResultSet.HOLD_CURSORS_OVER_COMMIT);

		ResultSet rs = stmt.executeQuery(QueryEnum.GET_DEAL_LIST.query);
		
		while (rs.next()) {
				Deal deal = new Deal();
				deal.setDealId(rs.getInt("DealId"));
				deal.setKdbTableId(rs.getInt("KdbTableId"));
				deal.setTransactionId(rs.getInt("TransactionId"));
				deal.setRetries(rs.getInt("Retries"));
				deal.setAction(rs.getString("Action"));
				deal.setStatus(rs.getString("Status"));
				deal.setVersion(rs.getInt("Version"));

				dealSet.add(deal);
				
		}
		
	}

}
