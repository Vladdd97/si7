package faf.labs.si.encryption.controllers;

import faf.labs.si.encryption.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {



    @GetMapping("hi")
    public String sayHi(Model model){
        model.addAttribute("user",new User());
        return "form";
    }




}
