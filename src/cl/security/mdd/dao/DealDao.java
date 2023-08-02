package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;

public final class DealDao {

	public static Logger log = Logger.getLogger(DealDao.class);

	public Deal getDealBD(int dealId, int kdbTable) throws SQLException {
		
		Connection con = DatabaseConnection.getInstance().getConnection();
		Deal deal = null;

		try (CallableStatement cs = con.prepareCall(QueryEnum.GET_DEAL_LIST.query);) {

			cs.setInt(1, dealId);
			cs.setInt(2, kdbTable);

			ResultSet rs = cs.executeQuery();

			log.info("Executed " + QueryEnum.GET_DEAL_LIST.query + " " + dealId + ", " + kdbTable);
			System.out.println("Executed " + QueryEnum.GET_DEAL_LIST.query + " " + dealId + ", " + kdbTable);

			while (rs.next()) {
				
				deal = new Deal();
				deal.setDealId(rs.getInt("DealId"));
				deal.setKdbTableId(rs.getInt("KdbTableId"));
				deal.setTransactionId(rs.getInt("TransactionId"));
				deal.setRetries(rs.getInt("Retries"));
				deal.setAction(rs.getString("Action"));
				deal.setStatus(rs.getString("Status"));
				deal.setVersion(rs.getInt("Version"));

				log.info("Return " + QueryEnum.GET_DEAL_LIST.query + " KdbTableId " + deal.getKdbTableId() + ", DealId " + deal.getDealId()
				+ ", TransactionId " + deal.getTransactionId() + ", Retries " + deal.getRetries()
				+ ", Action " + deal.getAction() + ", Status " + deal.getStatus() + ", Version " + deal.getVersion());
				System.out.println("Return " + QueryEnum.GET_DEAL_LIST.query + " KdbTableId " + deal.getKdbTableId() + ", DealId " + deal.getDealId()
				+ ", TransactionId " + deal.getTransactionId() + ", Retries " + deal.getRetries()
				+ ", Action " + deal.getAction() + ", Status " + deal.getStatus() + ", Version " + deal.getVersion());

			}
		} catch (Exception e) {
			
			System.out.println(e);
			log.error("Not executed " + QueryEnum.GET_DEAL_LIST.query + " " + dealId + ", " + kdbTable + ". Error: " + e.getMessage());
			System.out.println("Not executed " + QueryEnum.GET_DEAL_LIST.query + " " + dealId + ", " + kdbTable + ". Error: " + e.getMessage());
			
		}

		return deal;

	}

}
