package cl.security.mdd.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;

import cl.security.database.utils.QueryEnum;
import cl.security.model.Params;

public class RepairMLS extends Repair {

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

		super.deleteMessage(p);

	}

	@Override
	public Repair queryUpdateRepair(int dealId, int kdbTablesId, String repKGR, String repMLS, String envBO) {

		CallableStatement cs = null;

		//System.out.println("Ejecutando " + PropertiesUtil.FLAGS + " DealId: " + dealId);
		//String storeProcedure = "{call Kustom.dbo." + PropertiesUtil.FLAGS + "(?,?,?,?,?,?)}";
		System.out.println("Ejecutando " + QueryEnum.FLAGS_DEALS.query + " DealId: " + dealId);
		String storeProcedure = QueryEnum.FLAGS_DEALS.query;
		
		try {
			cs = getConn().prepareCall(storeProcedure);
		} catch (SQLException e) {
		}

		try {
			cs.setString(1, "U");
			cs.setInt(2, kdbTablesId);
			cs.setInt(3, dealId);
			cs.setString(4, repKGR);
			cs.setString(5, repMLS);
			cs.setString(6, envBO);
		} catch (SQLException e) {
		}
		try {
			cs.execute();
		} catch (SQLException e) {
		}
		return this;
	}

}
