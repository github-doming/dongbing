package c.x.all.simple.nio.channels.file.example.e1.client;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * @author liuyan
 * 
 */
public class NIOClient {
	static int SIZE = 2;
	final static int bufferSize = 500 * 1024;
	static InetSocketAddress ip = new InetSocketAddress("localhost", 12345);
	static CharsetEncoder encoder = Charset.forName("GB2312").newEncoder();
	static class Download implements Runnable {
		protected int index;
		String outfile = null;
		public Download(int index) {
			this.index = index;
			this.outfile = "c:\\" + index + ".rmvb";
		}
		public void run() {
			FileOutputStream fout = null;
			// FileChannel fcout = null;
			try {
				fout = new FileOutputStream(outfile);
				// fcout = fout.getChannel();
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}
			try {
				long start = System.currentTimeMillis();
				// 打开客户端socket管道
				SocketChannel client = SocketChannel.open();
				// 客户端的管道的通讯模式
				client.configureBlocking(false);
				// 选择器
				Selector selector = Selector.open();
				// 往客户端管道上注册感兴趣的连接事件
				client.register(selector, SelectionKey.OP_CONNECT);
				// 配置IP
				client.connect(ip);
				// 配置缓存大小
				ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
				int total = 0;
				FOR : for (;;) {
					// 阻塞，返回发生感兴趣事件的数量
					selector.select();
					// 相当于获得感兴趣事件的集合迭代
					Iterator<SelectionKey> iter = selector.selectedKeys()
							.iterator();
					while (iter.hasNext()) {
						SelectionKey key = iter.next();
						System.out.println("-----Thread " + index
								+ "------------------" + key.readyOps());
						// 删除这个马上就要被处理的事件
						iter.remove();
						// 感兴趣的是可连接的事件
						if (key.isConnectable()) {
							// 获得该事件中的管道对象
							SocketChannel channel = (SocketChannel) key
									.channel();
							// 如果该管道对象已经连接好了
							if (channel.isConnectionPending())
								channel.finishConnect();
							// channel.write(encoder.encode(CharBuffer
							// .wrap("d://film//" + index + ".mp3")));
							// 往管道中写一些块信息
							channel.write(encoder.encode(CharBuffer
									.wrap("d://film//1.rmvb")));
							// 之后为该客户端管道注册新的感兴趣的事件---读操作
							channel.register(selector, SelectionKey.OP_READ);
						} else if (key.isReadable()) {
							// 由事件获得通讯管道
							SocketChannel channel = (SocketChannel) key
									.channel();
							// 从管道中读取数据放到缓存中
							int count = channel.read(buffer);
							System.out.println("count:" + count);
							if (count > 0) {
								// 统计读取的字节数目
								total += count;
								// 这样一来从posistion~limit这段缓存数据是有效，可利用的
								// buffer.flip();
								buffer.clear();
								// 往输出文件中去写了
								if (count < bufferSize) {
									byte[] overByte = new byte[count];
									for (int index = 0; index < count; index++) {
										overByte[index] = buffer.get(index);
									}
									fout.write(overByte);
									// System.out.println(":::"
									// + new String(buffer.array()));
								} else {
									fout.write(buffer.array());
									// System.out.println(":::"
									// + new String(buffer.array()));
								}
							} else {
								// 关闭客户端通道
								client.close();
								// 退出大循环
								break FOR;
							}
							// ？
							// buffer.clear();
						}
					}
				}
				// 计算时间
				double last = (System.currentTimeMillis() - start) * 1.0 / 1000;
				System.out.println("Thread " + index + " downloaded " + total
						/ 1024 + "kbytes in " + last + "s.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		// 启用线程池
		ExecutorService exec = Executors.newFixedThreadPool(SIZE);
		for (int index = 1; index <= SIZE; index++) {
			exec.execute(new Download(index));
		}
		exec.shutdown();
		long endTime = System.currentTimeMillis();
		long timeLong = endTime - startTime;
		System.out.println("下载时间：" + timeLong);
	}
}
