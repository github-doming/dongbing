package c.x.all.simple.nio.channels.file.example.e1.client;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * liuyan
 * 
 * @version 1.0
 */
public class FilmClient {
	public static void main(String[] args) {
		for (int i = 1; i <= 2; i++) {
			Client client = new Client();
			client.i = i;
			client.start();
		}
	}
}
class Client extends Thread {
	int i;
	@Override
	public void run() {
		// 1.建立scoket连接
		Socket client;
		try {
			client = new Socket("127.0.0.1", 9999);
			// 2.打开socket的流信息，准备下面的操作
			OutputStream os = client.getOutputStream();
			// 3.写信息
			os.write(("d://film//2.rmvb").getBytes());
			String filmName = "c://io" + i + ".rmvb";
			FileOutputStream fileOutputStream = new FileOutputStream(filmName);
			// 3.1接收服务器端的反馈
			InputStream is = client.getInputStream();
			byte b[] = new byte[1024];
			while (is.read(b) > 0) {
				fileOutputStream.write(b);
			}
			// 4.关闭socket
			// 先关闭输出流
			os.close();
			// 最后关闭socket
			client.close();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
