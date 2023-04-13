package ru.melkov.springcourse.VCRApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.melkov.springcourse.VCRApp.models.OurUser;
import ru.melkov.springcourse.VCRApp.services.OurUsersService;

@Component
public class OurUserValidator implements Validator {

    private final OurUsersService ourUsersService;

    @Autowired
    public OurUserValidator(OurUsersService ourUsersService) {
        this.ourUsersService = ourUsersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OurUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        OurUser ourUser = (OurUser) target;

        if (ourUsersService.getOurUserByUsername(ourUser.getUsername()).isPresent())
            errors.rejectValue("username","","This user already exists!");

    }
}
