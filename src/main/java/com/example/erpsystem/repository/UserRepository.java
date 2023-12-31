package com.example.erpsystem.repository;

import com.example.erpsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUserName(String userName);

    Set<UserEntity> findByUserNameIn(Set<String> userName);

    Optional<UserEntity> findByEmail(String email);
}
