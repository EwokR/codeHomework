package com.example.codehomework.repository;


import com.example.codehomework.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Optional<Avatar> findByStudent_idStudent(Long id);
}
