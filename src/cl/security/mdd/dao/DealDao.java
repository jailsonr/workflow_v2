package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;

public final class DealDao {

	public static Logger log = Logger.getLogger(DealDao.class);

	public static Set<Deal> dealSet = new HashSet<>();
	public static Set<Deal> processedDealSet = new HashSet<>();

	public static void loadDeals() throws SQLException {
		Connection con = DatabaseConnection.getInstance().getConnection();

		try (CallableStatement cs = con.prepareCall(QueryEnum.GET_DEAL_LIST.query);) {

			cs.setString(1, "P");
			cs.setInt(2, 0);

			ResultSet rs = cs.executeQuery();

			log.info("Executed " + QueryEnum.GET_DEAL_LIST.query);
			System.out.println("Executed " + QueryEnum.GET_DEAL_LIST.query);

			while (rs.next()) {
				Deal deal = new Deal();
				deal.setDealId(rs.getInt("DealId"));
				deal.setKdbTableId(rs.getInt("KdbTableId"));
				deal.setTransactionId(rs.getInt("TransactionId"));
				deal.setRetries(rs.getInt("Retries"));
				deal.setAction(rs.getString("Action"));
				deal.setStatus(rs.getString("Status"));
				deal.setVersion(rs.getInt("Version"));

				log.info("KdbTableId: " + rs.getInt("KdbTableId") + ", DealId: " + rs.getInt("DealId")
						+ ", TransactionId: " + rs.getInt("TransactionId"));
				System.out.println("KdbTableId: " + rs.getInt("KdbTableId") + ", DealId: " + rs.getInt("DealId")
						+ ", TransactionId: " + rs.getInt("TransactionId"));

				dealSet.add(deal);

			}
		} catch (Exception e) {
			System.out.println(e);
			log.error("Not executed " + QueryEnum.GET_DEAL_LIST.query + ". Error: " + e.getMessage());
			System.out.println("Not executed " + QueryEnum.GET_DEAL_LIST.query + ". Error: " + e.getMessage());
		}

	}

}
