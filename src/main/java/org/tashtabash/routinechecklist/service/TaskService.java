package org.tashtabash.routinechecklist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.repository.TaskRepository;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(long id) {
        return taskRepository.getTask(id);
    }

    public Task saveTask(String name) {
        Task task = new Task(name);

        long id = taskRepository.saveTask(task);

        return getTask(id);
    }
}
