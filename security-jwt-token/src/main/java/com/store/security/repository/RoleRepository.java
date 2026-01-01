package com.store.security.repository;

import com.store.security.model.Role;
import com.store.security.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import io.micrometer.observation.annotation.Observed;

@Observed
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
