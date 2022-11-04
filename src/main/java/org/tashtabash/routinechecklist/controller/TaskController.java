package org.tashtabash.routinechecklist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task")
public class TaskController {
    @GetMapping(value="/test")
    public String getTest() {
        return "Hello world";
    }
}
