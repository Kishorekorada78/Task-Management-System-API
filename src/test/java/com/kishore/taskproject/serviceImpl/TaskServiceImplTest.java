package com.kishore.taskproject.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.kishore.taskproject.entity.Task;
import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.exception.ApiException;
import com.kishore.taskproject.exception.TaskNotFound;
import com.kishore.taskproject.exception.UserNotFound;
import com.kishore.taskproject.payload.TaskDTO;
import com.kishore.taskproject.repository.TaskRepositary;
import com.kishore.taskproject.repository.UserRepositary;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

	@Mock
	private UserRepositary userRepo;   // Fake UserRepository
	
	@Mock
	private TaskRepositary taskRepo;
	
	 @Mock
	 private ModelMapper modelMapper;

     @InjectMocks						// Injects above objects
     private TaskServiceImple taskService;
     
     @Test
     void shouldReturnTaskWhenUserAndTaskExist() {

         Users user = new Users();
         user.setId(1L);

         Task task = new Task();
         task.setId(100L);
         task.setUsers(user);

         TaskDTO taskDTO = new TaskDTO();
         taskDTO.setId(100L);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.of(task));

         when(modelMapper.map(
                 task,
                 TaskDTO.class))
                 .thenReturn(taskDTO);

         TaskDTO result =
                 taskService.getTask(
                         1L,
                         100L);

         assertNotNull(result);

         assertEquals(
                 100L,
                 result.getId());
     }
     @Test
     void shouldThrowUserNotFoundWhenUserDoesNotExist() {

     when(userRepo.findById(1L))
             .thenReturn(Optional.empty());

     assertThrows(
             UserNotFound.class,
             () -> taskService.getTask(
                     1L,
                     100L));
     }
     
     @Test
     void shouldThrowTaskNotFoundWhenTaskDoesNotExist() {

         Users user = new Users();
         user.setId(1L);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.empty());

         assertThrows(
                 TaskNotFound.class,
                 () -> taskService.getTask(
                         1L,
                         100L));
     }
     
     
     @Test
     void shouldThrowApiExceptionWhenTaskBelongsToAnotherUser() {

         Users user1 = new Users();
         user1.setId(1L);

         Users user2 = new Users();
         user2.setId(2L);

         Task task = new Task();
         task.setId(100L);

         task.setUsers(user2);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user1));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.of(task));

         assertThrows(
                 ApiException.class,
                 () -> taskService.getTask(
                         1L,
                         100L));
     }
     
     @Test
     void shouldDeleteTaskSuccessfully() {

         Users user = new Users();
         user.setId(1L);

         Task task = new Task();
         task.setId(100L);
         task.setUsers(user);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.of(task));

         taskService.deleteTask(
                 1L,
                 100L);

         verify(taskRepo)
                 .deleteById(100L);
     }
     
     @Test
     void shouldSaveTaskSuccessfully() {

         Users user = new Users();
         user.setId(1L);

         TaskDTO taskDTO = new TaskDTO();
         taskDTO.setTaskname("Learn Spring Boot");

         Task task = new Task();
         task.setTaskname("Learn Spring Boot");

         Task savedTask = new Task();
         savedTask.setId(100L);
         savedTask.setTaskname("Learn Spring Boot");
         savedTask.setUsers(user);

         TaskDTO responseDTO = new TaskDTO();
         responseDTO.setId(100L);
         responseDTO.setTaskname("Learn Spring Boot");

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(modelMapper.map(
                 taskDTO,
                 Task.class))
                 .thenReturn(task);

         when(taskRepo.save(task))
                 .thenReturn(savedTask);

         when(modelMapper.map(
                 savedTask,
                 TaskDTO.class))
                 .thenReturn(responseDTO);

         TaskDTO result =
                 taskService.saveTask(
                         1L,
                         taskDTO);

         assertNotNull(result);

         assertEquals(
                 100L,
                 result.getId());

         assertEquals(
                 "Learn Spring Boot",
                 result.getTaskname());

         verify(taskRepo)
                 .save(task);
     }

     @Test
     void shouldAssignUserBeforeSavingTask() {

         Users user = new Users();
         user.setId(1L);

         TaskDTO taskDTO = new TaskDTO();
         taskDTO.setTaskname("Spring Boot");

         Task task = new Task();

         Task savedTask = new Task();
         savedTask.setId(100L);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(modelMapper.map(
                 taskDTO,
                 Task.class))
                 .thenReturn(task);

         when(taskRepo.save(task))
                 .thenReturn(savedTask);

         when(modelMapper.map(
                 savedTask,
                 TaskDTO.class))
                 .thenReturn(new TaskDTO());

         taskService.saveTask(
                 1L,
                 taskDTO);

         ArgumentCaptor<Task> captor =
                 ArgumentCaptor.forClass(Task.class);

         verify(taskRepo)
                 .save(captor.capture());

         Task capturedTask =
                 captor.getValue();

         assertEquals(
                 1L,
                 capturedTask.getUsers().getId());
     }
    
     // 1. deleteTask() -> User Not Found
     @Test
     void shouldThrowUserNotFoundWhenDeletingTask() {

         when(userRepo.findById(1L))
                 .thenReturn(Optional.empty());

         assertThrows(
                 UserNotFound.class,
                 () -> taskService.deleteTask(
                         1L,
                         100L));
     }
     
     // 2. deleteTask() -> Task Not Found
     @Test
     void shouldThrowTaskNotFoundWhenDeletingTask() {

         Users user = new Users();
         user.setId(1L);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.empty());

         assertThrows(
                 TaskNotFound.class,
                 () -> taskService.deleteTask(
                         1L,
                         100L));
     }
     
     // 3. deleteTask() -> Wrong Owner
     @Test
     void shouldThrowApiExceptionWhenDeletingOthersTask() {

         Users user1 = new Users();
         user1.setId(1L);

         Users user2 = new Users();
         user2.setId(2L);

         Task task = new Task();
         task.setId(100L);
         task.setUsers(user2);

         when(userRepo.findById(1L))
                 .thenReturn(Optional.of(user1));

         when(taskRepo.findById(100L))
                 .thenReturn(Optional.of(task));

         assertThrows(
                 ApiException.class,
                 () -> taskService.deleteTask(
                         1L,
                         100L));
     }
}
