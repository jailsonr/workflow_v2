package cl.security.status.state;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.mdd.dao.KisFileDAO;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.utils.PropertiesUtil;

public class KGRStatusValue {

	private KGRStatusState state;
	public Deal deal;

	Logger log = Logger.getLogger(KGRStatusValue.class);
	
	public KGRStatusValue() {
	}

	public KGRStatusValue(Deal deal) {
		this.deal = deal;
	}

	public void setState(KGRStatusState state) {
		this.state = state;
	}

	public boolean queryUpdateRepairKGR(int dealId, int kdbTableId, String repKGR, String repMLS, String envBO) {

		final String spCall = QueryEnum.FLAGS_DEALS.query;

		try (CallableStatement cs = DatabaseConnection.getInstance().getConnection().prepareCall(spCall);) {
			
			cs.setString(1, "U");
			cs.setInt(2, kdbTableId);
			cs.setInt(3, dealId);
			cs.setString(4, repKGR);
			cs.setString(5, repMLS);
			cs.setString(6, envBO);

			System.out.println("Ejecutando " + spCall + " U, " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);			
			log.info("Ejecutando " + spCall + " U, " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);
			
			cs.execute();
			
		} catch (SQLException e) {
			
			e.getStackTrace();
			System.out.println("No se pudo ejecutar " + spCall + " U, " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO+ ". Error: " + e.getMessage());
			log.error("No se pudo ejecutar " + spCall + " U, " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO+ ". Error: " + e.getMessage());
			
		}

		return true;
	}

	public boolean queryUpdateWKFDealsList(int dealId, int kdbTableId, int transactionId) {

		String queryUpdateRepair = "UPDATE " + PropertiesUtil.DEALTABLA + " SET Status = 'T' WHERE DealId = " + dealId
				+ " AND KdbTableId = " + kdbTableId + " AND TransactionId = " + transactionId;

		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement();) {

			System.out.println("Ejecutando " + queryUpdateRepair);
			log.info("Ejecutando " + queryUpdateRepair);
			
			stmt.executeUpdate(queryUpdateRepair);
			
		} catch (SQLException e) {
			
			e.getStackTrace();
			System.out.println("No se pudo ejecutar " + queryUpdateRepair + ". Error: " + e.getMessage());
			log.error("No se pudo ejecutar " + queryUpdateRepair + ". Error: " + e.getMessage());
			
		}
		
		return true;

	}

	public void overDraftLogger(String application, int transactionId, String action, int kdbTablesId, int dealsId) {

		String storedProcedure= QueryEnum.EXCEEDED_DEALS_INSERT.query;

		try (CallableStatement cs = DatabaseConnection.getInstance().getConnection().prepareCall(storedProcedure);) {
			
			cs.setString(1, application);
			cs.setInt(2, transactionId);
			cs.setString(3, action);
			cs.setInt(4, kdbTablesId);
			cs.setInt(5, dealsId);
			
			System.out.println("Ejecutando " + storedProcedure + " " + application + ", " + transactionId + ", " + action + ", " + kdbTablesId + ", " + dealsId);			
			log.info("Ejecutando " + storedProcedure + " " + application + ", " + transactionId + ", " + action + ", " + kdbTablesId + ", " + dealsId);
			
			cs.execute();
			
		} catch (SQLException e) {
			
			e.getStackTrace();
			System.out.println("No se pudo ejecutar " + storedProcedure + " " + application + ", " + transactionId + ", " + action + ", " + kdbTablesId + ", " + dealsId + ". Error: " + e.getMessage());
			log.error("No se pudo ejecutar " + storedProcedure + " " + application + ", " + transactionId + ", " + action + ", " + kdbTablesId + ", " + dealsId + ". Error: " + e.getMessage());
			
		}

	}
	
	public void createKisFile(Params p) {
        String fileName = null;
        KisFileDAO create = new KisFileDAO();
        int dealsId = create.getKISDealId(p.getKdbTablesId(), p.getDealsId());
        fileName = create.importFile(dealsId, p.getKdbTablesId(), 0, "Y");

        log.info("Creando archivo KIS " + p.getKdbTablesId() + " " + p.getDealsId());

    }

}
