package com.kishore.taskproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kishore.taskproject.entity.Task;

public interface TaskRepositary extends JpaRepository<Task,Long>{

	List<Task> findAllByUsersId(long userid);

}
