package go.zlslog;

public class ZlsLogApi {
	public static void main(String[] args) throws Exception {
		String filePath =args[0];
		LogToModel.readFileByLines(filePath);
	}
}
