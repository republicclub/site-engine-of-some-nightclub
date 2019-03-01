package by.ladyka.club.endpoints.api;

import by.ladyka.club.repository.OrderEntityRepository;
import by.ladyka.club.service.email.EmailService;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.vcard.VCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class QrController {
	@Autowired
	EmailService emailService;
	@Autowired
	OrderEntityRepository orderEntityRepository;

	@GetMapping("/api/qr/{value}")
	public void get(@PathVariable String value, HttpServletResponse httpServletResponse) {
		try {
			QRCode.from(value)
					.withSize(365, 365)
					.writeTo(httpServletResponse.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
