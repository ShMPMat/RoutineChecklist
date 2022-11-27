package org.tashtabash.routinechecklist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc() {
        mockMvc = standaloneSetup(taskController).build();
    }

    @Test
    void getTask() throws Exception {
        var task = new Task(1, "Test Task");
        when(taskService.getTask(1))
                .thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    void getTaskAnswers404OnNoTask() throws Exception {
        when(taskService.getTask(1))
                .thenThrow(new NoTaskFoundException(1));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveTask() throws Exception {
        var taskName = "Task Name";
        var resultTask = new Task(1, taskName);
        when(taskService.saveTask(taskName))
                .thenReturn(resultTask);

        mockMvc.perform(post("/tasks").content(taskName))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(resultTask)));
    }

    @ParameterizedTest()
    @ValueSource(strings = { "", "    ", "\t", "\u205F" })
    void saveTaskThrows400WhenWhitespaceName(String emptyName) throws Exception {
        mockMvc.perform(
                post("/tasks").content(emptyName)
                        .contentType("text/plain")
                        .characterEncoding("UTF-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[] {}));
    }

    @Test
    void deleteTaskAnswers400OnAbsentId() throws Exception {
        doThrow(new NoTaskFoundException(1))
                .when(taskService).deleteTask(1);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound());
    }
}
