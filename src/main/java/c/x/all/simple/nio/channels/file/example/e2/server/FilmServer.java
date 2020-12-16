package c.x.all.simple.nio.channels.file.example.e2.server;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import c.a.tools.log.custom.common.BaseLog;
/**
 * 
 * 1
 * 
 * 60m文件 服务端 花费时间t=1888
 * 
 * 服务端 花费时间t=1593
 * 
 * 客户端 花费时间t=1919
 * 
 * 客户端 花费时间t=3528
 * 
 * 2 500m文件
 * 
 * 客户端 花费时间t=31638
 * 
 * 客户端 花费时间t=75679
 * 
 * 服务端 花费时间t=31607
 * 
 * 
 * 服务端 花费时间t=44009
 */
public class FilmServer {
	public static void main(String[] args) {
		FilmServer ms = new FilmServer();
		try {
			ms.server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 服务器端响应请求
	 * 
	 * @throws Exception
	 */
	public void server() throws Exception {
		// 0.建立服务器端的server的socket
		ServerSocket ss = new ServerSocket(9999);
		while (true) {
			// 1.打开socket连接
			// 等待客户端的请求
			final Socket server = ss.accept();
			// 时间计算
			Calendar calendar = Calendar.getInstance();
			long start = calendar.getTimeInMillis();
			// 执行业务
			System.out.println("服务-----------请求开始start");
			// 2.打开socket的流信息，准备下面的操作
			final InputStream is = server.getInputStream();
			byte b[] = new byte[1024];
			int readCount = is.read(b);
			String str = new String(b);
			str = str.trim();
			final String serverFileName = str;
			// 3.对流信息进行读写操作
			System.out.println("客户端传过来的信息是：" + str);
			System.out.println("线程" + Thread.currentThread().getName() + "启动");
			try {
				FileInputStream fileInputStream = new FileInputStream(
						serverFileName);
				// 3.1 服务器回复客户端信息(response)
				OutputStream os = server.getOutputStream();
				byte[] bfile = new byte[1024];
				// 往客户端写
				while (fileInputStream.read(bfile) > 0) {
					os.write(bfile);
				}
				fileInputStream.close();
				os.close();
				// 4.关闭socket
				// 先关闭输入流
				is.close();
				// 最后关闭socket
				server.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
			System.out.println("服务-----------请求结束over");
			// 时间计算
			calendar = Calendar.getInstance();
			long end = calendar.getTimeInMillis();
			long t = end - start;
			BaseLog.trace("服务端 花费时间t=" + t);
		}
	}
}
