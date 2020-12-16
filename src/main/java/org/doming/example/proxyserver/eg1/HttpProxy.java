package org.doming.example.proxyserver.eg1;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * @Description: 一个基础的代理服务器类
 * @Author: Dongming
 * @Date: 2018-10-17 17:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpProxy extends Thread {
	static public int CONNECT_RETRIES = 5;
	static public int CONNECT_PAUSE = 5;
	static public int TIMEOUT = 50;
	static public int BUFSIZ = 1024;
	static public boolean logging = false;
	static public OutputStream log = null;

	// 传入数据用的Socket

	protected Socket socket;

	// 上级代理服务器，可选

	static private String parent = null;
	static private int parentPort = -1;
	static public void setParentProxy(String name, int pport) {
		parent = name;
		parentPort = pport;
	}
	//
	/**
	 * 在给定Socket上创建一个代理线程。
	 *
	 * @param s
	 */
	public HttpProxy(Socket s) {
		socket = s;
		start();
	}
	public void writeLog(int c, boolean browser) throws IOException {
		log.write(c);
	}
	public void writeLog(byte[] bytes, int offset, int len, boolean browser) throws IOException {
		for (int i = 0; i < len; i++) {
			writeLog((int) bytes[offset + i], browser);
		}
	}

	/**
	 * 默认情况下，日志信息输出到,标准输出设备，派生类可以覆盖它
	 *
	 * @param url
	 * @param host
	 * @param port
	 * @param sock
	 * @return
	 */
	public String processHostName(String url, String host, int port, Socket sock) {
		java.text.DateFormat cal = java.text.DateFormat.getDateTimeInstance();
		System.out.println(cal.format(new java.util.Date()) + " - " + url + " " + sock.getInetAddress() + "<BR>");
		return host;
	}

	@Override public void run() {
		String line;
		String host;
		int port = 80;
		Socket outbound = null;
		try {
			socket.setSoTimeout(TIMEOUT);
			InputStream is = socket.getInputStream();
			OutputStream os = null;
			try {
				// 获取请求行的内容
				line = "";
				host = "";
				int state = 0;
				boolean space;
				while (true) {
					int c = is.read();
					if (c == -1){
						break;
					}
					if (logging){
						writeLog(c, true);
					}
					space = Character.isWhitespace((char) c);
					switch (state) {
						case 0:
							if (space){
								continue;
							}
							state = 1;
						case 1:
							if (space) {
								state = 2;
								continue;
							}
							line = line + (char) c;
							break;
						case 2:
							if (space)
								continue; // 跳过多个空白字符
							state = 3;
						case 3:
							if (space) {
								state = 4;
								// 只分析主机名称部分
								String host0 = host;
								int n;
								n = host.indexOf("//");
								if (n != -1) {
									host = host.substring(n + 2);
								}
								n = host.indexOf('/');
								if (n != -1){
									host = host.substring(0, n);
								}
								// 分析可能存在的端口号
								n = host.indexOf(":");
								if (n != -1) {
									port = Integer.parseInt(host.substring(n + 1));
									host = host.substring(0, n);
								}
								host = processHostName(host0, host, port, socket);
								if (parent != null) {
									host = parent;
									port = parentPort;
								}
								int retry = CONNECT_RETRIES;
								while (retry-- != 0) {
									try {
										outbound = new Socket(host, port);
										break;
									} catch (Exception e) {
									}
									// 等待
									Thread.sleep(CONNECT_PAUSE);
								}
								if (outbound == null){
									break;
								}
								outbound.setSoTimeout(TIMEOUT);
								os = outbound.getOutputStream();
								os.write(line.getBytes());
								os.write(' ');
								os.write(host0.getBytes());
								os.write(' ');
//								pipe(is, outbound.getInputStream(), os, socket.getOutputStream());
								break;
							}
							host = host + (char) c;
							break;
					}
				}
			} catch (IOException e) {
			}
		} catch (Exception e) {
		} finally {
			try {
				socket.close();
			} catch (Exception e1) {
			}
			try {
				outbound.close();
			} catch (Exception e2) {
			}
		}
	}

}
