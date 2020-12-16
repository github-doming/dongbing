package c.a.util.netty.bean;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
/**
 * 
 * 
 * @Description:
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class NettyUser {
	//所有通道与用户(退出时或断开连接移除)
	public static Map<String,String> channelUserAll=new HashMap<String,String> ();
	//所有用户与通道(退出时或断开连接不移除)
	public static Map<String,Channel> userChannelAll=new HashMap<String,Channel> ();
	//指定用户
	//public static List<String> userSend=new ArrayList<String> ();
}
