package com.ftp.qanlong.control;

import java.io.IOException;
import java.util.Properties;

import com.ftp.qanlong.utils.Util;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MyEmail {

	public void send(String[] toUser, String[] copyToUser, String subject, String Content)
			throws IOException, MessagingException {

		// 1：初始化默认参数
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", Util.getProperty("transportType"));
		props.setProperty("mail.host", Util.getProperty("emailHost"));
		props.setProperty("mail.port", Util.getProperty("emailPort"));
		props.setProperty("mail.user", Util.getProperty("fromUser"));
		props.setProperty("mail.from", Util.getProperty("fromEmail"));
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.put("mail.smtp.starttls.enable", "true");
		// 2：获取Session对象
		Session session = Session.getInstance(props, null);
		session.setDebug(true);
		// 通过MimeMessage来创建Message接口的子类
		MimeMessage message = new MimeMessage(session);
		// 下面是对邮件的基本设置
		// 设置发件人：
		// 设置发件人第一种方式：直接显示：antladdie <13880756558m@sina.cn>
		// InternetAddress from = new InternetAddress(sender_username);
		// 设置发件人第二种方式：发件人信息拼接显示：澎恰恰 <13880756558m@sina.cn>
//		String formName = MimeUtility.encodeWord("澎恰恰") + " <" + Util.getProperty("fromEmail") + ">";
		String formName = " <" + Util.getProperty("fromEmail") + ">";
		InternetAddress from = new InternetAddress(formName);
		message.setFrom(from);

		// 设置收件人：
		InternetAddress[] toUserArr = new InternetAddress[toUser.length];
		for (int i = 0; i < toUser.length; i++) {
			InternetAddress to = new InternetAddress(toUser[i]);
			toUserArr[i] = to;
		}
		message.setRecipients(Message.RecipientType.TO, toUserArr);

		// 设置抄送人(两个)可有可无抄送人：
		InternetAddress[] copyToUserArr = new InternetAddress[copyToUser.length];
		for (int i = 0; i < copyToUser.length; i++) {
			InternetAddress copy = new InternetAddress(copyToUser[i]);
			copyToUserArr[i] = copy;
		}
		message.setRecipients(Message.RecipientType.CC, copyToUserArr);

		// 设置密送人 可有可无密送人：
		// InternetAddress toBCC = new InternetAddress(toEmail);
		// message.setRecipient(Message.RecipientType.BCC, toBCC);

		// 设置邮件主题
		message.setSubject(subject);

		// 设置邮件内容,这里我使用html格式，其实也可以使用纯文本；纯文本"text/plain"
		message.setContent(Content, "text/html;charset=UTF-8");

		// 保存上面设置的邮件内容
		message.saveChanges();

		// 获取Transport对象
		Transport transport = session.getTransport();
		// smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
		transport.connect(null, null, Util.getProperty("authCode"));
		// 发送邮件
		transport.sendMessage(message, message.getAllRecipients()); // 发送
	}

	public static void main(String[] args) {
		String[] arr = { "1215556977@qq.com", "1215556977@qq.com" };
		MyEmail email = new MyEmail();
		try {
			email.send(arr, arr, "简单测试", "简单测试");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String[] arr = { null, "" };
//		System.out.println(arr.length);
	}

}
