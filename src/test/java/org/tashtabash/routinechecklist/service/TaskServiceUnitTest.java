package org.tashtabash.routinechecklist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.repository.TaskRepository;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    private final Random random = new Random();

    @Test
    void getTask() {
        long id = random.nextInt();
        Task expectedTask = new Task(id, "Test name");
        when(taskRepository.getTask(id))
                .thenReturn(expectedTask);

        Task task = taskService.getTask(id);

        assertEquals(
                expectedTask,
                task
        );
    }

    @Test
    void getTaskThrowsOnNoTask() {
        assertThrows(
                NoTaskFoundException.class,
                () -> taskService.getTask(1)
        );
    }

    @Test
    void saveTask() {
        Task expectedTask = new Task(random.nextInt(), "Test name");
        when(taskRepository.saveTask(new Task(expectedTask.getName())))
                .thenReturn(expectedTask);

        Task task = taskService.saveTask(expectedTask.getName());

        assertEquals(
                expectedTask,
                task
        );
    }

    @Test
    void updateTask() {
        var task = new Task(random.nextInt(), "name");
        when(taskRepository.update(task))
                .thenReturn(task);
        when(taskRepository.getTask(task.getId()))
                .thenReturn(task);

        var returnedTask = taskService.updateTask(task);

        verify(taskRepository, times(1))
                .update(task);
        assertEquals(task, returnedTask);
    }

    @Test
    void updateTaskThrowsOnAbsentId() {
//        when(taskRepository.update(anyLong()))
//                .thenReturn(false);

        assertThrows(
                NoTaskFoundException.class,
                () -> taskService.updateTask(new Task(1, "Task name"))
        );
    }

    @Test
    void deleteTask() {
        long id = random.nextInt();
        when(taskRepository.delete(id))
                .thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository, only()).delete(id);
    }

    @Test
    void deleteTaskThrowsOnAbsentId() {
        when(taskRepository.delete(anyLong()))
                .thenReturn(false);

        assertThrows(
                NoTaskFoundException.class,
                () -> taskService.deleteTask(1)
        );
    }
}
