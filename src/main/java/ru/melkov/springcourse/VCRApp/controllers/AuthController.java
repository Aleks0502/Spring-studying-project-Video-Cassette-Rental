package ru.melkov.springcourse.VCRApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.melkov.springcourse.VCRApp.models.OurUser;
import ru.melkov.springcourse.VCRApp.services.OurUsersService;
import ru.melkov.springcourse.VCRApp.util.OurUserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final OurUserValidator ourUserValidator;
    private final OurUsersService ourUsersService;

    @Autowired
    public AuthController(OurUserValidator ourUserValidator, OurUsersService ourUsersService) {
        this.ourUserValidator = ourUserValidator;
        this.ourUsersService = ourUsersService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("our_user") OurUser ourUser) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("our_user") @Valid OurUser ourUser,
                                 BindingResult bindingResult) {
        ourUserValidator.validate(ourUser, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        ourUsersService.save(ourUser);

        return "redirect:/auth/login";
    }
}
