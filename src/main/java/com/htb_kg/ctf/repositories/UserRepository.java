package com.htb_kg.ctf.repositories;

import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndNickname(String email, String nickname);
    Optional<User> findByEmailOrNickname(String email, String nickname);
    List<User> findAllByToBusiness(Boolean business);
    Optional<User> findByRole(Role role);
    void deleteByRole(Role role);
}
