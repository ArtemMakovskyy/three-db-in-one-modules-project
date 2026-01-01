package com.store.security.repository;

import com.store.security.model.RoleName;
import com.store.security.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.micrometer.observation.annotation.Observed;

@Observed
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByEmail(String email);

    @Query("""
            SELECT u FROM User u JOIN u.roles r 
            WHERE r.name = :roleName AND u.isDeleted = FALSE AND r.isDeleted = FALSE""")
    List<User> findUsersByRole(@Param("roleName") RoleName roleName);
}
