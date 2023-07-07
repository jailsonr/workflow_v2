package cl.security.model;

public class Params {

	private String dataBaseName;
	private int kdbTablesId;
	private int dealsId;

	public Params(String params, int kdbTablesId, int dealsId) {
		this.dataBaseName = params;
		this.kdbTablesId = kdbTablesId;
		this.dealsId = dealsId;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public int getKdbTablesId() {
		return kdbTablesId;
	}

	public int getDealsId() {
		return dealsId;
	}

}
