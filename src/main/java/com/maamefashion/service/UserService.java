package com.maamefashion.service;

import com.maamefashion.config.CustomUserDetails;
import com.maamefashion.model.User;
import com.maamefashion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new CustomUserDetails(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void initAdminUser() {
        if (!userRepository.existsByEmail("designer@maame.com")) {
            User admin = User.builder()
                    .email("designer@maame.com")
                    .password("$2a$12$9KrCnUW4JYNAz27nXYn7de7tSfE4iSaSBrVLGBj.EI5ovIZY0/7wC") // admin123
                    .name("Maame Designer")//
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}