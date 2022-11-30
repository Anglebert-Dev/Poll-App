package com.pollapp.Pollapp.repositiory;

import com.pollapp.Pollapp.model.Role;
import com.pollapp.Pollapp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
