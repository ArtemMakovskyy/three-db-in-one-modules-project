package com.store.security.service;

import com.store.security.dto.UserRegistrationRequestDto;
import com.store.security.dto.UserResponseDto;
import com.store.security.exception.EntityNotFoundException;
import com.store.security.exception.RegistrationException;
import com.store.security.mapper.UserMapper;
import com.store.security.model.Role;
import com.store.security.model.RoleName;
import com.store.security.model.User;
import com.store.security.repository.RoleRepository;
import com.store.security.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "userTransactionManager")
public class UserServiceImpl implements UserService {
    private static final int MINIMUM_ALLOWED_NUMBER_OF_ADMIN_USERS = 1;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration.");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        Role roleUser =
                roleRepository.findById(3L).orElseThrow(
                        () -> new EntityNotFoundException("Can't find ROLE_CUSTOMER by id"));
        user.setRoles(Set.of(roleUser));

        final User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto updateRole(Long userId, String role) {
        final User userFromDb = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + userId));
        final Role roleFromDb = roleRepository.findByName(RoleName.valueOf(role)).orElseThrow(
                () -> new EntityNotFoundException("Can't find role " + role));
        final List<Role> currentUserFromDatabaseWithAdminRoleListThatWillChange
                = userFromDb.getRoles().stream()
                .filter(r -> r.getName().equals(RoleName.ROLE_ADMIN))
                .toList();
        final List<User> usersByRoleAdminInDb = userRepository.findUsersByRole(RoleName.ROLE_ADMIN);
        if (!currentUserFromDatabaseWithAdminRoleListThatWillChange.isEmpty()
                && usersByRoleAdminInDb.size() == MINIMUM_ALLOWED_NUMBER_OF_ADMIN_USERS) {
            throw new RegistrationException("You cannot change the last admin role in the "
                    + "database. After the change, at least one user with the admin role "
                    + "has to remain.");
        }
        userFromDb.setRoles(Set.of(roleFromDb));
        return userMapper.toDto(userRepository.save(userFromDb));
    }
}
