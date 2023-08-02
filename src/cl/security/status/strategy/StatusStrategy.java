package cl.security.status.strategy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;

public interface StatusStrategy {

	Logger log = Logger.getLogger(StatusStrategy.class);

	default Connection getConn() {

		try {

			return DatabaseConnection.getInstance().getConnection();

		} catch (SQLException e) {
			
			e.getStackTrace();
			log.error("No se pudo conectar. Error:  " + e.getMessage());
		}

		return null;

	}

	default String statusFromCustomWindow(Params p) {

		ResultSet rs = null;

		String storeProcedure = QueryEnum.FLAGS_DEALS.query;

		try (CallableStatement cs = getConn().prepareCall(storeProcedure);) {

			cs.setString(1, "S");
			cs.setInt(2, p.getKdbTablesId());
			cs.setInt(3, p.getDealsId());
			cs.setString(4, null);
			cs.setString(5, null);
			cs.setString(6, null);

			log.info("Ejecutando " + storeProcedure + " S, " + p.getKdbTablesId() + ", " + p.getDealsId() + ", null, null, null");

			rs = cs.executeQuery();
			
			if (rs.next()) {

				if (rs.getObject("RepMLS") != null) {

					System.out.println("RepMLS");
					return rs.getString("RepMLS");

				} else if (rs.getObject("RepKGR") != null) {

					System.out.println("RepKGR");
					return rs.getString("RepKGR");

				}

				return "";

			}
		} catch (SQLException e) {
			
			e.getStackTrace();
			log.error("No se pudo ejecutar: " + storeProcedure+ " S, " + p.getKdbTablesId() + ", " + p.getDealsId() + ", null, null, null. Error: " + e.getMessage());

		}

		return "";

	}

	public void acceptanceLogger(Params p);

	public int getStatus(Deal deal);

	default boolean getStatusReady(Deal deal) {

		int status = 0;
		String storeProcedure = QueryEnum.MLS_DEAL_RESULT_GET.query;

		try (CallableStatement cs = getConn().prepareCall(storeProcedure);) {

			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getTransactionId());
			cs.setDouble(3, deal.getDealId());
			cs.registerOutParameter(4, Types.INTEGER);
			log.info("Ejecutando " + storeProcedure + " " + deal.getKdbTableId() + ", " + deal.getTransactionId() + ", " + deal.getDealId() + ", @MLSStatus");
			cs.execute();
			status = cs.getInt(4);

		} catch (SQLException e) {
			
			e.getStackTrace();
			log.error("No se pudo ejecutar: " + storeProcedure + " " + deal.getKdbTableId() + ", " + deal.getTransactionId() + ", " + deal.getDealId() + ", @MLSStatus. Error: " + e.getMessage());
			
		}

		return status != 0;

	};

	boolean updateStatusDealList(Deal deal);

}
