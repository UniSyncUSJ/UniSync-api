package com.uniSync.uniSync_api.Repository;

import com.uniSync.uniSync_api.Common.UserRole;
import com.uniSync.uniSync_api.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Set<User> findAllByRole(UserRole role);

}
