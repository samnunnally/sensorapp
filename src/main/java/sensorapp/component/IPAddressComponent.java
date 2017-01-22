/**
 * 
 */
package sensorapp.component;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import sensorapp.mixin.InetAddressMixIn;
import sensorapp.mixin.InterfaceAddressMixIn;
import sensorapp.mixin.NetworkInterfaceMixIn;

/**
 * @author samuelnunnally
 *
 */
@Component
public class IPAddressComponent {

	@PostConstruct
	private void generateIPAddresses() throws JsonProcessingException, SocketException {
		final String username = "";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
			message.setSubject("IP address - " + new Date());

			StringBuilder sb = new StringBuilder();

			sb.append("http://<ip>:8080/toggle-led").append("\n").append("http://<ip>:8080/ip-addresses").append("\n")
					.append(getIPAddresses(true));
			message.setText(sb.toString());

			try {
				Transport.send(message);

			} catch (Exception expected) {
				System.out.println(expected.toString());

			}
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getIPAddresses() throws JsonProcessingException, SocketException {
		return getIPAddresses(false);
	}

	public String getIPAddresses(boolean pretty) throws JsonProcessingException, SocketException {
		Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
		ArrayList<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();

		while (enumeration.hasMoreElements()) {
			interfaces.add(enumeration.nextElement());
		}

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, pretty);
		mapper.addMixIn(NetworkInterface.class, NetworkInterfaceMixIn.class);
		mapper.addMixIn(InetAddress.class, InetAddressMixIn.class);
		mapper.addMixIn(InterfaceAddress.class, InterfaceAddressMixIn.class);

		return mapper.writeValueAsString(interfaces);
	}
}
