package com.blubank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BankController {

    @GetMapping("/WEB-INF/static/index.html")
    public String index() {
        return "/WEB-INF/static/index.html";
    }


}
