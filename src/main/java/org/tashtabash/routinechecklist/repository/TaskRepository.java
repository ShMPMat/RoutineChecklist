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


    public Task getTask(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Task.class, id);
        }
    }

    //TODO search

    public long saveTask(Task task) {
        try (Session session = sessionFactory.openSession()) {
            return (long) session.save(task);
        }
    }

//    public void update(Task task) {
//        try (Session session = sessionFactory.openSession()) {
//            session.update(task);
//        }
//    }
//
//    public void delete(Task task) {
//        try (Session session = sessionFactory.openSession()) {
//            session.delete(task);
//        }
//    }
}
