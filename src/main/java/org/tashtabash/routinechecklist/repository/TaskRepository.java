package org.tashtabash.routinechecklist.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tashtabash.routinechecklist.entity.Task;


@Repository
public class TaskRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Task saveTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.save(task);

            return task;
        }
    }

    public Task getTask(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Task.class, id);
        }
    }

    //TODO search

    public Task updateTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        }
        return task;
    }

    public boolean deleteTask(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Task task = getTask(id);

            if (task != null) {
                session.delete(task);
            }

            session.getTransaction().commit();

            return task != null;
        }
    }
}
