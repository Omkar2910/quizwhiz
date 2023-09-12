package com.quizwhiz.repository;

import com.quizwhiz.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {

}
