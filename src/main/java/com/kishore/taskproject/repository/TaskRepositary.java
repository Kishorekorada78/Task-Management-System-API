package com.kishore.taskproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kishore.taskproject.entity.Task;

public interface TaskRepositary extends JpaRepository<Task,Long>{

	Page<Task> findAllByUsersId(long userid,Pageable pageable);

}
