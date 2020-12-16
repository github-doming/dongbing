package c.a.util.netty.core;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanSingletonUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.netty.config.TcpNettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
public class TcpNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		if (false) {
			// 没有解决粘包、拆包
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("chat", new TcpNettyServerChannelInboundHandler());
		}
		if (false) {
			// 解决粘包、拆包;加上换行符;
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			channelPipeline.addLast(new LineBasedFrameDecoder(1024 * 1024 * 10));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("chat", new TcpNettyServerChannelInboundHandler());
		}
		if (true) {
			// 解决粘包、拆包;加上delimiter;
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			ByteBuf delimiter = Unpooled.copiedBuffer(TcpNettyConfig.tail.getBytes());
			channelPipeline.addLast(new DelimiterBasedFrameDecoder(TcpNettyConfig.clientReceiveBufferSize, delimiter));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			// 当服务端每隔一段时间就会向客户端发送心跳包，客户端收到心跳包后同样也会回一个心跳包给服务端
			// 一般情况下，客户端与服务端在指定时间内没有任何读写请求，就会认为连接是idle（空闲的）的。
			// 此时，客户端需要向服务端发送心跳消息，来维持服务端与客户端的链接。
			// 那么怎么判断客户端在指定时间里没有任何读写请求呢？
			// netty中为我们提供一个特别好用的IdleStateHandler来干这个苦差事！
			// 这个处理器，它的作用就是用来检测客户端的读取超时的，
			// 该类的第一个参数是指定读操作空闲秒数，第二个参数是指定写操作的空闲秒数，
			// 第三个参数是指定读写空闲秒数，当有操作操作超出指定空闲秒数时，
			// 便会触发UserEventTriggered事件。所以我们只需要在自己的handler中截获该事件，
			// 然后发起相应的操作即可（比如说发起心跳操作）。以下是我们自定义的handler中的代码：
			// 设定IdleStateHandler心跳检测每25秒进行一次读检测

			// 也就是说 服务端在10s内未进行读写操作，就会向客户端发送心跳包，
			// 客户端收到心跳包后立即回复心跳包给服务端，此时服务端就进行了读操作，
			// 也就不会触发IdleState.READER_IDLE（未读操作状态），若客户端异常掉线了，
			// 并不能响应服务端发来的心跳包，在25s后就会触发IdleState.READER_IDLE（未读操作状态），
			// 此时服务器就会将通道关闭

			long readerIdleTime = BeanSingletonUtil.findInstance()
					.find(SysConfig.findInstance().findMap().get("netty.local.readerIdleTime"), 25l);
			long writerIdleTime = BeanSingletonUtil.findInstance()
					.find(SysConfig.findInstance().findMap().get("netty.local.writerIdleTime"), 15l);
			long allIdleTime = BeanSingletonUtil.findInstance()
					.find(SysConfig.findInstance().findMap().get("netty.local.allIdleTime"), 10);
			channelPipeline.addLast("ping",
					new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.SECONDS));
			// channelPipeline.addLast("ping", new IdleStateHandler(60+25,
			// 60+15, 60+10, TimeUnit.SECONDS));

			channelPipeline.addLast("chat", new TcpNettyServerChannelInboundHandler());
		}
		if (false) {
			// 将消息划分为消息头和消息体，消息头中包含消息总长度的字段。
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			// channelPipeline.addLast("frameEncode", new
			// LengthFieldPrepender(4, false));
			channelPipeline.addLast("frameDecode", new LengthFieldPrepender(4, false));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("chat", new TcpNettyServerChannelInboundHandler());
		}
		if (false) {
			// 将消息划分为消息头和消息体，消息头中包含消息总长度的字段。
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			// channelPipeline.addLast(new
			// DelimiterBasedFrameDecoder(TcpNettyConfig.clientReceiveBufferSize,
			// delimiter));
			// 入参有五个参数，分别解释如下：
			//
			//
			//
			// maxFrameLength：单个包最大的长度，这个值根据实际场景而定，
			// 我设置的是1024，固然我的心跳包不大，但是其他包可能比较大。
			//
			//
			// lengthFieldOffset：表示数据长度字段开始的偏移量，比如上面的协议，lengthFieldOffset应该取值为5，
			// 因为数据长度之前有2个字节的包头，1个字节的功能ID，2个字节的设备ID,一共为5。
			//
			//
			// lengthFieldLength：数据长度字段的所占的字节数，上面的协议中写的是2个字节，所以取值为2
			//
			//
			// lengthAdjustment：这里取值为10=7(系统时间) + 1（校验码）+ 2 (包尾)，如果这个值取值为0，
			// 试想一下，解码器跟数据长度字段的取值（这里数据长度内容肯定是1），只向后取一个字节，肯定不对。
			//
			// （lengthAdjustment + 数据长度取值 = 数据长度字段之后剩下包的字节数）
			//
			//
			// initialBytesToStrip：表示从整个包第一个字节开始，向后忽略的字节数，
			// 我设置为0，本来可以忽略掉包头的两个字节（即设置为2），
			// 但是，实际项目中，需要校验包头取值是否为AA55，来判断包的合法性。
			//
			// （1） maxFrameLength - 发送的数据包最大长度；
			//
			// （2） lengthFieldOffset - 长度域偏移量，指的是长度域位于整个数据包字节数组中的下标；
			//
			// （3） lengthFieldLength - 长度域的自己的字节数长度。
			//
			// （4） lengthAdjustment – 长度域的偏移量矫正。 如果长度域的值，除了包含有效数据域的长度外，
			// 还包含了其他域（如长度域自身）长度，那么，就需要进行矫正。
			// 矫正的值为：包长 - 长度域的值 – 长度域偏移 – 长度域长。
			//
			// （5） initialBytesToStrip – 丢弃的起始字节数。丢弃处于有效数据前面的字节数量。
			// 比如前面有4个节点的长度域，则它的值为4。
			//
			channelPipeline.addLast("frameDecode",
					new LengthFieldBasedFrameDecoder(TcpNettyConfig.clientReceiveBufferSize, 0, 4, 0, 0));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("chat", new TcpNettyServerChannelInboundHandler());
		}
	}
}
