package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.FeedBackDto;
import by.ladyka.club.entity.FeedBackEntity;
import by.ladyka.club.repository.FeedBackRepository;
import by.ladyka.club.service.email.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/api/feedback")
public class FeedBackController {

	@Autowired
	FeedBackRepository feedBackRepository;

	@Autowired
	EmailService emailService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity get(Principal principal, HttpServletRequest httpServletRequest, @RequestBody FeedBackDto feedBackDto) {
		FeedBackEntity feedBackEntity = new FeedBackEntity();
		BeanUtils.copyProperties(feedBackDto,feedBackEntity);
		feedBackEntity = feedBackRepository.save(feedBackEntity);
		emailService.sendFeedBack(feedBackEntity);
		BeanUtils.copyProperties(feedBackEntity, feedBackDto);
		return new ResponseEntity<>(feedBackDto, HttpStatus.OK);
	}

}
