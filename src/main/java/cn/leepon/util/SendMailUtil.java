package cn.leepon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.Logger;
import cn.leepon.util.ResourceUtil;

public class SendMailUtil {

	private static String sender = "";

	private static String host = "";

	private static String user = "";

	private static String password = "";

	private static String protocol = "";

	private static String auth = "";

	private static Logger logger = Logger.getLogger(SendMailUtil.class);

	static {
		sender = ResourceUtil.getValue("mail", "sender");
		host = ResourceUtil.getValue("mail", "host");
		user = ResourceUtil.getValue("mail", "user");
		password = ResourceUtil.getValue("mail", "password");
		protocol = ResourceUtil.getValue("mail", "protocol");
		auth = ResourceUtil.getValue("mail", "auth");
	}


	private static Session openSession() {

		Properties prop = new Properties();
		prop.setProperty("mail.host", host);
		prop.setProperty("mail.transport.protocol", protocol);
		prop.setProperty("mail.smtp.auth", auth);
		// 1、创建session
		return Session.getInstance(prop);

	}
	
	
	private static void closeTransport(Transport ts){
		if (ts != null) {
			try {
				ts.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.info(e.getMessage());
			}
		}
	}

	public static void sendMail(Message message) {

		// 1、创建session
		Session session = openSession();
		// 开启Session的debug模式查看到程序发送Email的运行状态
		session.setDebug(true);
		// 2、通过session得到transport对象
		Transport ts = null;
		try {
			ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect(host, user, password);
			// 4、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info(e.getMessage());
		}finally {
			closeTransport(ts); 
		}

	}

	

	/**
	 * 
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 * @Anthor:leepon
	 * @param session
	 * @param sender
	 *            发件人
	 * @param recevers
	 *            收件人
	 * @param cs
	 *            抄送
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createSimpleMail(String sender, String[] recevers, String[] cs,
			String subject, String content) throws Exception {
		MimeMessage message = wrapMime(sender, recevers, cs, subject);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

	/**
	 * @Method: createImageMail
	 * @Description: 生成一封邮件正文带图片的邮件
	 * @Anthor:leepon
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createImageMail(String sender, String[] recevers, String[] cs,
			String subject, String content, String name) throws Exception {
		
		MimeMessage message = wrapMime(sender, recevers, cs, subject);

		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(content + System.lineSeparator() + "<img src='cid:image'>", "text/html;charset=UTF-8");
		// 准备图片数据
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(name));
		image.setDataHandler(dh);
		image.setContentID("image");
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");

		message.setContent(mm);
		message.saveChanges();
		// message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
		// 返回创建好的邮件
		return message;
	}

	/**
	 * @Method: createAttachMail
	 * @Description: 创建一封带附件的邮件
	 * @Anthor:leepon
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createAttachMail(String sender, String[] recevers, String[] cs,
			String subject, String content, String[] attachnames) throws Exception {
		
		MimeMessage message = wrapMime(sender, recevers, cs, subject);

		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		// 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		text.setContent(content, "text/html;charset=UTF-8");

		// 创建邮件附件

		List<MimeBodyPart> attaches = new ArrayList<>();

		for (int i = 0; i < attachnames.length; i++) {
			MimeBodyPart attach = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource(attachnames[i]));
			attach.setDataHandler(dh);
			attach.setFileName(MimeUtility.encodeText(dh.getName()));
			attaches.add(attach);
		}

		// 创建容器描述数据关系
		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(text);
		for (MimeBodyPart attach : attaches) {
			mp.addBodyPart(attach);
		}
		mp.setSubType("mixed");

		message.setContent(mp);
		message.saveChanges();
		// 将创建的Email写入到E盘存储
		// message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
		// 返回生成的邮件
		return message;
	}

	/**
	 * @Method: createMixedMail
	 * @Description: 生成一封带附件和带图片的邮件
	 * @Anthor:leepon
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMixedMail(String sender, String[] recevers, String[] cs,
			String subject, String content, String imagename, String[] attachnames) throws Exception {
		
		MimeMessage message = wrapMime(sender, recevers, cs, subject);

		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		// 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		text.setContent(content + System.lineSeparator() + "<img src='cid:image'>", "text/html;charset=UTF-8");

		// 图片
		MimeBodyPart image = new MimeBodyPart();
		image.setDataHandler(new DataHandler(new FileDataSource(imagename)));
		image.setContentID("image");

		List<MimeBodyPart> attaches = new ArrayList<>();

		for (int i = 0; i < attachnames.length; i++) {
			MimeBodyPart attach = new MimeBodyPart();
			DataHandler dh = new DataHandler(new FileDataSource(attachnames[i]));
			attach.setDataHandler(dh);
			attach.setFileName(MimeUtility.encodeText(dh.getName()));
			attaches.add(attach);
		}

		// 描述关系:正文和图片
		MimeMultipart mp1 = new MimeMultipart();
		mp1.addBodyPart(text);
		mp1.addBodyPart(image);
		mp1.setSubType("related");

		// 描述关系:正文和附件
		MimeMultipart mp2 = new MimeMultipart();

		for (MimeBodyPart attach : attaches) {
			mp2.addBodyPart(attach);
		}

		// 代表正文的bodypart
		MimeBodyPart contentp = new MimeBodyPart();
		contentp.setContent(mp1);
		mp2.addBodyPart(contentp);
		mp2.setSubType("mixed");

		message.setContent(mp2);
		message.saveChanges();

		// message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
		// 返回创建好的的邮件
		return message;
	}

	
	/**
	 * 抽取公用
	 * @param sender  发送邮箱
	 * @param recevers  接收邮箱
	 * @param copyers  抄送邮箱
	 * @param subject  标题
	 * @return
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private static MimeMessage wrapMime(String sender, String[] recevers, String[] copyers, String subject)
			throws MessagingException, AddressException {
		
		Session session = openSession();
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(sender));
		if (recevers != null && recevers.length > 0) {
			// 收件人
			InternetAddress[] res = new InternetAddress[recevers.length];
			for (int i = 0; i < recevers.length; i++) {
				res[i] = new InternetAddress(recevers[i]);
			}
			message.setRecipients(Message.RecipientType.TO, res);
		}
		if (copyers != null && copyers.length > 0) {
			// 抄送
			InternetAddress[] cc = new InternetAddress[copyers.length];
			for (int i = 0; i < copyers.length; i++) {
				cc[i] = new InternetAddress(copyers[i]);
			}
			message.setRecipients(Message.RecipientType.CC, cc);
		}
		// 邮件的标题
		message.setSubject(subject);
		return message;
	}
	
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		//创建邮件
		// Message message = createSimpleMail(session,sender,new String[]{"liupeng136@pingan.com.cn"},null,"测试邮件","测试邮件");
		// Message message = createImageMail(session,sender,new String[]{"liupeng136@pingan.com.cn"},null,"测试邮件","测试邮件","1.jpg");
		Message message = createAttachMail(sender, new String[] { "liupeng136@pingan.com.cn" }, null, "测试邮件","测试邮件", new String[] { "1.jpg", "1.txt" });
		// Message message = createMixedMail(session,sender,new String[]{"liupeng136@pingan.com.cn"},null,"测试邮件","测试邮件","1.jpg",new String[]{"1.jpg","1.txt"});
		// 发送邮件
		sendMail(message); 
		
	}

}
