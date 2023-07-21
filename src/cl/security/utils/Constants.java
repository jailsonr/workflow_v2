package cl.security.utils;

import org.apache.log4j.Logger;

import cl.security.utils.LoadProperties;

public final class Constants {
	
	private static final LoadProperties load = new LoadProperties();

	public static final Logger log = null;
	
	public static final String CONFIGURACION = "resources/config.properties";
	
	public static final String KPLUSIMPORT = "/kondor/configuracion/Workflow/config/KplusImport_WF.params";
	
	public static final String KRG = "kgr";
	
	public static final String MLS = "mls";
	
	public static final String KONDOR = "kondor";
	
	public static final String LOG4J = load.getProperties(CONFIGURACION, log).get("PropertiesLog4j").toString().trim();
	
	public static final String ROUTEKIS = load.getProperties(CONFIGURACION, log).get("RouteKisFile").toString().trim();
	
	public static final String ROUTETEMPLATES = load.getProperties(CONFIGURACION, log).get("RouteTemplates").toString().trim();
	
	public static final String RETRIES = load.getProperties(CONFIGURACION, log).get("Retries").toString().trim();

	public static final String HOST = load.getProperties(CONFIGURACION, log).get("Url").toString().trim();
	
	public static final String HOST2 = load.getProperties(CONFIGURACION, log).get("Url2").toString().trim();

	public static final String DRIVER = load.getProperties(CONFIGURACION, log).get("driver").toString().trim();

	public static final String USERKONDOR = load.getProperties(CONFIGURACION, log).get("userKondor").toString().trim();

	public static final String PASSWORDKONDOR = load.getProperties(CONFIGURACION, log).get("passwordKondor").toString().trim();

	public static final String USERKGR = load.getProperties(CONFIGURACION, log).get("userKGR").toString().trim();

	public static final String PASSWORDKGR = load.getProperties(CONFIGURACION, log).get("passwordKGR").toString().trim();

	public static final String URL = HOST + ";user=" + USERKONDOR + ";password=" + PASSWORDKONDOR;

	public static final String URL2 = HOST2 + ";user=" + USERKGR + ";password=" + PASSWORDKGR;

	public static final String APPLICATION_NAME = load.getProperties(CONFIGURACION, log).get("AppName").toString().trim();

	public static final String PROGRAM_NAME = load.getProperties(CONFIGURACION, log).get("ProgramName").toString().trim();
	
	public static final int INTERVAL_TIME = 5;
	
	
	
}
