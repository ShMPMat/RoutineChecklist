package org.tashtabash.routinechecklist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.service.TaskService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value="")
    public ResponseEntity<Task> saveTask(@RequestBody String name) {
        if (name.strip().equals("")) {
            return ResponseEntity.badRequest()
                    .build();
        }

        Task newTask = taskService.saveTask(name);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newTask);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        Task task = taskService.getTask(id);

        return ResponseEntity.ok(task);
    }

    @GetMapping(value="")
    public ResponseEntity<List<Task>> searchTasks() {
        List<Task> tasks = taskService.searchTasks();

        return ResponseEntity.ok(tasks);
    }

    @PutMapping("")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        if (task.getName().strip().equals("")) {
            return ResponseEntity.badRequest()
                    .build();
        }

        Task updatedTask = taskService.updateTask(task);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        taskService.deleteTask(id);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
