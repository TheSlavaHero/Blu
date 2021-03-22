package com.blubank;

import com.blubank.entity.User;
import com.blubank.entity.UserRole;
import com.blubank.entity.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;
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

    @Autowired
    private EmailValidator emailValidator;

    @GetMapping("/login")
    public String login(@RequestParam(name="error", required=false, defaultValue="false") Boolean error,
                        @RequestParam(name="successRegistration", required=false, defaultValue="false") Boolean successRegistration,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("successRegistration", successRegistration);
        return "login";
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/registerUser")
    public String newUserForm(@RequestParam(name="emailExists", required=false, defaultValue="false") Boolean emailExists,
                              @RequestParam(name="wrongEmail", required=false, defaultValue="false") Boolean wrongEmail,
                              Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("emailExists", emailExists);
        model.addAttribute("wrongEmail", wrongEmail);
        return "register";
    }

    @PostMapping("/registerUser")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String age,
                         Model model) {
        String passHash = passwordEncoder.encode(password);
        User user = new User(name, surname, passHash, UserRole.USER, email, phone, age);
//        Boolean emailExists = false;
//        Boolean wrongEmail = false;
//        Boolean samePassword = false;
//        if (userService.checkUser(email)) {
//            model.addAttribute("emailExists", true);
//            emailExists = true;
//        }
//        if (!emailValidator.isValid(email)) {
//            model.addAttribute("wrongEmail", true);
//            wrongEmail = true;
//        }
//        if () {
//            model.addAttribute("samePassword", true);
//            samePassword = false;
//        }

        if (!checkUser(user, model)) {
            return "redirect:/registerUser";
        }
        userService.addUser(name, surname, passHash, UserRole.USER, email, phone, age);
        model.addAttribute("successRegistration", true);
        return "redirect:/login";
    }

        private org.springframework.security.core.userdetails.User getCurrentUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
    public boolean checkUser(User user, Model model) {//returns true if everything is ok
        Boolean error = false;
        if (userService.checkUser(user.getEmail())) {
            model.addAttribute("emailExists", true);
            error = true;
        }
        if (!emailValidator.isValid(user.getEmail())) {
            model.addAttribute("wrongEmail", true);
            error = true;
        }
        return !error;
    }
}
