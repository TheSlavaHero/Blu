package com.blubank;

import com.blubank.entity.Card.CardRepository;
import com.blubank.entity.Card.CardService;
import com.blubank.entity.Card.CardType;
import com.blubank.entity.User.User;
import com.blubank.entity.User.UserRole;
import com.blubank.entity.User.UserService;
import com.blubank.entity.User.ApplicationUserForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private CardService cardService;

    @GetMapping("/login")
    public String login(@RequestParam(name="error", required=false, defaultValue="false") Boolean error,
                        @RequestParam(name="successRegistration", required=false, defaultValue="false") Boolean successRegistration,
                        @RequestParam(name="logout", required=false, defaultValue="false") Boolean logout,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("successRegistration", successRegistration);
        model.addAttribute("logout", logout);
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    @GetMapping("/registerUser")
    public String newUserForm(@RequestParam(name="emailExists", required=false, defaultValue="false") Boolean emailExists,
                              @RequestParam(name="wrongEmail", required=false, defaultValue="false") Boolean wrongEmail,
                              @RequestParam(name="samePassword", required=false, defaultValue="false") Boolean samePassword,
                              Model model) {
        model.addAttribute("applicationUserForm", new ApplicationUserForm());
        model.addAttribute("emailExists", emailExists);
        model.addAttribute("wrongEmail", wrongEmail);
        model.addAttribute("samePassword", samePassword);
        return "register";
    }

    @PostMapping("/registerUser")
    public String greetingSubmit(@ModelAttribute ApplicationUserForm applicationUserForm, Model model) {
        model.addAttribute("applicationUserForm", applicationUserForm);
        return "register";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam(required = false, name = "user.name") String name,
                         @RequestParam(required = false, name = "user.surname") String surname,
                         @RequestParam(required = false, name = "user.password") String password,
                         @RequestParam(required = false, name = "user.email")String email,
                         @RequestParam(required = false, name = "confirmPassword") String confirmPassword,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String age,
                         Model model) {
        String passHash = passwordEncoder.encode(password);
        User user = new User(name, surname, passHash, UserRole.USER, email, phone, age);

        if (!checkUser(user, model, password, confirmPassword)) {
            return "redirect:/registerUser";
        }
        userService.addUser(name, surname, passHash, UserRole.USER, email, phone, age);
        model.addAttribute("successRegistration", true);
        return "redirect:/login";
    }

    public boolean checkUser(User user, Model model, String password, String confirmPassword) {//returns true if everything is ok
        Boolean loginOK = true;
        if (userService.checkUser(user.getEmail())) {
            model.addAttribute("emailExists", true);
            loginOK = false;
        }
        if (!(password.equals(confirmPassword))) {
            model.addAttribute("samePassword", true);
            loginOK = false;
        }
        if (!emailValidator.isValid(user.getEmail())) {
            model.addAttribute("wrongEmail", true);
            loginOK = false;
        }
        return loginOK;
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                       Model model) {
        if (user != null) {
            User currentUser = userService.findByEmail(user.getUsername());
            String name = "Welcome back, " + currentUser.getName() + " " + currentUser.getSurname() + "!";
            model.addAttribute("name", name);
        } else {
            model.addAttribute("name", "");
        }
        return "main";
    }
    @PostMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/version")
    public String version() {
        return "version";
    }

    @GetMapping("/newcard")
    public String newCard() {
        return "newcard";
    }

    @PostMapping("/newcard/{cardType}")
    public String createCard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @PathVariable CardType cardType) {
        User currentUser = userService.findByEmail(user.getUsername());
        cardService.addCard(currentUser, cardType);

        return "redirect:/mycards";
    }

    @GetMapping("/mycards")
    public String myCards(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                          Model model
                          ) {
        User currentUser = userService.findByEmail(user.getUsername());
        model.addAttribute("cards", cardService.allCards(currentUser));
        return "mycards";
    }

    @PostMapping("/mycards")
    public String myCards() {
        return "mycards";
    }

    @PostMapping("/mycards/delete")
    public ResponseEntity<Void> delete(
            @RequestParam(value = "toDelete[]", required = false)
                    long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            cardService.deleteCards(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mycards/topup")
    public ResponseEntity<Void> topup(
            @RequestParam(value = "toDelete[]", required = false) long[] toDelete,
            @RequestParam(value = "balance", required = false) double balance) {
        if (toDelete != null && toDelete.length > 0)
            cardService.topupCards(toDelete, balance);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


