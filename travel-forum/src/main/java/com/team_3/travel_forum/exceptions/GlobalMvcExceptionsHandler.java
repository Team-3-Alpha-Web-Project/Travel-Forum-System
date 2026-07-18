package com.team_3.travel_forum.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice(basePackages = "com.team_3.travel_forum.controllers.mvc")
public class GlobalMvcExceptionsHandler {

    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred on the server.";

    private static final Logger logger = LoggerFactory.getLogger(GlobalMvcExceptionsHandler.class);

    @ExceptionHandler({BlockedUserException.class, UnauthorizedAccessException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenActions(RuntimeException ex, Model model) {
        return buildErrorView(HttpStatus.FORBIDDEN, ex.getMessage(), model);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException ex, Model model) {
        return buildErrorView(HttpStatus.NOT_FOUND, ex.getMessage(), model);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Unexpected exception: {}", ex.getMessage(), ex);

        return buildErrorView(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_MESSAGE, model);
    }

    private String buildErrorView(HttpStatus status, String message, Model model) {
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("statusCode", status.value());
        model.addAttribute("statusPhrase", status.getReasonPhrase());
        model.addAttribute("message", message);

        return "error-page";
    }
}