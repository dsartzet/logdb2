package com.logdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static final String ERROR = "/error";
    protected static final String UNKNOWN_ERROR = "Unknown error";
    protected static final String ERROR_MESSAGE = "errorMessage";
    protected static final String EXCEPTION_DURING_EXECUTION_OF_SPRING_SECURITY_APPLICATION = "Exception during execution of SpringSecurity application";
    protected static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable) {
        logger.error(EXCEPTION_DURING_EXECUTION_OF_SPRING_SECURITY_APPLICATION, throwable);
        ModelAndView modelAndView = new ModelAndView(ERROR);
        String errorMessage = (throwable != null ? throwable.toString() : UNKNOWN_ERROR);
        modelAndView.addObject(ERROR_MESSAGE, errorMessage);
        return modelAndView;
    }

}
