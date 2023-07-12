package cl.security.database.utils;

import cl.security.utils.PropertiesUtil;

public enum QueryEnum {

	VERIFY_MESSAGES("SELECT * FROM " + PropertiesUtil.MESSAGES),
	GET_DEAL_LIST("SELECT * FROM Kustom..WKF_DealsList WHERE Status = 'P' AND Retries = 0");

	public final String query;

	QueryEnum(String query) {
		this.query = query;
	}

}
