package ru.melkov.springcourse.VCRApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melkov.springcourse.VCRApp.models.Person;
import ru.melkov.springcourse.VCRApp.models.Videocassette;
import ru.melkov.springcourse.VCRApp.services.PeopleService;
import ru.melkov.springcourse.VCRApp.services.VideoCassettesService;


// import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/videocassettes")
public class VideocassettesController {
    // private final VideocassetteDAO videoCassetteDAO;
    // Здесь используем DAO людей для того, чтобы получить список из всех людей - personDAO.index();
    // private final PersonDAO personDAO;


    private final VideoCassettesService videoCassettesService;
    private final PeopleService peopleService;

    @Autowired
    public VideocassettesController(VideoCassettesService videoCassettesService, PeopleService peopleService) {
        this.videoCassettesService = videoCassettesService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("videocassettes", videoCassettesService.findAll());
        return "videocassettes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {

        model.addAttribute("videocassette", videoCassettesService.findOne(id));

        // Используем обощения, чтобы вызывать метод isPresent().
        Optional<Person> videocassetteOwner = videoCassettesService.getVideocassetteOwner(id);

        // Если книга назначена какому-либо человеку, то получаем этого человека.
        // Если нет, то получаем весь список людей.
        if (videocassetteOwner.isPresent())
            model.addAttribute("owner", videocassetteOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());

            return "videocassettes/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("videocassette") Videocassette videocassette) {
        return "videocassettes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("videocassette") @Valid Videocassette videocassette, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "videocassettes/new";

        videoCassettesService.save(videocassette);
        return "redirect:/videocassettes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("videocassette", videoCassettesService.findOne(id));

        return "videocassettes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("videocassette") @Valid Videocassette videocassette, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "videocassettes/edit";

        videoCassettesService.update(id, videocassette);
        return "redirect:/videocassettes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        videoCassettesService.delete(id);
        return "redirect:/videocassettes";
    }

    // Дообавляем два метода контроллера для назначения книги человеку и для освобождения книги:
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson есть только id, т.е. неn значений для полей name, yearOfBirth.
        // И нам этого достаточно, т.к. мы получили id человека, кому нам необходимо назначить эту книгу.
        videoCassettesService.assign(id, selectedPerson);

        return "redirect:/videocassettes/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        videoCassettesService.release(id);
        return "redirect:/videocassettes/" + id;
    }
}
