package go.zlslog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogToModel {

	/**
	 * @throws SQLException
	 * @throws ParseException
	 * @throws IOException
	 * 
	 */
	private static int Hourflag = 0;
	private static String Nextlog = "";
	static Logger logger = Logger.getLogger(LogToModel.class);

	public static void readFileByLines(String filePath) throws SQLException, ParseException, IOException {
		logger.info("start to read file");
		File file = new File(filePath);
		GetFirstHour(filePath);
		String logDate = filePath.substring(filePath.length() - 10);
		LineIterator it = FileUtils.lineIterator(file, "UTF-8");
		try {
			int line = 1;
			List<LogModel> logmodelList = new ArrayList();
			while (it.hasNext()) {
				String thisLine = it.nextLine();
				JSONArray jsonarray = new JSONArray("[" + thisLine + "]");
				for (int i = 0; i < jsonarray.length(); i++) {
					LogModel logmodel = new LogModel();
					JSONObject jsonobj = jsonarray.getJSONObject(i);
					try {
						// params
						String params = jsonobj.getString("params");
						if (params != null && !"".equals(params)) {
							String param[] = params.split("\\n");
							HashMap key = new HashMap();
							if (param.length > 0) {
								for (int j = 0; j < param.length; j++) {
									if (param[j].contains("=")) {
										if (param[j].split("=").length == 2) {
											key.put(param[j].split("=")[0], param[j].split("=")[1]);
										} else {
											key.put(param[j].split("=")[0], "");
										}

									} else {
										if (param[j].split(":").length == 2) {
											key.put(param[j].split(":")[0], param[j].split(":")[1]);
										} else {
											key.put(param[j].split(":")[0], "");
										}
									}
								}
								String paramJson = hashMapToJson(key);
								logmodel.setParams(paramJson);
							} else {
								logger.error("param doesn't have \n" + params);
							}
						} else {
							logmodel.setParams("");
						}
					} catch (Exception e) {
						logger.error(e.getMessage() + "&&&&&&" + e.toString());
						logger.error("error record:" + thisLine);
					}
					String ver_full = jsonobj.getString("ver_full");
					if (ver_full == null || "".equals(ver_full)) {
						logmodel.setPlatform("web");
						logmodel.setOs("\\N");
					} else if (ver_full.startsWith("t")) {
						logmodel.setPlatform("tzls");
						if (ver_full.startsWith("ti")) {
							logmodel.setOs("ios");
						} else {
							logmodel.setOs("android");
						}
					} else if (ver_full.startsWith("i")) {
						logmodel.setPlatform("zls");
						logmodel.setOs("ios");
					} else if (ver_full.startsWith("a")) {
						logmodel.setPlatform("zls");
						logmodel.setOs("android");
					} else if (ver_full.startsWith("m")) {
						logmodel.setPlatform("zls");
						logmodel.setOs("tv");
					} else if (ver_full.startsWith("p")) {
						logmodel.setPlatform("zls");
						logmodel.setOs("ipad");
					} else if (ver_full.startsWith("w")) {
						logmodel.setPlatform("zls");
						logmodel.setOs("iwatch");
					} else {
						logmodel.setPlatform("unknown");
						logmodel.setOs("unknown");
					}
					logmodel.setCmd(jsonobj.getString("cmd"));
					if (jsonobj.has("app_id")) {
						logmodel.setApp_id(jsonobj.getString("app_id"));
					} else {
						logmodel.setApp_id(jsonobj.getString("app_type"));
					}
					logmodel.setUserid(jsonobj.getInt("userid") + "");
					logmodel.setDuration(jsonobj.getDouble("duration") + "");
					logmodel.setOk(jsonobj.getInt("ok") + "");
					logmodel.setClient_addr(ToNull(jsonobj.getString("client_addr")));
					logmodel.setServer_addr(ToNull(jsonobj.getString("server_addr")));
					logmodel.setVer_full(ToNull(jsonobj.getString("ver_full")));
					logmodel.setVer(ToNull(jsonobj.getString("ver")));

					logmodel.setVc(ToNull(jsonobj.getString("vc")));
					logmodel.setVd(ToNull(jsonobj.getString("vd")));
					logmodel.setDbqcnt(jsonobj.getInt("dbqcnt") + "");
					logmodel.setMccnt(jsonobj.getInt("mccnt") + "");
					logmodel.setMongocnt(jsonobj.getInt("mongocnt") + "");
					logmodel.setMemory(jsonobj.getInt("memory") + "");
					logmodel.setVersion(ToNull(jsonobj.getString("@version")));
					logmodel.setTimestamp(ToNull(jsonobj.getString("@timestamp")));
					logmodel.setLine(line + "");
					logmodel.setRequest_time(jsonobj.getString("request_time"));
					logmodel.setRequest_date(jsonobj.getString("request_time").substring(0, 10));
					logmodel.setRequest_hour(jsonobj.getString("request_time").substring(11, 13));
					Date date = new Date();
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					logmodel.setInserttime(format.format(date).toString());
					logmodel.setLogDate(logDate);
					String basicPath = "/home/disk1/log/log_api_fmt/";
					String folderName = jsonobj.getString("request_time").substring(0, 10);
					File a = new File(basicPath + logDate);
					if (!a.exists()) {
						a.mkdir();
					}
					String logpath = basicPath + logDate + "/";
					int currenthour = Integer.parseInt(jsonobj.getString("request_time").substring(11, 13));

					FileWriter fw = new FileWriter(logpath + jsonobj.getString("request_time").substring(0, 10) + "-"
							+ jsonobj.getString("request_time").substring(11, 13), true);

					if (currenthour <= Hourflag) {
						if (currenthour == 1) {
							Hourflag = 1;
						}
						if (Nextlog != null || !"".equals(Nextlog)) {
							fw.write(Nextlog.toString());
							Nextlog = "";
						}
						fw.write(logmodel.toString());
						fw.write("\n");
						fw.flush();
						fw.close();
					} else {
						Hourflag++;
					}
					Nextlog = logmodel.toString();
				}

				line++;
			}
		} finally {
			LineIterator.closeQuietly(it);
		}
	}

	private static void GetFirstHour(String filePath) throws IOException {
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		File file = new File(filePath);
		InputStream inputStream = new FileInputStream(file);
		inputReader = new InputStreamReader(inputStream);
		bufferReader = new BufferedReader(inputReader);
		String line = null;
		StringBuffer strBuffer = new StringBuffer();
		while ((line = bufferReader.readLine()) != null) {
			strBuffer.append(line);
			break;
		}
		String nr = strBuffer.toString();
		JSONArray jsonarray = new JSONArray("[" + nr + "]");
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobj = jsonarray.getJSONObject(i);
			String firstHour = jsonobj.getString("request_time").substring(11, 13);
			Hourflag = Integer.parseInt(firstHour);
			return;
		}
	}

	public static String ToNull(String a) {
		if (a == null || "".equals(a)) {
			return "\\N";
		}
		return a;
	}

	public static String hashMapToJson(HashMap map) {
		String string = "{";
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Entry e = (Entry) it.next();
			string += "'" + e.getKey() + "':";
			string += "'" + e.getValue() + "',";
		}
		string = string.substring(0, string.lastIndexOf(","));
		string += "}";
		return string;
	}

	public static String getToString(String param[]) {
		StringBuffer a = new StringBuffer();
		for (int i = 0; i < param.length; i++) {
			a.append(param[i] + ",");
		}
		return a.toString().substring(0, a.toString().length() - 2);
	}

	public static void main(String[] args) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = "/home/disk1/log/log_api/aa";
		System.out.println(a.substring(0, 24));
		int hour = 01;
		hour++;
		System.out.println(hour);
		System.out.println(format.format(date).toString());
	}
}
