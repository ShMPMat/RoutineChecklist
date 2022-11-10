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
    public Task getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @PostMapping(value="/")
    public ResponseEntity<Task> getTask(@RequestBody String name) {
        return ResponseEntity.ok(taskService.saveTask(name));
    }
}
