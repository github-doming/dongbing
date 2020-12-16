package c.a.util.netty.example.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogUtil;
import c.a.util.netty.config.TcpNettyConfig;
import c.a.util.netty.core.TcpNettyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
public class NettyClientMain1 {
	private static Logger log = LogManager.getLogger("custom");
	private String host;
	private int port;
	private boolean stop = false;
	public NettyClientMain1(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public static void main(String[] args) throws IOException {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}

		try {
			String servletContextPath = "d:\\";
			LogUtil.findInstance().init(servletContextPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new NettyClientMain1("127.0.0.1", 2000).run();
		// new NettyClientMain1("amuse.tzxyz.xyz", 2000).run();
	}
	public void run() throws IOException {
		// 设置一个worker线程，使用
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker);
		// 指定所使用的 NIO 传输 Channel
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new TcpNettyClientChannelInitializer());
		if (true) {
			// 加上这个，里面是最大接收、发送的长度
			bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535));
			// bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new
			// FixedRecvByteBufAllocator(10));
		}
		try {
			// 使用指定的 端口设置套 接字地址
			Channel channel = bootstrap.connect(host, port).sync().channel();

			// channel.writeAndFlush(input.getBytes("UTF-8"));
			// 客户端发送了100次数据,理论上服务器端应该收到100条数据。但实际上服务器只收到2条，很明显发生了粘包。
			for (int i = 1; i <= 100; i++) {

				log.debug("向服务端发送内容");
				// 向服务端发送内容
				JsonTcpBean jrb = new JsonTcpBean();

				jrb.setUrl("/all/netty/open.do");

				jrb.setData("abc用户");
				jrb.setAppUserId("cf67f4ba1a914f1ba90754d42d8c228a");

				jrb.setData("a1用户,i=" + i);
				String input = JsonThreadLocal.findThreadLocal().get().bean2json(jrb);
				// channel.writeAndFlush(input);
				channel.writeAndFlush(input + TcpNettyConfig.tail);
				// 在响应的数据后面添加分隔符
				// channel.writeAndFlush(Unpooled.wrappedBuffer((input+"$_").getBytes()));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
