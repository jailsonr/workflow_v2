package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;

public class MLSStatus implements StatusStrategy {

	Logger log = Logger.getLogger(MLSStatus.class);

	private static final String KGR = "KGR";

	@Override
	public void acceptanceLogger(Params p) {
		final String spCall = QueryEnum.EXCEEDED_DEALS_ACCEPTANCE_INSERT.query;
		try (CallableStatement callableStatement = getConn().prepareCall(spCall);) {
			callableStatement.setString(1, KGR);
			callableStatement.setInt(2, p.getKdbTablesId());
			callableStatement.setInt(3, p.getDealsId());
			log.info("Ejecutando " + spCall);
			log.info("Sistema: " + KGR + " KDB Table:" + p.getKdbTablesId() + " Deal Id: " + p.getDealsId());

			callableStatement.execute();

		} catch (SQLException e) {
			log.error("No se pudo ejecutar" + spCall);
			log.error("Sistema: " + KGR + " KDB Table:" + p.getKdbTablesId() + " Deal Id: " + p.getDealsId());
		}

	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public int getStatus(Deal deal) {
		int status = 0;
		String query = QueryEnum.MLS_STATUS_GET.query;

		try (CallableStatement cs = getConn().prepareCall(query);) {

			cs.setInt(1, deal.getKdbTableId());
			cs.setInt(2, deal.getDealId());
			cs.registerOutParameter(3, 4);
			log.info("Ejecutando " + query);
			log.info("KDB Table:" + deal.getKdbTableId() + " Deal Id: " + deal.getDealId());

			cs.execute();
			status = cs.getInt(3);
		} catch (SQLException e2) {
			log.error("No se pudo ejecutar" + query);
			log.error("KDB Table:" + deal.getKdbTableId() + " Deal Id: " + deal.getDealId());
		}

		return status;
	}

	@Override
	public boolean updateStatusDealList(Deal deal) {
		log.warn(
				"Clase MLSStatus no tiene implementacion para metodo acceptanceLogger. Favor revisar updateStatusDealList(Deal deal).");

		return false;
	}

}
