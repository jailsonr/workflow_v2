package cl.security.status.state;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;

import cl.security.database.DatabaseConnection;
import cl.security.model.Deal;
import cl.security.utils.PropertiesUtil;

public class KGRStatusValue {

	private KGRStatusState state;
	public Deal deal;

	public KGRStatusValue() {
	}

	public KGRStatusValue(Deal deal) {
		this.deal = deal;
	}

	public void setState(KGRStatusState state) {
		this.state = state;
	}

	public boolean queryUpdateRepairKGR(int dealId, int kdbTableId, String repKGR, String repMLS, String envBO) {
		CallableStatement cs = null;
		final String spCall = "{call Kustom.." + PropertiesUtil.FLAGS + "(?,?,?,?,?,?)}";

		try {
			cs = DatabaseConnection.getInstance().getConnection().prepareCall(spCall);
		} catch (SQLException e) {
		}
		try {
			cs.setString(1, "U");
			cs.setInt(2, kdbTableId);
			cs.setInt(3, dealId);
			cs.setString(4, repKGR);
			cs.setString(5, repMLS);
			cs.setString(6, envBO);
		} catch (SQLException e) {
		}
		try {
			System.out.println("Ejecutando " +  PropertiesUtil.FLAGS + " DealId: " + dealId);
			cs.execute();
		} catch (SQLException e) {
		}

		return true;
	}

	public boolean queryUpdateWKFDealsList(int dealId, int kdbTableId, int transactionId) {
		Statement stmt = null;

		String queryUpdateRepair = "UPDATE " + PropertiesUtil.DEALTABLA + " SET Status = 'T' WHERE DealId = " + dealId
				+ " AND KdbTableId = " + kdbTableId + " AND TransactionId = " + transactionId;

		try {
			stmt = DatabaseConnection.getInstance().getConnection().createStatement();

		} catch (SQLException e) {
		}
		try {
			System.out.println("Ejecutando " +  PropertiesUtil.DEALTABLA + " DealId: " + dealId);
			stmt.executeUpdate(queryUpdateRepair);

		} catch (SQLException e) {
		}
		return true;

	}

	public void overDraftLogger(String application, int transactionId, String action, int kdbTablesId, int dealsId) {
		String storedProcedure = "{call " + PropertiesUtil.EDAI + "(?,?,?)}";
		System.out
				.println("WKF_ExceededDeals_acceptanceInsert: " + application + " - " + kdbTablesId + " - " + dealsId);

		CallableStatement cs = null;

		try {
			cs = DatabaseConnection.getInstance().getConnection().prepareCall(storedProcedure);
		} catch (SQLException e) {
		}

		try {
			cs.setString(1, application);
			cs.setInt(2, kdbTablesId);
			cs.setInt(3, dealsId);

		} catch (SQLException e) {
		}

		try {
			System.out.println("Ejecutando " +  PropertiesUtil.EDAI + " DealId: " + dealsId);
			cs.execute();
		} catch (SQLException e) {
		}

	}

}
