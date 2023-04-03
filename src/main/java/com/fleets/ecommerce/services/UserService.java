package com.fleets.ecommerce.services;


import com.fleets.ecommerce.entities.User;
import com.fleets.ecommerce.exceptions.UserNotFoundException;
import com.fleets.ecommerce.models.Users.SignupDto;
import com.fleets.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository usersRepository;

    public User save(SignupDto userDto) {
        var user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(usersRepository.count() == 0){
                user.setRole("ROLE_ADMIN");
            }else {
                user.setRole("ROLE_USER");
            }

            return usersRepository.save(user);
    }

    public User getById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no user with id: " + id));
    }

    public User getByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("There is no user with email: " + email));
    }
}