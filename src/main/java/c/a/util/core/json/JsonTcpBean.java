package c.a.util.core.json;
import io.netty.channel.Channel;
/**
 * 返回的Json格式字符串,包括是否成功的状态及返回的格式字符串
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JsonTcpBean extends JsonResultBean{
	private String channelId;
	private Channel channel;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public JsonTcpBean() {
		super();
	}
	public JsonTcpBean(boolean success, String code, String msg) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
	}
	public JsonTcpBean(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}
}