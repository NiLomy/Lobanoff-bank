package ru.kpfu.itis.gateway.lobanov.gatewayservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.entities.User;
import ru.kpfu.itis.gateway.lobanov.gatewayservice.repositories.UserRepository;

import static ru.kpfu.itis.gateway.lobanov.gatewayservice.utils.ExceptionMessages.USERNAME_NOT_FOUND_EXCEPTION;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_EXCEPTION, email));
        return user;
    }
}
