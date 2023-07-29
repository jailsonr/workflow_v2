package cl.security.status.strategy.status;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Deal;
import cl.security.model.Params;
import cl.security.status.strategy.StatusStrategy;
import cl.security.utils.PropertiesUtil;

public class KondorStatus implements StatusStrategy {

	Logger log = Logger.getLogger(KondorStatus.class);

	@Override
	public String statusFromCustomWindow(Params p) {
		log.warn(
				"Clase KondorStatus no tiene implementacion para metodo statusFromCustomWindow(Params p). Favor revisar ejecución.");
		return null;
	}

	@Override
	public void acceptanceLogger(Params p) {
		log.warn("Clase KondorStatus no tiene implementacion para metodo acceptanceLogger. Favor revisar ejecución.");
	}

	@Override
	public int getStatus(Deal deal) {
		log.warn(
				"Clase KondorStatus no tiene implementacion para metodo getStatus(Deal deal). Favor revisar ejecución.");
		return 0;
	}

	@Override
	public boolean updateStatusDealList(Deal deal) {

		int status = 0;
		String storeProcedure = QueryEnum.DEAL_LIST_UPDATE.query;

		try (CallableStatement cs = getConn().prepareCall(storeProcedure);) {
			cs.setInt(1, deal.getDealId());
			cs.setInt(2, deal.getKdbTableId());
			cs.setDouble(3, deal.getTransactionId());
			cs.setString(4, "P");

			log.info("Ejecutando " + storeProcedure);
			log.info("KDB Table:" + deal.getKdbTableId() + " Deal Id: " + deal.getDealId() + " Transaction Id: "
					+ deal.getTransactionId() + " Status: " + "P");

			cs.executeUpdate();
		} catch (SQLException e) {
			log.error("No se puedo ejecutar " + storeProcedure);
			log.error("KDB Table:" + deal.getKdbTableId() + " Deal Id: " + deal.getDealId() + " Transaction Id: "
					+ deal.getTransactionId() + " Status: " + "P");
		}

		return status != 0;
	}

}
