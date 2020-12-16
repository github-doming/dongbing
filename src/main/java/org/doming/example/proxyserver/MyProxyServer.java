package org.doming.example.proxyserver;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
/**
 * @Description: java实现代理服务器,接收客户端连接,发送到对应服务器
 * @Author: Dongming
 * @Date: 2018-10-17 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MyProxyServer {
	public static void main(String args[]) {
		try {
			ServerSocket ss = new ServerSocket(8080);
			System.out.println("服务器启动....");
			while (true) {
				Socket s = ss.accept();
				Process p = new Process(s);
				Thread t = new Thread(p);
				t.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
class Process implements Runnable {
	Socket s;
	public Process(Socket s1) {
		s = s1;
	}
	@Override public void run() {
		try {
			// 从Socket中获得输入
			PrintStream out = new PrintStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String info = in.readLine();
			// 分析请求字符串,得到URL
			System.out.println("已经获取到 " + info);
			int sp1 = info.indexOf(' ');
			int sp2 = info.indexOf(' ', sp1 + 1);
			String gotourl = info.substring(sp1, sp2);
			System.out.println("正在连接到 " + gotourl);
			URL con = new URL(gotourl);
			InputStream gotoin = con.openStream();
			int n = gotoin.available();
			out.println("HTTP/1.0 200 OK");
			out.println("MIME_version:1.0");
			out.println("Content_Type:text/html");
			out.println("Content_Length:" + n);
			out.println(" ");
			byte buf[] = new byte[1024];
			while ((n = gotoin.read(buf)) >= 0) {
				out.write(buf, 0, n);
			}
			out.close();
			s.close();
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}
}
