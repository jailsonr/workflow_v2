package cl.security.utils;

import java.util.HashMap;

import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;
import cl.security.status.strategy.status.MLSStatus;

public final class LoaderUtil {

	public static void main(String[] args) {
	}

	public static final HashMap<String, StatusStrategy> getInstatiatedStatusClasses()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		HashMap<String, StatusStrategy> classes = new HashMap<String, StatusStrategy>();
		classes.put("kondor", new KondorStatus());
		classes.put("mls", new MLSStatus());
		classes.put("kgr", new KGRStatus());
		return classes;

	}

}
