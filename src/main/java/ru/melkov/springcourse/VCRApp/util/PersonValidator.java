package ru.melkov.springcourse.VCRApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.melkov.springcourse.VCRApp.models.Person;
import ru.melkov.springcourse.VCRApp.services.PeopleService;


@Component
public class PersonValidator implements Validator {

    // private final PersonDAO personDAO;
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.getPersonByName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Человек с таким именем уже создан!");
    }
}
