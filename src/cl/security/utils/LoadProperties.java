package cl.security.utils;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;

public class LoadProperties {
	public HashMap getProperties(String name, Logger log) {
		FileInputStream file = null;

		try {
			file = new FileInputStream(name);
			Properties propers = new Properties();

			try {
				propers.load(file);

				try {
					file.close();
				} catch (IOException ex) {

					ex.getStackTrace();
					log.info("Problemas al Leer las Propiedades. " + ex.getMessage());
					
				}
				
				if (propers.isEmpty()) {
					
					log.info("Propiedades vacias");
					
				}
				
			} catch (IOException ex) {

				ex.getStackTrace();
				log.info("Problemas al Leer las Propiedades. " + ex.getMessage());
				
			} finally {

				try {
					
					file.close();
					
				} catch (IOException ex) {

					ex.getStackTrace();
					log.info("Problemas al Leer las Propiedades. " + ex.getMessage());
					
				}
				
			}
			
			return new HashMap<Object, Object>(propers);
			
		} catch (FileNotFoundException ex) {
			
			ex.getStackTrace();
			log.error("El archivo de Propiedades no existe. " + ex.getMessage());
			
			return null;
			
		} catch (IOException e) {

			e.getStackTrace();
			log.error("Problemas al Leer las Propiedades. " + e.getMessage());
			
			return null;
			
		} catch (NullPointerException nulo) {

			nulo.getStackTrace();
			log.error("Problemas al Leer las Propiedades. " + nulo.getMessage());

			return null;
			
		} catch (Exception excep) {

			excep.getStackTrace();
			log.error("Problemas al Leer las Propiedades. " + excep.getMessage());
			
			return null;
		}
	}
}
