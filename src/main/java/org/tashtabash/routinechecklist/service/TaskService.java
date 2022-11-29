package org.tashtabash.routinechecklist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.repository.TaskRepository;

import javax.transaction.Transactional;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(long id) {
        Task task = taskRepository.getTask(id);

        if (task == null) {
            throw new NoTaskFoundException(id);
        }

        return task;
    }

    public Task saveTask(String name) {
        Task task = new Task(name);

        return taskRepository.saveTask(task);
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
