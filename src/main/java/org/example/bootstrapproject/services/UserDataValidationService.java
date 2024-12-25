package org.example.bootstrapproject.services;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.validators.MyDataValidator;
import org.example.bootstrapproject.validators.PersonValidator;
import org.example.bootstrapproject.validators.RoleValidator;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;


import java.util.List;

// Класс, выполняющий валидацию пользователя и ролей
@Component
public class UserDataValidationService {

    private final PersonValidator personValidator;
    private final RoleValidator roleValidator;
    private final MyDataValidator myDataValidator;

    public UserDataValidationService(
            PersonValidator personValidator,
            RoleValidator roleValidator,
            MyDataValidator myDataValidator) {
        this.personValidator = personValidator;
        this.roleValidator = roleValidator;
        this.myDataValidator = myDataValidator;
    }

    // Метод для валидации пользователя и ролей
    public String validateUserData(Person user, List<String> role, Model model) {
        // Создание объекта BindingResult для пользователя и списка ролей
        BindingResult userBindingResult = new BeanPropertyBindingResult(user, "user");
        BindingResult roleBindingResult = new BeanPropertyBindingResult(role, "role");

        // Валидация пользователя и списка ролей
        personValidator.validate(user, userBindingResult);
        roleValidator.validate(role, roleBindingResult);

        // Очистка предыдущих результатов валидации
        myDataValidator.dataClean();

        // Валидация результатов валидации пользователя и списка ролей
        myDataValidator.validate(userBindingResult);
        myDataValidator.validate(roleBindingResult);

        // Получение строкового представления ошибок
        String allErrors = myDataValidator.getAllErrorsAsString();

        // Если есть ошибки, добавить их в модель
        if (!allErrors.isEmpty()) {
            model.addAttribute("allErrors", allErrors);
        }

        return allErrors;
    }
}
