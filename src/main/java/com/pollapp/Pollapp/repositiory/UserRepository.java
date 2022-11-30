package com.pollapp.Pollapp.repositiory;

import com.pollapp.Pollapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username , String email);
    Optional<User> findByUsername(String username);
    List<User> findById(List<Long> userIds);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
