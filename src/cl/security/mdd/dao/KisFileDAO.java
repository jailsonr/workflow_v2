package cl.security.mdd.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.security.database.DatabaseConnection;
import cl.security.database.utils.QueryEnum;
import cl.security.model.KISFile;
import cl.security.utils.Constants;

public class KisFileDAO {

	Logger log = Logger.getLogger(KisFileDAO.class);

	public static final String DATE_FORMAT = "dd-MM-yyyy_HH:mm:ss.SSSSSS";
	private String fileName;
	private String routeKisFile;
	private String routeTemplates;
	private int paramId;
	private String paramName;
	private String paramValue;
	private int dealId;
	private HashMap<Integer, KISFile> mapas;

	public String importFile(int dealId, int kdbTableId, int KGRStatus, String KGRRequest) {

		this.dealId = dealId;
		final FileWriter fw = null;
		final PrintWriter salida = null;
		KGRRequest = null;

		final String storeProcedure = QueryEnum.IMPORT_FILE.query;

		ResultSet rs = null;

		try (CallableStatement cs = DatabaseConnection.getInstance().getConnection().prepareCall(storeProcedure);) {

			cs.setInt(1, kdbTableId);
			cs.setInt(2, dealId);
			cs.setString(3, KGRRequest);

			log.info("Executed " + storeProcedure + " " + kdbTableId + ", " + dealId + ", " + KGRRequest);
			System.out.println("Executed " + storeProcedure + " " + kdbTableId + ", " + dealId + ", " + KGRRequest);

			rs = cs.executeQuery();

			int i = 0;
			this.mapas = new HashMap<Integer, KISFile>();

			if (rs != null) {
				while (rs.next()) {

					this.paramId = rs.getInt("ParamId");
					this.paramName = rs.getString("ParamName").trim();
					this.paramValue = rs.getString("ParamValue").trim();
					final KISFile parametros = new KISFile();
					parametros.setParamId(this.paramId);
					parametros.setParamName(this.paramName);
					parametros.setParamValue(this.paramValue);
					this.mapas.put(i, parametros);
					++i;

				}
			}

		} catch (SQLException e2) {

			e2.printStackTrace();
			log.error("Not executed " + storeProcedure + " " + kdbTableId + ", " + dealId + ", " + KGRRequest
					+ ".Error: " + e2.getMessage());
			System.out.println("Not executed " + storeProcedure + " " + kdbTableId + ", " + dealId + ", " + KGRRequest
					+ ".Error: " + e2.getMessage());

		}

		try {

			this.fileName = this.createKISFile(this.mapas, dealId);

			log.info("CREATE KISFILE" + " " + kdbTableId + "," + dealId);
			
		} catch (IOException e4) {
			
			e4.printStackTrace();
			log.error("CREATE KISFILE" + " " + kdbTableId + "," + dealId + " ERROR: " + e4.getMessage());
		}

		return this.fileName;

	}

	public String createKISFile(HashMap<Integer, KISFile> mapa, int dealId) throws IOException {

		String template = null;
		String dealId2 = null;

		if (mapa.size() == 3) {

			for (final Map.Entry<Integer, KISFile> e : mapa.entrySet()) {

				final Integer Id = e.getKey();
				final KISFile file = e.getValue();
				final int getParamId = file.getParamId();
				final String getParamName = file.getParamName();
				final String getParamValue = file.getParamValue();

				System.out.println("File: " + file);

				if (getParamName.equals("NbParams")) {

					final String nbParams = getParamValue;
					System.out.println(nbParams);
					log.info(nbParams);

				} else if (getParamName.equals("Template")) {

					template = getParamValue;
					log.info("Template: " + template);

				} else {

					if (!getParamName.equals("DealId")) {

						log.info("DealId: " + getParamName);
						continue;

					}

					dealId2 = getParamValue;
					log.info("DealId2: " + dealId2);

				}

			}

		}

		this.routeKisFile = Constants.ROUTEKIS;
		this.routeTemplates = Constants.ROUTETEMPLATES;
		final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSSSSS");
		final Calendar cal = Calendar.getInstance();
		final String Date = sdf.format(cal.getTime());
		final String FileNameCreate = String.valueOf(this.routeKisFile) + template + "_" + dealId + "_"
				+ Date.replace(":", "_");
		BufferedReader reader = null;
		PrintWriter writer = null;
		String line = null;
		final File In = new File(String.valueOf(this.routeTemplates) + template);
		log.debug(routeKisFile);
		log.debug(routeTemplates);
		final File Out = new File(FileNameCreate);

		try {

			reader = new BufferedReader(new FileReader(In));
			writer = new PrintWriter(new FileWriter(Out));

			while ((line = reader.readLine()) != null) {

				writer.println(line.replaceAll("#DealId#", dealId2));

			}

		} catch (FileNotFoundException e2) {

			e2.printStackTrace();
			log.error(e2.getMessage());

		}

		try {

			reader.close();

		} catch (IOException e3) {

			e3.printStackTrace();
			log.error(e3.getMessage());

		}

		writer.close();
		return FileNameCreate;

	}

	public int getKISDealId(int kdbTableId, int dealId) {
		
		final String storeProcedure = QueryEnum.GET_KIS_DEAL_ID.query;
		int dealsIdOut = 0;
		
		try (CallableStatement cs = DatabaseConnection.getInstance().getConnection().prepareCall(storeProcedure);) {
			
			cs.setInt(1, kdbTableId);
			cs.setInt(2, dealId);
			cs.registerOutParameter(3, 4);

			log.info("Executed " + storeProcedure + " " + kdbTableId + ", " + dealId);
			System.out.println("Executed " + storeProcedure + " " + kdbTableId + ", " + dealId);

			cs.execute();
			dealsIdOut = cs.getInt(3);

		} catch (SQLException e2) {
			
			e2.printStackTrace();
			dealsIdOut = dealId;
			log.info("DealIdOUTPUT: " + dealsIdOut + " Error: " + e2.getMessage());
			
		}
		return dealsIdOut;
	}
}
