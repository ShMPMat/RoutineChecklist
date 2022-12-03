package org.tashtabash.routinechecklist.repository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Repository;
import org.tashtabash.routinechecklist.entity.Task;


@Repository
public class TaskRepository {
    @Lookup
    protected Session getSession() {
        throw new UnsupportedOperationException("getSession() wasn't overridden in the implementation");
    }

    public Task saveTask(Task task) {
        try (Session session = getSession()) {
            session.save(task);

            return task;
        }
    }

    public Task getTask(long id) {
        try (Session session = getSession()) {
            return session.get(Task.class, id);
        }
    }

    //TODO search

    public Task updateTask(Task task) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        }
        return task;
    }

    public boolean deleteTask(long id) {
        try (Session session = getSession()) {
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
