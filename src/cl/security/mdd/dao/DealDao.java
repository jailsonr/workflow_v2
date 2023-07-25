package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.utils.PropertiesUtil;

public final class DealDao {

	public static Set<Deal> dealSet = new HashSet<>();
	public static Set<Deal> processedDealSet = new HashSet<>();

	public static void loadDeals() throws SQLException {

		try {
			Connection con = DatabaseConnection.getInstance().getConnection();

			CallableStatement cs = con.prepareCall(QueryEnum.GET_DEAL_LIST.query);

			System.out.println("GET_DEAL_LIST" + cs);
			cs.setString(1, "P");
			cs.setInt(2, 0);
			
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				Deal deal = new Deal();
				System.out.println("ENTRO = " + rs.getInt("DealId"));
				deal.setDealId(rs.getInt("DealId"));
				deal.setKdbTableId(rs.getInt("KdbTableId"));
				deal.setTransactionId(rs.getInt("TransactionId"));
				deal.setRetries(rs.getInt("Retries"));
				deal.setAction(rs.getString("Action"));
				deal.setStatus(rs.getString("Status"));
				deal.setVersion(rs.getInt("Version"));

				dealSet.add(deal);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
