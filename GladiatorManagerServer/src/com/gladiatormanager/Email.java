package com.gladiatormanager;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email
{
  private Properties props = null;

  public Email()
  {

  }

  public void start()
  {
    this.props = System.getProperties();
    this.props.put("mail.smtp.starttls.enable", true);
    this.props.put("mail.smtp.host", "smtp.gmail.com");
    this.props.put("mail.smtp.user", "gladiatormanager.noreply");
    this.props.put("mail.smtp.password", "passwordFU");
    this.props.put("mail.smtp.port", "587");
    this.props.put("mail.smtp.auth", true);
  }

  public boolean sendMail(String to, String subject, String msg)
  {
    Session session = Session.getInstance(this.props, null);
    MimeMessage message = new MimeMessage(session);
    try
    {
      InternetAddress from = new InternetAddress("gladiatormanager.noreply@gmail.com");
      message.setSubject(subject);
      message.setFrom(from);
      message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

      // Create a multi-part to combine the parts
      Multipart multipart = new MimeMultipart("alternative");

      // Create your text message part
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(msg);

      // Add the text part to the multipart
      multipart.addBodyPart(messageBodyPart);

      // Create the html part
      messageBodyPart = new MimeBodyPart();
      String htmlMessage = msg;
      messageBodyPart.setContent(htmlMessage, "text/html");

      // Add html part to multi part
      multipart.addBodyPart(messageBodyPart);

      // Associate multi-part with message
      message.setContent(multipart);

      // Send message
      Transport transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", "gladiatormanager.noreply", "passwordFU");
      transport.sendMessage(message, message.getAllRecipients());
      return true;
    }
    catch (Exception e)
    {
      System.out.println("Exception when trying to send email: " + e.toString());
      e.printStackTrace();
      return false;
    }
  }
}