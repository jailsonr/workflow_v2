package cl.security.utils;

import java.util.HashMap;

import cl.security.status.strategy.StatusStrategy;
import cl.security.status.strategy.status.KGRStatus;
import cl.security.status.strategy.status.KondorStatus;
import cl.security.status.strategy.status.MLSStatus;

public final class LoaderUtil {

	public static void main(String[] args) {
//		try {
//			
//			for (Map.Entry<String, StatusStrategy> e: getClassesForPackage("cl.security.status.strategy.status").entrySet()) {
//				System.out.println(e.getKey());
//			}
//			
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static final HashMap<String, StatusStrategy> getInstatiatedStatusClasses()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// This will hold a list of directories matching the pckgname. There may be more
		// than one if a package is split over multiple jars/paths
//		String pckgname = "cl.security.status.strategy.status";
//        ArrayList<File> directories = new ArrayList<File>();
//        try {
//            ClassLoader cld = Thread.currentThread().getContextClassLoader();
//            if (cld == null) {
//                throw new ClassNotFoundException("Can't get class loader.");
//            }
//            String path = pckgname.replace('.', '/');
//            //String path = pckgname;
//            // Ask for all resources for the path
//            Enumeration<URL> resources = cld.getResources(path);
//            while (resources.hasMoreElements()) {
//                directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
//            }
//        } catch (NullPointerException x) {
//            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Null pointer exception)");
//        } catch (UnsupportedEncodingException encex) {
//            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package (Unsupported encoding)");
//        } catch (IOException ioex) {
//            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
//        }
//
//        HashMap<String, StatusStrategy> classes = new HashMap<String, StatusStrategy>();
//        // For every directory identified capture all the .class files
//        for (File directory : directories) {
//            if (directory.exists()) {
//                // Get the list of the files contained in the package
//                String[] files = directory.list();
//                for (String file : files) {
//                    // we are only interested in .class files
//                    if (file.endsWith(".class")) {
//                        // removes the .class extension
//                      try
//                      {
//                        classes.put(file.replace("Status", "").toLowerCase().split("\\.")[0], (StatusStrategy) Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)).newInstance());                      
//                      }
//                      catch (NoClassDefFoundError e)
//                      {
//                        // do nothing. this class hasn't been found by the loader, and we don't care.
//                      }
//                    }
//                }
//            } else {
//                throw new ClassNotFoundException(pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
//            }
//        }

		HashMap<String, StatusStrategy> classes = new HashMap<String, StatusStrategy>();
		classes.put("kondor", new KondorStatus());
		classes.put("mls", new MLSStatus());
		classes.put("kgr", new KGRStatus());
		return classes;
	}

}
