package com.blubank;

import com.blubank.entity.User;
import com.blubank.entity.UserRole;
import com.blubank.entity.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class BankController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(@RequestParam(name="error", required=false, defaultValue="false") Boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/registerUser")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerUser")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String age,
                         Model model) {
        String passHash = passwordEncoder.encode(password);
        User dbUser = userService.findByLogin(login);

        if ( ! userService.addUser(login, passHash, UserRole.USER, email, phone, age)) {
            model.addAttribute("exists", true);
            model.addAttribute("login", login);
            return "register";
        }

        return "redirect:/";
    }

//    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
//    public String update(@RequestParam User user,
//                         @RequestParam(required = false) String login,
//                         @RequestParam(required = false) String password,
//                         @RequestParam(required = false) String email,
//                         @RequestParam(required = false) String phone,
//                         @RequestParam(required = false) String age,
//                         Model model) {
//        String passHash = passwordEncoder.encode(user.getPassword());
//        User dbUser = userService.findByLogin(user.getLogin());
//
//        if ( ! userService.addUser(user.getLogin(), user.getPassword(), UserRole.USER, email, phone, age)) {
//            model.addAttribute("exists", true);
//            model.addAttribute("login", login);
//            return "register";
//        }
//
//        return "redirect:/";
//    }

        private org.springframework.security.core.userdetails.User getCurrentUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
