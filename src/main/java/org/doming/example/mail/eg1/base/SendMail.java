package org.doming.example.mail.eg1.base;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * @Description: 发送Email
 * @Author: Doming
 * @Date: 2019-04-16 15:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendMail {

	private static final String USERNAME="job_doming@163.com";
	private static final String PASSWORD="job123456";
	private static final String MAIL_HOST="smtp.163.com";

	public static void main(String[] args) throws MessagingException {
		Properties prop = new Properties();
		prop.setProperty("mail.host",MAIL_HOST);
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop);
		//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		//2、通过session得到transport对象
		Transport ts = session.getTransport();
		//3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		ts.connect(MAIL_HOST, USERNAME, PASSWORD);
		//4、创建邮件
		Message message = createSimpleMail(session);
		//5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}




	private static Message createSimpleMail(Session session) throws MessagingException {
		//创建邮件对象
		MimeMessage message = new MimeMessage(session);
		//指明邮件的发件人
		message.setFrom(new InternetAddress(USERNAME));
		//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(USERNAME));
		//邮件的标题
		message.setSubject("只包含文本的简单邮件");
		//邮件的文本内容
		message.setContent("你好啊！", "text/html;charset=UTF-8");
		//返回创建好的邮件对象
		return message;
	}
}
