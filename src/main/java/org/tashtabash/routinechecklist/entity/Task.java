package org.tashtabash.routinechecklist.entity;


import javax.persistence.*;

@Entity
public class Task {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    //TODO dates

    public Task() {}

    public Task(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
