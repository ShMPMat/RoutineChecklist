package org.tashtabash.routinechecklist.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NoTaskFoundException extends ResponseStatusException {
    public NoTaskFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "No Task with id " + id + " found");
    }
}
