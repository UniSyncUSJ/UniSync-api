package com.uniSync.uniSync_api.Service;

import com.uniSync.uniSync_api.Common.UserRole;
import com.uniSync.uniSync_api.Model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    Optional<User> getUserbyEmail(String email);
    List<User> getUsersByRole(UserRole userRole);
}

