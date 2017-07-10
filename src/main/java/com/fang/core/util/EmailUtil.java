package com.fang.core.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fang.core.entity.EmailEntity;
import com.fang.sys.controller.DeptController;

/**
 * 发送邮件的工具类
 * 
 * @author fang
 * @version 2017年7月5日
 */
public final class EmailUtil {
	
	private final static Logger  logger  = LoggerFactory.getLogger(DeptController.class);
	
	private static Properties props = new Properties();

	//默认的参数
	private static Properties properties = new Properties();
	private static InputStreamReader is = null;
	
	public static String getParam(String key) {
		if (is == null) {
			try {
				is = new InputStreamReader(EmailUtil.class.getClassLoader().getResourceAsStream("config/email.properties"), "UTF-8");
				properties.load(is);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (key == null)
			return key;
		Object value = properties.get(key);
		if (value == null || "".equals(value))
			return "";
		else
			return value.toString();
	}
	
	private static String host = EmailUtil.getParam("email.host");

	private static String from = EmailUtil.getParam("email.from");

	private static String name = EmailUtil.getParam("email.name");

	private static String password = EmailUtil.getParam("email.password");


	private static Authenticator authenticator;

	static {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
		// props.put("mail.smtp.port", "25"); //google使用465或587端口
		props.put("mail.smtp.auth", "true"); // 使用验证
		// props.put("mail.debug", "true");

		authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				if (password == null || "".equals(password.trim())) {
					return null;
				}
				return new PasswordAuthentication(from, password);
			}
		};
	}

	/**
	 * 发送邮件
	 */
	public static final boolean sendEmail(final  EmailEntity email) {

		Properties p = props;
		Authenticator auth = authenticator;
		Multipart mp = new MimeMultipart();
		String from_ = from;
		try {
			//如果EmailEntity有host  替换默认host
			if(email.getHost() != null){
				p = new Properties(props);
				p.put("mail.smtp.host", email.getHost());
			}
			//如果EmailEnitity有发件人信息，则替换默认的发件人信息
			if(email.getFrom() != null){
				auth = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email.getFrom(), email.getPassword());
					}
				};
				from_  = email.getFrom();
			}
			
			//创建Session
			Session mailSession = Session.getInstance(p, auth);
			
			//设置邮件内容
			BodyPart bp = new MimeBodyPart();
			bp.setContent(email.getContent(), "text/html;charset=UTF-8");
			mp.addBodyPart(bp);
			
			//设置邮件附件
			if (email.getAttachments() != null && email.getAttachments().length > 0) {
				for (int i = 0; i < email.getAttachments().length; i++) {
					BodyPart bpAtta = new MimeBodyPart();
					FileDataSource fileds = new FileDataSource(email.getAttachments()[i]);
					bpAtta.setDataHandler(new DataHandler(fileds));
					bpAtta.setFileName(MimeUtility.encodeText(fileds.getName()));
					mp.addBodyPart(bp);
				}
			}
			
			//创建邮件实例
			MimeMessage message = new MimeMessage(mailSession);  
	        message.setFrom(new InternetAddress(from_));  //设置发件人
	        message.addRecipient(RecipientType.TO, new InternetAddress(email.getSendTo()));  //设置收件人
	        message.setSentDate(Calendar.getInstance().getTime());  //设置发件时间
	        message.setSubject(email.getSubject());  //设置主题
	        message.setContent(mp);  //设置主体
	        
	        
	        Transport transport = mailSession.getTransport("smtp");  
	        transport.connect(host,name, password);  
	        Transport.send(message, message.getRecipients(RecipientType.TO)); 
	        transport.close();
	        return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 

		// 发送
		return false;
	}
}
