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
import cl.security.quartz.scheduler.CheckJob;
import cl.security.utils.Constants;
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

			System.out.println("Ejecutando KGRStatusValue " + QueryEnum.FLAGS_DEALS.query + " @Origen, @KdbTables_Id, @DealId, @RepKGR, @RepMLS, @Bloqueo");
			System.out.println("Ejecutando KGRStatusValue " + QueryEnum.FLAGS_DEALS.query + "U , " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);			
			log.info("Ejecutando KGRStatusValue " + QueryEnum.FLAGS_DEALS.query + " @Origen, @KdbTables_Id, @DealId, @RepKGR, @RepMLS, @Bloqueo");
			log.info("Ejecutando KGRStatusValue " + QueryEnum.FLAGS_DEALS.query + "U , " + kdbTableId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);
			
			cs.execute();
			
		} catch (SQLException e) {
			
			System.out.println("No se pudo ejecutar KGRStatusValue " + spCall + " " + e.getMessage());
			log.error("No se pudo ejecutar KGRStatusValue " + spCall + " " + e.getMessage());
			
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
			
			System.out.println("No se pudo ejecutar " + queryUpdateRepair + " " + e.getMessage());
			log.error("No se pudo ejecutar " + queryUpdateRepair + " " + e.getMessage());
			
		}
		
		return true;

	}

	public void overDraftLogger(String application, int transactionId, String action, int kdbTablesId, int dealsId) {

		//String storedProcedure = "{call " + PropertiesUtil.EDAI + "(?,?,?)}";
		String storedProcedure= QueryEnum.EXCEEDED_DEALS_ACCEPTANCE_INSERT.query;
		
		System.out
				.println("WKF_ExceededDeals_acceptanceInsert: " + application + " - " + kdbTablesId + " - " + dealsId);
		// log.info("WKF_ExceededDeals_acceptanceInsert: " + application + " - " +
		// kdbTablesId + " - " + dealsId);

		try (CallableStatement cs = DatabaseConnection.getInstance().getConnection().prepareCall(storedProcedure);) {
			
			cs.setString(1, application);
			cs.setInt(2, kdbTablesId);
			cs.setInt(3, dealsId);
			
			System.out.println("Ejecutando KGRStatusValue " + storedProcedure + " @Application, @KdbTables_Id, @DealId");
			System.out.println("Ejecutando KGRStatusValue " + storedProcedure + " " + application + ", " + kdbTablesId + ", " + dealsId);			
			log.info("Ejecutando KGRStatusValue " + storedProcedure + " @Application, @KdbTables_Id, @DealId");
			log.info("Ejecutando KGRStatusValue " + storedProcedure + " " + application + ", " + kdbTablesId + ", " + dealsId);
			
			cs.execute();
			
		} catch (SQLException e) {
			
			System.out.println("No se pudo ejecutar " + storedProcedure + " " + e.getMessage());
			log.error("No se pudo ejecutar " + storedProcedure + " " + e.getMessage());
			
		}

	}
	
	public void createKisFile(Params p) {
        String fileName = null;
        KisFileDAO create = new KisFileDAO();
        int dealsId = create.getKISDealId(p.getKdbTablesId(), p.getDealsId());
        fileName = create.importFile(dealsId, p.getKdbTablesId(), 0, "Y");

        System.out.println("Creando archivo KIS");
        log.info("Creando archivo KIS");

    }

}
