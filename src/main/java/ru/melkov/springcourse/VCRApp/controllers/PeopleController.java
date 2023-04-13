package ru.melkov.springcourse.VCRApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melkov.springcourse.VCRApp.models.Person;
import ru.melkov.springcourse.VCRApp.services.OurUsersService;
import ru.melkov.springcourse.VCRApp.services.PeopleService;
import ru.melkov.springcourse.VCRApp.security.AuthenticationFacade;
import ru.melkov.springcourse.VCRApp.util.PersonValidator;


// import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    // private final PersonDAO personDAO;
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    private final OurUsersService ourUsersService;

    private final AuthenticationFacade facade;




    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator,
                            OurUsersService ourUsersService, AuthenticationFacade facade) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.ourUsersService = ourUsersService;
        this.facade = facade;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        // books.isEmpty
        model.addAttribute("videocassettes", peopleService.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        person.setWhomCreatedBy(getCurrentUserName(facade.getAuthentication()));

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }


    private String getCurrentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
