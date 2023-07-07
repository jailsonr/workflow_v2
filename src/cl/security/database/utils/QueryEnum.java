package cl.security.database.utils;

import cl.security.utils.PropertiesUtil;

public enum QueryEnum {

	VERIFY_MESSAGES("SELECT * FROM " + PropertiesUtil.MESSAGES);

	public final String query;

	QueryEnum(String query) {
		this.query = query;
	}

}
