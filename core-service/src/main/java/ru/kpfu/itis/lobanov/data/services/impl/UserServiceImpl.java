package ru.kpfu.itis.lobanov.data.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.requests.UpdateUserRequest;
import ru.kpfu.itis.lobanov.exceptions.EmailAlreadyInUseException;
import ru.kpfu.itis.lobanov.exceptions.PasswordMatchException;
import ru.kpfu.itis.lobanov.exceptions.PhoneAlreadyInUseException;
import ru.kpfu.itis.lobanov.exceptions.UserNotFoundException;

import java.util.List;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.UPDATE_USER_REQUEST_NOT_NULL;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
    public UserDto update(UpdateUserRequest request) {
        validateUpdateRequest(request);

        User user = userRepository.findById(Long.parseLong(request.getId())).orElseThrow(
                () -> new UserNotFoundException("User cannot be found by id = " + request.getId()));

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserDto banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User cannot be found by id = " + userId));
        if (!user.getRole().equals(Role.ADMIN)) {
            return userMapper.toResponse(userRepository.updateStateById(userId, State.BANNED.toString()));
        } else {
            return null;
        }
    }

    // soft-delete
    @Override
    public UserDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User cannot be found by id = " + userId));
        user.setDeleted(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    private void validateUpdateRequest(
            @NotNull(message = UPDATE_USER_REQUEST_NOT_NULL)
            UpdateUserRequest request
    ) {
        String id = request.getId();
        String email = request.getEmail();
        String phone = request.getPhone();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        if (!userRepository.existsById(Long.parseLong(id)))
            throw new UserNotFoundException("User cannot be found by id = " + id);
        if (!password.equals(confirmPassword))
            throw new PasswordMatchException("Passwords in form don't match");
        if (userRepository.existsByEmail(email))
            throw new EmailAlreadyInUseException("This email is already occupied by another user");
        if (userRepository.existsByPhone(phone))
            throw new PhoneAlreadyInUseException("This phone is already occupied by another user");
    }
}
