package c.a.util.netty.core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class TcpNettyServerBoot {
	protected Logger log = LogManager.getLogger("netty");
	private int port;
	public TcpNettyServerBoot(int port) {
		this.port = port;
	}
	public static void main(String[] args) {
		new TcpNettyServerBoot(2000).run();
	}
	public void run() {
		EventLoopGroup acceptor = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		// 设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO
		bootstrap.group(acceptor, worker);
		// 用于构造socketchannel工厂
		bootstrap.channel(NioServerSocketChannel.class);
		// 为处理accept客户端的channel中的pipeline添加自定义处理函数
		bootstrap.childHandler(new TcpNettyServerChannelInitializer());
		if (true) {
			//bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535));

			// 设置缓存区大小20M
			// bootstrap.childAttr("child.receiveBufferSize",1024*1024 * 20);

			// bootstrap.childAttr("child.keepAlive", false);
			// bootstrap.childAttrn("child.tcpNoDelay", true);
		}
		try {
			// 服务器绑定端口监听
			Channel channel = bootstrap.bind(port).sync().channel();
			// log.trace("server strart running in port=" + port);
			log.info("server strart running in port=" + port);
			// 监听服务器关闭监听
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 退出
			acceptor.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
