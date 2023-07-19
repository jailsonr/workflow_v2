package cl.security.status.strategy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import cl.security.database.DatabaseConnection;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.utils.PropertiesUtil;

public interface StatusStrategy {

	default Connection getConn() {
		try {
			return DatabaseConnection.getInstance().getConnection();
		} catch (SQLException e) {
		}
		return null;
	}

	default String statusFromCustomWindow(Params p) {

		CallableStatement cs = null;
		ResultSet rs = null;

		String storeProcedure = "{call " + PropertiesUtil.FLAGS + "(?,?,?,?,?,?)}";

		try {
			cs = getConn().prepareCall(storeProcedure);
		} catch (SQLException e) {
		}
		try {
			cs.setString(1, "S");
			cs.setInt(2, p.getKdbTablesId());
			cs.setInt(3, p.getDealsId());
			cs.setString(4, null);
			cs.setString(5, null);
			cs.setString(6, null);

		} catch (SQLException e) {
		}

		try {
			rs = cs.executeQuery();
		} catch (SQLException e) {
		}

		try {
			if (rs.next()) {
				if (rs.getObject("RepMLS") != null) {
					return rs.getString("RepMLS");
				} else if (rs.getObject("RepKGR") != null) {
					return rs.getString("RepKGR");
				}
				return "";
			}
		} catch (SQLException e) {
		}

		return "";

	}

	public void acceptanceLogger(Params p);

	public int getStatus(Deal deal);

	default boolean getStatusReady(Deal deal) {

		CallableStatement cs = null;
		int status = 0;
		String storeProcedure = "{call Kustom.." + PropertiesUtil.MLSRESULT + "(?,?,?,?)}";

		try {
			cs = getConn().prepareCall(storeProcedure);
			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getTransactionId());
			cs.setDouble(3, deal.getDealId());
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			status = cs.getInt(4);
		} catch (SQLException e) {
		}

		return status != 0;

	};

}
