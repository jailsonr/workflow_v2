package cl.security.database.utils;

import cl.security.utils.PropertiesUtil;

public enum QueryEnum {

	VERIFY_MESSAGES("SELECT * FROM " + PropertiesUtil.MESSAGES),
    GET_DEAL_LIST("{call " + PropertiesUtil.DEAL + "(?,?)}"),
    FLAGS_DEALS("{call " + PropertiesUtil.FLAGS + "(?,?,?,?,?,?)}"),
    MESSAGES_IN_PROGRESS_DELETE("{call " + PropertiesUtil.REGISTRYDELETE + "(?,?,?)}"),
    IMPORT_FILE("{call "+ PropertiesUtil.KISFIELDS + "(?,?,?)}"),
    GET_KIS_DEAL_ID("{call "+ PropertiesUtil.GETKISDEAL + "(?,?,?)}"),
    MLS_STATUS_GET("{call " + PropertiesUtil.MLSGET + "(?,?,?)}"),
    EXCEEDED_DEALS_ACCEPTANCE_INSERT("{call " + PropertiesUtil.EDAI + "(?,?,?)}"),
    EXCEEDED_DEALS_INSERT("{call " + PropertiesUtil.EDI + "(?,?,?,?,?)}"),
    MLS_DEAL_RESULT_GET("{call " + PropertiesUtil.MLSRESULT + "(?,?,?,?)}"),
    KGR_STATUS_GET("{call " + PropertiesUtil.KGRGET + "(?,?,?,?,?,?,?,?)}"),
    DEAL_LIST_UPDATE("{call " + PropertiesUtil.DEALLISTUPDATE + "(?,?,?,?)}");

	public final String query;

	QueryEnum(String query) {
		this.query = query;
	}

}
