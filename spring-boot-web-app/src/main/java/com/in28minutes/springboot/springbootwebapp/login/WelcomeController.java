package com.in28minutes.springboot.springbootwebapp.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
@SessionAttributes("name")
@RequiredArgsConstructor
public class WelcomeController {

    @RequestMapping(value = "/", method = GET)
    public String goToWelcomePage(ModelMap map) {
        map.put("name", getLoggedInUsername());
        return "welcome";
    }

    private String getLoggedInUsername() {
        return ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .orElse(EMPTY);
    }

//    @RequestMapping(value = "login", method = POST)
//    public String welcomePage(@RequestParam String name,
//                              @RequestParam String password,
//                              ModelMap model) {
//        model.put("name", name);
//        if (authenticationService.authenticate(name, password)) {
//            return "welcome";
//        } else {
//            model.put("error", "Wrong credentials have been inserted.");
//            return "login";
//        }
//    }
}
