package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;
import cl.security.utils.Constants;

public class RepairKGR extends Repair {
	
	Logger log = Logger.getLogger(RepairKGR.class);

	@Override
	public Repair build(Params p, String reparo) {
		this.p = p;
		this.reparo = reparo;
		return this;
	}

	@Override
	public void createKisFile(Params p) {
		String fileName = null;
		KisFileDAO create = new KisFileDAO();
		int dealsId = create.getKISDealId(p.getKdbTablesId(), p.getDealsId());
		fileName = create.importFile(dealsId, p.getKdbTablesId(), 0, "Y");
		
		System.out.println("Creando archivo KIS" + p.getKdbTablesId() + " " + p.getDealsId());
		log.info("Creando archivo KIS" + p.getKdbTablesId() + " " + p.getDealsId());;
		
		super.deleteMessage(p);

	}

	@Override
	public Repair queryUpdateRepair(int dealId, int kdbTablesId, String repKGR, String repMLS, String envBO) {
		
		String storeProcedure = QueryEnum.FLAGS_DEALS.query;
		
		try (CallableStatement cs = getConn().prepareCall(storeProcedure);) {
			
			cs.setString(1, "U");
			cs.setInt(2, kdbTablesId);
			cs.setInt(3, dealId);
			cs.setString(4, repKGR);
			cs.setString(5, repMLS);
			cs.setString(6, envBO);
			
			System.out.println("Ejecutando RepairKGR " + QueryEnum.FLAGS_DEALS.query + " @Origen, @KdbTables_Id, @DealId, @RepKGR, @RepMLS, @Bloqueo");
			System.out.println("Ejecutando RepairKGR " + QueryEnum.FLAGS_DEALS.query + "U , " + kdbTablesId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);			
			log.info("Ejecutando RepairKGR " + QueryEnum.FLAGS_DEALS.query + " @Origen, @KdbTables_Id, @DealId, @RepKGR, @RepMLS, @Bloqueo");
			log.info("Ejecutando RepairKGR " + QueryEnum.FLAGS_DEALS.query + "U , " + kdbTablesId + ", " + dealId + ", " + repKGR + ", " + repMLS + ", " + envBO);
			
			cs.execute();
		} catch (SQLException e) {
			
			System.out.println("No se pudo ejecutar RepairKGR " + storeProcedure + " " + e.getMessage());
			log.error("No se pudo ejecutar RepairKGR " + storeProcedure + " " + e.getMessage());
			
		}

	
		return this;
	
	}

}
