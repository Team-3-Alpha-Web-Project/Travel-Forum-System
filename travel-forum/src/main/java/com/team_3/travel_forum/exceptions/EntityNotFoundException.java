package com.team_3.travel_forum.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String type) {
        super(String.format("%s not found.", type));
    }

    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }
}
