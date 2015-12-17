package go.zlslog;

public class LogModel {

	private String cmd;
	private String app_id;
	private String userid;
	private String duration;
	private String ok;
	private String client_addr;
	private String server_addr;
	private String ver_full;
	private String ver;
	private String vc;
	private String vd;
	private String dbqcnt;
	private String mccnt;
	private String mongocnt;
	private String memory;
	private String params; 
	private String version;
	private String timestamp; 
	private String request_date;
	private String request_hour;
	private String platform;
	private String os;
	private String inserttime;
	private String line;
	private String logDate;
	private String request_time;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getClient_addr() {
		return client_addr;
	}

	public void setClient_addr(String client_addr) {
		this.client_addr = client_addr;
	}

	public String getServer_addr() {
		return server_addr;
	}

	public void setServer_addr(String server_addr) {
		this.server_addr = server_addr;
	}

	public String getVer_full() {
		return ver_full;
	}

	public void setVer_full(String ver_full) {
		this.ver_full = ver_full;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getVc() {
		return vc;
	}

	public void setVc(String vc) {
		this.vc = vc;
	}

	public String getVd() {
		return vd;
	}

	public void setVd(String vd) {
		this.vd = vd;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getDbqcnt() {
		return dbqcnt;
	}

	public void setDbqcnt(String dbqcnt) {
		this.dbqcnt = dbqcnt;
	}

	public String getMccnt() {
		return mccnt;
	}

	public void setMccnt(String mccnt) {
		this.mccnt = mccnt;
	}

	public String getMongocnt() {
		return mongocnt;
	}

	public void setMongocnt(String mongocnt) {
		this.mongocnt = mongocnt;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public LogModel() {
		super();
	}

	public String getRequest_date() {
		return request_date;
	}

	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}

	public String getRequest_hour() {
		return request_hour;
	}

	public void setRequest_hour(String request_hour) {
		this.request_hour = request_hour;
	}

	public String getInserttime() {
		return inserttime;
	}

	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}

	@Override
	public String toString() {
		StringBuffer log = new StringBuffer();
		log.append(cmd).append("\001").append(app_id).append("\001").append(userid).append("\001").append(duration)
				.append("\001").append(ok).append("\001").append(client_addr).append("\001").append(server_addr)
				.append("\001").append(ver_full).append("\001").append(ver).append("\001").append(vc).append("\001")
				.append(vd).append("\001").append(dbqcnt).append("\001").append(mccnt).append("\001").append(mongocnt)
				.append("\001").append(memory).append("\001").append(params).append("\001")
				.append(version).append("\001").append(timestamp).append("\001").append(platform).append("\001").append(os)
				.append("\001").append(line).append("\001").append(logDate).append("\001").append(request_time).append("\001")
				.append(request_date).append("\001").append(request_hour).append("\001").append(inserttime).append("\001");
		return log.toString();
	}

}
