package org.tashtabash.routinechecklist.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.tashtabash.routinechecklist.RoutineChecklistConfiguration;
import org.tashtabash.routinechecklist.entity.Task;
import org.tashtabash.routinechecklist.service.NoTaskFoundException;
import org.tashtabash.routinechecklist.service.TaskService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration( classes = { RoutineChecklistConfiguration.class })
@WebAppConfiguration
class TaskControllerTest {
    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc() {
        mockMvc = standaloneSetup(taskController).build();
    }

    @Test
    void getTask() throws Exception {
        when(taskService.getTask(1))
                .thenReturn(new Task(1, "Test Task"));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"id\": 1, \"name\": \"Test Task\"}"));
    }

    @Test
    void getTaskAnswers404OnNoTask() throws Exception {
        when(taskService.getTask(1))
                .thenThrow(new NoTaskFoundException(1));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveTask() throws Exception {
        var taskName = "Task Name";
        when(taskService.saveTask(taskName))
                .thenReturn(new Task(1, taskName));

        mockMvc.perform(post("/task/").content(taskName))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"id\": 1, \"name\": \"Task Name\"}"));
    }
}