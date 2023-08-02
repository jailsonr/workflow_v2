package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;

public class KGRStatus implements StatusStrategy {

	Logger log = Logger.getLogger(KGRStatus.class);

	private static final String MLS = "MLS";

	@Override
	public void acceptanceLogger(Params p) {

		final String spCall = QueryEnum.EXCEEDED_DEALS_ACCEPTANCE_INSERT.query;
		try (CallableStatement callableStatement = getConn().prepareCall(spCall)) {
			
			callableStatement.setString(1, MLS);
			callableStatement.setInt(2, p.getKdbTablesId());
			callableStatement.setInt(3, p.getDealsId());

			log.info("Ejecutando " + spCall + " " + MLS + ", " + p.getKdbTablesId() + ", " + p.getDealsId());

			callableStatement.execute();
			
		} catch (SQLException e) {
			
			e.getStackTrace();
			log.error("No se pudo ejecutar" + spCall + " " + MLS + ", " + p.getKdbTablesId() + ", " + p.getDealsId() + ". Error: " + e.getMessage());
			
		}

	}

	@Override
	public String toString() {
		
		return this.getClass().getName();
		
	}

	@Override
	public int getStatus(Deal deal) {
		
		int status = 0;
		String query = QueryEnum.KGR_STATUS_GET.query;

		try (CallableStatement cs = getConn().prepareCall(query);) {

			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getDealId());
			cs.setInt(3, deal.getTransactionId());
			cs.setString(4, deal.getAction());
			cs.setInt(5, deal.getVersion());
			cs.setInt(6, deal.getRetries());
			cs.registerOutParameter(7, 4);
			cs.registerOutParameter(8, 12);

			log.info("Ejecutando " + query + " " + deal.getKdbTableId() + ", " + deal.getDealId() + ", "
					+ deal.getTransactionId() + ", " + deal.getAction() + ", " + deal.getVersion() + ", "
					+ deal.getRetries());
			
			cs.execute();
			status = cs.getInt(7);
		} catch (SQLException e2) {
			
			e2.getStackTrace();
			log.error("No se pudo ejecutar" + query + " " + deal.getKdbTableId() + ", " + deal.getDealId() + ", "
					+ deal.getTransactionId() + ", " + deal.getAction() + ", " + deal.getVersion() + ", "
					+ deal.getRetries() + ". Error: " + e2.getMessage());
		}

		return status;
		
	}

	@Override
	public boolean updateStatusDealList(Deal deal) {
		log.warn(
				"Clase KGRStatus no tiene implementacion para metodo updateStatusDealList(Deal deal). Favor revisar ejecución.");
		return false;
	}

}
