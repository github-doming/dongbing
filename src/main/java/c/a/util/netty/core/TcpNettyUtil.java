package c.a.util.netty.core;
import io.netty.channel.ChannelHandlerContext;
public class TcpNettyUtil extends TcpNettyCoreUtil{
	private static TcpNettyUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private TcpNettyUtil() {
	}
	public static TcpNettyUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new TcpNettyUtil();
				}
			}
		}
		return instance;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
	}
}
