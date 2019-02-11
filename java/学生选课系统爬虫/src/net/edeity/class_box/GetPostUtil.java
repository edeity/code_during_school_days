package net.edeity.class_box;
import java.io.*;
import java.net.*;

/**
 * Post �� Get���������Ĵ����ܽ�
 * 
 * @author javer
 * 
 */
public class GetPostUtil {
	private static URL connectedUrl;		
	private static String untreatedCookie;
	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param params
	 *            �������,�������Ӧ��Ϊ name1 = value1 & name2= value2����ʽ
	 * @param cookie
	 * 				cookieΪ��ȡ��Դ����Ҫ��cookie,���粻��Ҫcookie,�ɴ�����null
	 * @return String result ������Զ����Դ����Ӧ(һ��Ϊhtml�ĵ�)
	 */
	static String sendGet(String url, String params, String cookie) {
		String result = "";	
		BufferedReader in = null;
		try {
			if (params != null) {
				url = url + "?" + params;
			}
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			if (cookie != null)
				conn.setRequestProperty("cookie", cookie);
			// ����ʵ�ʵ�����
			conn.connect();
			connectedUrl = conn.getURL();
			// �������е���Ӧͷ�ֶ�
			// ����BufferedReader����������ȡURL����Ӧ, GB2312�����ǹ���
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GB2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣!" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {	in.close(); }
				} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��ָ��URL����POST����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param params
	 *            �������,�������Ӧ��Ϊ name1 = value1 & name2 = value2����ʽ
	 * @return URL ������Զ����Դ����Ӧ
	 */
	static String sendPost(String url, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpURLConnection.setFollowRedirects(false);// �ض�����Ϊfalse,Ϊ��cookie������ԭ������վ��ȡ
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(params);
			// flush������Ļ���
			out.flush();
			untreatedCookie = conn.getHeaderField("Set-Cookie");
		} catch (Exception e) {
			System.out.println("����POST��������쳣! :  " + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������, �����
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * @return  	 ������һ�����ӵ�URL
	 */
	static URL getConnectedUrl() {
		return connectedUrl;
	}
	/**
	 * @return ��Ч��cookieֵ,���緵�ص���null, ֤��cookieδ����Ч��ȡ
	 */
	static String getCookie() {
		if(untreatedCookie != "" && untreatedCookie != null)
			return untreatedCookie.substring(0, 30);
		else
			return null;
	}
}
