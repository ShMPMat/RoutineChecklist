package org.tashtabash.routinechecklist.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tashtabash.routinechecklist.RoutineChecklistConfiguration;
import org.tashtabash.routinechecklist.entity.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { RoutineChecklistConfiguration.class }
)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
    }

    @AfterEach
    void closeSession() {
        session.close();
        session = null;
    }

    @Test
    void saveTask() {
        Task task = new Task("Test name");
        taskRepository.saveTask(task);

        List<Task> tasks = session.createQuery("SELECT t from Task t", Task.class)
                .list();

        assertEquals(1, tasks.size());
        assertEquals(task.getName(), tasks.get(0).getName());
        assertNotEquals(0, tasks.get(0).getId());
    }

    @Test
    void getTask() {
        session.beginTransaction();
        Task task = new Task("Test name");
        long id = (long) session.save(task);
        task.setId(id);
        session.getTransaction().commit();

        Task foundTask = taskRepository.getTask(id);

        assertEquals(task, foundTask);
    }

    @Test
    void getTaskReturnsNullOnNoTask() {
        Task foundTask = taskRepository.getTask(1);

        assertNull(foundTask);
    }

    @Test
    void updateTask() {
        session.beginTransaction();
        Task task = new Task("Test name");
        long id = (long) session.save(task);
        task.setId(id);
        session.getTransaction().commit();

        var newTask = new Task(id, "New name");

        taskRepository.updateTask(newTask);

        session.close();
        openSession();

        List<Task> tasks = session.createQuery("SELECT t FROM Task t", Task.class)
                .list();

        assertEquals(1, tasks.size());
        assertEquals(newTask.getName(), tasks.get(0).getName());
        assertEquals(newTask.getId(), tasks.get(0).getId());
    }

    @Test
    void deleteTask() {
        session.beginTransaction();
        Task task = new Task("Test name");
        long id = (long) session.save(task);
        task.setId(id);
        session.getTransaction().commit();

        taskRepository.deleteTask(id);

        List<Task> tasks = session.createQuery("SELECT t from Task t", Task.class)
                .list();

        assertEquals(0, tasks.size());
    }

    @Test
    void deleteTaskReturnsFalseOnNoTask() {
        long id = 1;

        boolean success = taskRepository.deleteTask(id);

        assertFalse(success);
    }
}
