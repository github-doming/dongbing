package c.x.all.simple.sftp.jcraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import c.a.config.SysConfig;
import c.a.util.core.file.FileUtil;

/**
 * 文件工具类.<br>
 * 1.所有的文件路径必须以'/'开头和结尾，否则路径最后一部分会被当做是文件名<br>
 * 2.方法出现异常的时候，会关闭sftp连接（但是不会关闭session和channel），异常会抛出
 * 
 */
public class SftpUtil {
	protected static Logger log = LogManager.getLogger(SftpUtil.class);
	/**
	 * sftp连接池.
	 */
	private static final Map<String, Channel> SFTP_CHANNEL_POOL = new HashMap<String, Channel>();

	/**
	 * 获取sftp协议连接.
	 * 
	 * @param host
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 连接对象
	 * @throws JSchException
	 *             异常
	 */
	public static ChannelSftp findSftpConnect(final String host, final int port, final String username,
			final String password) throws JSchException {
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(username, host, port);
		Session sshSession = jsch.getSession(username, host, port);
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		return sftp;
	}

	/**
	 * 获取sftp协议连接.
	 * 
	 * @param host
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 连接对象
	 * @throws JSchException
	 *             异常
	 */
	public static ChannelSftp findSftpConnect_v1(final String host, final int port, final String username,
			final String password) throws JSchException {
		Session sshSession = null;
		Channel channel = null;
		ChannelSftp sftp = null;
		String key = host + "," + port + "," + username + "," + password;
		if (null == SFTP_CHANNEL_POOL.get(key)) {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			SFTP_CHANNEL_POOL.put(key, channel);
		} else {
			channel = SFTP_CHANNEL_POOL.get(key);
			sshSession = channel.getSession();
			if (!sshSession.isConnected())
				sshSession.connect();
			if (!channel.isConnected())
				channel.connect();
		}
		sftp = (ChannelSftp) channel;
		return sftp;
	}

	public static ChannelSftp upload(String fileName, String content) throws Exception {

		String ftpBankIP = SysConfig.findInstance().findMap().get("ftp.bank.ip").toString();
		String ftpBankPort = (String) SysConfig.findInstance().findMap().get("ftp.bank.port").toString();
		int ftpBankPortInt = Integer.parseInt(ftpBankPort);
		String ftpBankUser = SysConfig.findInstance().findMap().get("ftp.bank.user").toString();

		String ftpBankPassword = SysConfig.findInstance().findMap().get("ftp.bank.password").toString();

		String ftpBankSend = SysConfig.findInstance().findMap().get("ftp.bank.send").toString();
		String ftpBankSRecv = SysConfig.findInstance().findMap().get("ftp.bank.recv").toString();
		String ftpLocalSend = SysConfig.findInstance().findMap().get("ftp.local.send").toString();
		String ftpLocalRecv = SysConfig.findInstance().findMap().get("ftp.local.recv").toString();

		System.out.println("ftpLocalSend=" + ftpLocalSend);

		ChannelSftp sftp = findSftpConnect(ftpBankIP, ftpBankPortInt, ftpBankUser, ftpBankPassword);

		sftp.cd(ftpBankSend);
		String filePath = ftpLocalSend + "/" + fileName;
		System.out.println("filePath =" + filePath);

		FileUtil fileUtil = new FileUtil();

		// fileUtil.write(fileName, content);
		// fileUtil.appendByAbsolutePath(filePath, content);
		fileUtil.writeByAbsolutePath(filePath, content);
		sftp.put(filePath, fileName);

		System.out.println("上传文件结束");
		log.trace("上传文件结束");
		return sftp;
	}

}
