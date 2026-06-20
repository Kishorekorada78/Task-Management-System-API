package com.kishore.taskproject.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kishore.taskproject.payload.TaskDTO;
import com.kishore.taskproject.security.JwtAuthenticationFilter;
import com.kishore.taskproject.security.JwtTokenProvider;
import com.kishore.taskproject.service.TaskService;

@WebMvcTest(TaskController.class)    // it loads only controller , MockMvc , Jackson not Database , JPA , Hibernate.
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;
	
	 @MockBean
	private TaskService taskService;
	 
	 @Autowired
	 private ObjectMapper objectMapper;
	 
	 @Test
	 @WithMockUser
	 void shouldReturnTask() throws Exception {

	     TaskDTO taskDTO =
	             new TaskDTO();

	     taskDTO.setId(100L);
	     taskDTO.setTaskname("Spring Boot");

	     when(taskService.getTask(
	             1L,
	             100L))
	             .thenReturn(taskDTO);

	     mockMvc.perform(
	             get("/api/1/tasks/100"))
	             .andExpect(
	                     status().isOk())
	             .andExpect(
	                     jsonPath("$.id")
	                             .value(100))
	             .andExpect(
	                     jsonPath("$.taskname")
	                             .value("Spring Boot"));
	 }
	 
	 @Test
	 void shouldCreateTask() throws Exception {

	     TaskDTO requestDTO = new TaskDTO();
	     requestDTO.setTaskname("Learn Kafka");

	     TaskDTO responseDTO = new TaskDTO();
	     responseDTO.setId(100L);
	     responseDTO.setTaskname("Learn Kafka");

	     when(taskService.saveTask(
	    	        org.mockito.ArgumentMatchers.eq(1L),
	    	        org.mockito.ArgumentMatchers.any(TaskDTO.class)))
	             .thenReturn(responseDTO);

	     mockMvc.perform(
	             post("/api/1/tasks")
	                     .contentType(
	                             MediaType.APPLICATION_JSON)
	                     .content(
	                             objectMapper.writeValueAsString(
	                                     requestDTO)))
	             .andExpect(
	                     status().isCreated())
	             .andExpect(
	                     jsonPath("$.id")
	                             .value(100))
	             .andExpect(
	                     jsonPath("$.taskname")
	                             .value("Learn Kafka"));
	 }
	 

@Test
	void shouldDeleteTask() throws Exception {

    	doNothing().when(taskService)
               .deleteTask(1L, 100L);

    	mockMvc.perform(
            delete("/api/1/tasks/100"))
            .andExpect(
                    status().isOk())
            .andExpect(
                    jsonPath("$.message")
                            .value("task deleted successfully"));
     }
}
