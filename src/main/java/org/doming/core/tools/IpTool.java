package org.doming.core.tools;
import org.apache.commons.lang3.StringUtils;
import org.doming.develop.http.HttpTool;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-02-15 11:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class IpTool {

	/**
	 * 获取本地ip列表
	 *
	 * @return ip 列表组
	 */
	public static List<String> getLocalIpList() throws SocketException {
		List<String> ipList = new ArrayList<>();
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		NetworkInterface netInterface;
		Enumeration<InetAddress> addresses;
		InetAddress inetAddress;
		while (networkInterfaces.hasMoreElements()) {
			netInterface = networkInterfaces.nextElement();
			if (!netInterface.isLoopback() && !netInterface.isVirtual() && netInterface.isUp()) {
				addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					inetAddress = addresses.nextElement();
					if (inetAddress instanceof Inet4Address) {
						ipList.add(inetAddress.getHostAddress());
					}
				}
			}

		}
		return ipList;
	}
	/**
	 * 获取本机IP
	 * @return 本机IP
	 */
	public static String getIpAddress() throws SocketException {
		return getLocalIpList().get(0);
	}

	/**
	 * 获取外网IP
	 * @return 外网IP
	 */
	public static String getIpExtranet() throws IOException {
		String url = "http://pv.sohu.com/cityjson?ie=utf-8";
		String html  = HttpTool.doGet(url);
		String ip = StringUtils.substringBetween(html,"cip\": \"","\", \"cid");
		if (StringTool.isEmpty(ip)){
			url = "https://www.ip.cn/";
			html = HttpTool.doGet(url);
			ip = StringUtils.substringBetween(html,"<code>","</code>");
		}
		return StringTool.isEmpty(ip) ?  getIpAddress() : ip	;
	}
}
