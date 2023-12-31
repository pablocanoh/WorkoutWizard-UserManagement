package edu.uoc.workoutwizardusermanagement.repositories;

import edu.uoc.workoutwizardusermanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    List<User> findAllByLoginAttempsGreaterThan(int loginAttempts);

    void updateUserById(UUID id, User user);
}

