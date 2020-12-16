package c.a.util.netty.core;
import java.nio.charset.Charset;
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
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
public class TcpNettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		if (false) {
			// 没有解决粘包、拆包
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("http", new HttpClientCodec());
			channelPipeline.addLast("chat", new TcpNettyClientChannelInboundHandler());
		}
		if (false) {
			// 解决粘包、拆包;加上换行符;
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			channelPipeline.addLast(new LineBasedFrameDecoder(1024 * 1024 * 10));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("http", new HttpClientCodec());
			channelPipeline.addLast("chat", new TcpNettyClientChannelInboundHandler());
		}
		if (true) {
			// 解决粘包、拆包;加上delimiter;
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			ByteBuf delimiter = Unpooled.copiedBuffer(TcpNettyConfig.tail.getBytes());
			channelPipeline.addLast(new DelimiterBasedFrameDecoder(TcpNettyConfig.clientReceiveBufferSize, delimiter));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("http", new HttpClientCodec());
			channelPipeline.addLast("chat", new TcpNettyClientChannelInboundHandler());
		}
		if (false) {
			// 将消息划分为消息头和消息体，消息头中包含消息总长度的字段。
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			// channelPipeline.addLast("frameEncode", new
			// LengthFieldPrepender(4, false));
			channelPipeline.addLast("frameDecode", new LengthFieldPrepender(4, false));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("http", new HttpClientCodec());
			channelPipeline.addLast("chat", new TcpNettyClientChannelInboundHandler());
		}
		if (false) {
			// 将消息划分为消息头和消息体，消息头中包含消息总长度的字段。
			ChannelPipeline channelPipeline = socketChannel.pipeline();
			channelPipeline.addLast("frameDecode",
					new LengthFieldBasedFrameDecoder(TcpNettyConfig.clientReceiveBufferSize, 0, 4, 0, 0));
			channelPipeline.addLast("decode", new StringDecoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("encode", new StringEncoder(Charset.forName("UTF-8")));
			channelPipeline.addLast("http", new HttpClientCodec());
			channelPipeline.addLast("chat", new TcpNettyClientChannelInboundHandler());
		}
	}
}
