package org.tashtabash.routinechecklist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.repository.TaskRepository;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
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
    void searchTask() {
        List<Task> tasks = List.of(new Task(random.nextInt(), "Name"), new Task(random.nextInt(), "Name2"));
        when(taskRepository.searchTasks())
                .thenReturn(tasks);

        List<Task> foundTasks = taskService.searchTasks();

        assertThat(foundTasks)
                .containsExactlyInAnyOrderElementsOf(tasks);
    }

    @Test
    void searchTaskReturnsEmptyListOnNoTask() {
        when(taskRepository.searchTasks())
                .thenReturn(List.of());

        List<Task> foundTasks = taskService.searchTasks();

        assertThat(foundTasks)
                .isEmpty();
    }

    @Test
    void updateTask() {
        var task = new Task(random.nextInt(), "name");
        when(taskRepository.updateTask(task))
                .thenReturn(task);
        when(taskRepository.getTask(task.getId()))
                .thenReturn(task);

        var returnedTask = taskService.updateTask(task);

        verify(taskRepository, times(1))
                .updateTask(task);
        assertEquals(task, returnedTask);
    }

    @Test
    void updateTaskThrowsOnAbsentId() {
        assertThrows(
                NoTaskFoundException.class,
                () -> taskService.updateTask(new Task(1, "Task name"))
        );
    }

    @Test
    void deleteTask() {
        long id = random.nextInt();
        when(taskRepository.deleteTask(id))
                .thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository, only()).deleteTask(id);
    }

    @Test
    void deleteTaskThrowsOnAbsentId() {
        when(taskRepository.deleteTask(anyLong()))
                .thenReturn(false);

        assertThrows(
                NoTaskFoundException.class,
                () -> taskService.deleteTask(1)
        );
    }
}
