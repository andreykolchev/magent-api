package com.magent.controller.loggerlevel;

import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.servicemodule.utils.validators.ImageValidatorImpl;
import com.magent.servicemodule.utils.validators.OnBoardingValidatorImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Created  on 23.05.2016.
 */
@ControllerAdvice
public class LoggerController {
    private final static Logger LOGGER = Logger.getLogger(LoggerController.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            RollbackException.class, javax.persistence.RollbackException.class, IllegalArgumentException.class, DataIntegrityViolationException.class, ConstraintViolationException.class})
    public void conflict(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        LOGGER.warn("WARNING exception: " + request.getRequestURI() + " with exception " + e + " " + Arrays.toString(e.getStackTrace()));
        exceptionWriter(request, response, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, NullPointerException.class})
    public void notFoundException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        exceptionWriter(request, response, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IOException.class, ParseException.class})
    public void IOException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        exceptionWriter(request, response, e);
        LOGGER.warn("WARNING IOException: " + request.getRequestURI() + " with exception " + e + " " + Arrays.toString(e.getStackTrace()));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({Exception.class})
    public void exception(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        LOGGER.warn("WARNING NOT KNOWABLE EXCEPTION: " + request.getRequestURI() + " with exception " + e + " " + Arrays.toString(e.getStackTrace()));
    }

    @ResponseStatus(HttpStatus.LOCKED)
    @ExceptionHandler({UserValidatorImpl.UserIsBlockedException.class})
    public void userIsBlocked(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        exceptionWriter(request, response, e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({OnBoardingValidatorImpl.InvalidOnboardEntity.class, ImageValidatorImpl.NotCorrectImageExtension.class, ValidationException.class})
    public void badOnboardEntity(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        response.getWriter().println("bad request : " + e.getMessage());
        LOGGER.info("bad request : " + request.getRequestURI() + " with exception " + e);
    }

    @SuppressFBWarnings({"XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER", "CRLF_INJECTION_LOGS"})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OAuth2AccessDeniedException.class)
    public void accessDinied(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        OAuth2Exception ex = OAuth2Exception.create("error", "Unexpected exception.");
        String referesh = request.getParameter("refreshToken");
        response.getWriter().println(ex + " " + " with token " + referesh);
        LOGGER.info("accessDinied exception : " + request.getRequestURI() + " with exception " + e);
    }

    private void exceptionWriter(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        response.getWriter().println("message " + e.getMessage());
        LOGGER.info("exception in : " + request.getRequestURI() + " with exception " + e);
    }
}
