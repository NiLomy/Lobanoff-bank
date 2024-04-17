package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListResponse(userRepository.findAllByIsDeletedIsFalse());
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        return userMapper.toResponse(userRepository.findByPhone(phone));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            return null;
        User user = (User) authentication.getPrincipal();
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isDeleted(user.getIsDeleted())
                .build();
    }

    @Override
    public void banUser(UserDto userDto) {
        if (!userDto.getRole().equals(Role.ADMIN)) {
            userRepository.updateStateById(userDto.getId(), State.BANNED.toString());
            log.info("User {} was banned.", userDto);
        } else {
            log.warn("You can not ban {} because of his role.", userDto);
        }
    }

    // soft-delete
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setIsDeleted(true);
        userRepository.save(user);
        log.info("User {} was deleted.", user);
    }
}
