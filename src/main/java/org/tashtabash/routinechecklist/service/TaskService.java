package org.tashtabash.routinechecklist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(String name) {
        Task task = new Task(name);

        return taskRepository.saveTask(task);
    }

    public Task getTask(long id) {
        Task task = taskRepository.getTask(id);

        if (task == null) {
            throw new NoTaskFoundException(id);
        }

        return task;
    }

    public List<Task> searchTasks() {
        List<Task> tasks = taskRepository.searchTasks();

        return tasks;
    }

    @Transactional
    public Task updateTask(Task task) {
        getTask(task.getId());

        return taskRepository.updateTask(task);
    }

    public void deleteTask(long id) {
        boolean success = taskRepository.deleteTask(id);

        if (!success) {
            throw new NoTaskFoundException(id);
        }
    }
}
