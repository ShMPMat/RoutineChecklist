package org.tashtabash.routinechecklist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.service.TaskService;


@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        Task task = taskService.getTask(id);

        return ResponseEntity.ok(task);
    }

    @PostMapping(value="/")
    public ResponseEntity<Task> getTask(@RequestBody String name) {
        Task newTask = taskService.saveTask(name);

        return ResponseEntity.ok(newTask);
    }
}
