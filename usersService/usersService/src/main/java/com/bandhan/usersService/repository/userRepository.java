package com.bandhan.usersService.repository;

import com.bandhan.usersService.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface userRepository extends JpaRepository<Users,Long> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
}
