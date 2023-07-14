package cl.security.database.utils;

import cl.security.utils.PropertiesUtil;

public enum QueryEnum {

	VERIFY_MESSAGES("SELECT * FROM " + PropertiesUtil.MESSAGES),
	GET_DEAL_LIST("SELECT * FROM " + PropertiesUtil.DEAL + " WHERE Status = 'P' AND Retries = 0"),
	UPDATE_FLAGS_DEALS("{call Kustom.." + PropertiesUtil.FLAGS + "(?,?,?,?,?,?)}"),
	MESSAGES_IN_PROGRESS_DELETE("{call Kustom.." + PropertiesUtil.REGISTRYDELETE + "(?,?)}"),
	IMPORT_FILE("{call Kustom.."+ PropertiesUtil.KISFIELDS + "(?,?,?)}"),
	GET_KIS_DEAL_ID("{call Kustom.."+ PropertiesUtil.GETKISDEAL + "(?,?,?)}"),
	MLS_STATUS_GET("{call Kustom.." + PropertiesUtil.MLSGET + "(?,?,?)}"),
	EXCEEDED_DEALS_ACCEPTANCE_INSERT("{call Kustom.." + PropertiesUtil.EDAI + "(?,?,?)}"),
	EXCEEDED_DEALS_INSERT("{call Kustom.." + PropertiesUtil.EDI + "(?,?,?,?,?)}"),
	MLS_DEAL_RESULT_GET("{call Kustom.." + PropertiesUtil.MLSRESULT + "(?,?,?,?)}"),
	KGR_STATUS_GET("{call " + PropertiesUtil.KGRGET + "(?,?,?,?,?,?,?,?)}"),
	DEAL_LIST_UPDATE("{call " + PropertiesUtil.DEALLISTUPDATE + "(?,?,?)}")
	;

	public final String query;

	QueryEnum(String query) {
		this.query = query;
	}

}
