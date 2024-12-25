package org.example.bootstrapproject.services;

import org.example.bootstrapproject.model.Person;
import org.example.bootstrapproject.repo.PeopleRepository;
import org.example.bootstrapproject.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * Сервис, предоставляющий информацию о пользователях для Spring Security.
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param peopleRepository Репозиторий для работы с пользователями.
     */
    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * Загружает пользователя по его имени для Spring Security.
     *
     * @param email почта пользователя.
     * @return Объект UserDetails, представляющий информацию о пользователе.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> personOptional = peopleRepository.findByEmailWithRoles(email);

        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new PersonDetails(personOptional.get());
    }
}
