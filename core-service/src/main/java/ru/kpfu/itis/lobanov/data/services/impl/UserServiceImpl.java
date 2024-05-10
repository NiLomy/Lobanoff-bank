package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.UserService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper<User, UserDto> userMapper;

    @Override
    public UserDto getById(Long userId) {
        return userMapper.toResponse(userRepository.findById(userId).orElse(null));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListResponse(userRepository.findAllByDeletedIsFalse());
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        return userMapper.toResponse(userRepository.findByPhone(phone).orElse(null));
    }

    @Override
    public UserDto banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        if (!user.getRole().equals(Role.ADMIN)) {
            log.info("User {} was banned.", user);
            return userMapper.toResponse(userRepository.updateStateById(userId, State.BANNED.toString()));
        } else {
            log.warn("You can not ban {} because of his role.", user);
            return null;    
        }
    }

    // soft-delete
    @Override
    public UserDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setDeleted(true);
        log.info("User {} was deleted.", user);
        return userMapper.toResponse(userRepository.save(user));
    }
}
