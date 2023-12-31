package edu.uoc.workoutwizardusermanagement.repositories;

import edu.uoc.workoutwizardusermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    List<User> findAllByLoginAttempsGreaterThan(int loginAttempts);

    @Modifying
    @Query("update User u set u.loginAttempts = ?1 where u.id = ?2")
    void updateUserLoginAttemptsById(int loginAttempts, UUID id);
}

