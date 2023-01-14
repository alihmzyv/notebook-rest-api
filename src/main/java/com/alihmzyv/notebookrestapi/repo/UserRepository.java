package com.alihmzyv.notebookrestapi.repo;

import com.alihmzyv.notebookrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.emailAddress = ?1 or u.username = ?1")
    Optional<User> findByEmailAddressOrUsername(String emailAddressOrUsername);

    boolean existsByUsername(String username);
    boolean existsByEmailAddress(String username);
}
