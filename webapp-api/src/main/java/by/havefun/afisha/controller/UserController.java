package by.havefun.afisha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by user on 15.10.15.
 */
@Controller
public class UserController {

    @RequestMapping(value = "api", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String profile(Model model, Principal principal) {
        return "hello from web-api";
    }
}
