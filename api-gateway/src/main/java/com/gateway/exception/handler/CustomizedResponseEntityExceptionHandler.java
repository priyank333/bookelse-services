/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gateway.exception.handler;

import com.gateway.exception.InvalidCredentialsException;
import com.gateway.exception.OperationError;
import com.gateway.exception.model.ApiError;
import com.gateway.exception.model.ExceptionResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Priyank Agrawal
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OperationError.class)
    public final ResponseEntity<ExceptionResponse> handleOperationError(
            OperationError exception,
            WebRequest webRequest) {
        logger.error("Error: {}", exception);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                exception.getHttpStatus(),
                LocalDateTime.now(),
                exception.getMessage()), exception.getResource()),
                exception.getHttpStatus());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public final ResponseEntity<ExceptionResponse>
            handleInvalidCredentialException(
                    InvalidCredentialsException exception,
                    WebRequest webRequest) {
        logger.error("InvalidCredentialException", exception);
        Map<String, Object> messageParam = new HashMap<>();
        messageParam.put("rejectedUserId", exception.getUsername());
        return new ResponseEntity<>(
                new ExceptionResponse(
                        new ApiError(
                                HttpStatus.UNAUTHORIZED,
                                LocalDateTime.now(),
                                exception.getMessage()), messageParam),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<Object> handleSQLException(
            SQLException exception,
            WebRequest webRequest) {
        logger.error("SQLException", exception);
        Map<String, Object> message = new HashMap<>();
        message.put("errorCode", exception.getErrorCode());
        message.put("sqlState", exception.getSQLState());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(),
                exception.getMessage()), message),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleIOException(
            RuntimeException exception,
            WebRequest webRequest) {
        logger.error("IOException", exception);
        Map<String, Object> message = new HashMap<>();
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(),
                exception.getMessage()), message),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest webRequest) {
        logger.error("ConstraintViolationException", exception);
        Map<String, Object> message = new HashMap<>();
        Iterator<ConstraintViolation<?>> exceptionIter
                = exception.getConstraintViolations().iterator();
        List<HashMap<String, Object>> violoationList = new ArrayList<>();
        while (exceptionIter.hasNext()) {
            HashMap<String, Object> messageMap = new HashMap<>();
            ConstraintViolation<?> constraintViolation = exceptionIter.next();
            messageMap.put("message", constraintViolation.getMessage());
            messageMap.put("executableParameters", constraintViolation.getExecutableParameters());
            messageMap.put("invalidValue", constraintViolation.getInvalidValue());
            violoationList.add(messageMap);
        }
        message.put("constraintViolations", violoationList);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                HttpStatus.UNPROCESSABLE_ENTITY,
                LocalDateTime.now(),
                exception.getMessage()), message),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException hrmnse,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("HttpRequestMethodNotSupported: {}", hrmnse);
        Map<String, Object> message = new HashMap<>();
        message.put("requestedMethod", hrmnse.getMethod());
        message.put("supportedMethods", hrmnse.getSupportedHttpMethods());
        return new ResponseEntity<>(new ExceptionResponse(
                new ApiError(
                        hs,
                        LocalDateTime.now(),
                        hrmnse.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object o,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("ExceptionInternal: {}", exception);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                exception.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException arte,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("AsyncRequestTimeoutException: {}", arte);
        return new ResponseEntity<>(
                new ExceptionResponse(new ApiError(
                        hs,
                        LocalDateTime.now(),
                        arte.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException nhfe,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("NoHandlerFoundException: {}", nhfe);
        Map<String, Object> message = new HashMap<>();
        message.put("requestedMethod", nhfe.getHttpMethod());
        message.put("requestURL", nhfe.getRequestURL());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                nhfe.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException be,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("BindException: {}", be);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                be.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException msrpe,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("MissingServletRequestPart: {}", msrpe);
        Map<String, Object> message = new HashMap<>();
        message.put("requestPartName", msrpe.getRequestPartName());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                msrpe.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException manve,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("MethodArgumentNotValid: {}", manve);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                manve.getMessage(), manve.getBindingResult().getAllErrors())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException hmnwe,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("HttpMessageNotWritable: {}", hmnwe);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                hmnwe.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException hmnre,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("HttpMessageNotReadable: {}", hmnre);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                hmnre.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException tme,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("TypeMismatch: {}", tme);
        Map<String, Object> message = new HashMap<>();
        message.put("errorCode", tme.getErrorCode());
        message.put("propertyName", tme.getPropertyName());
        message.put("requiredType", tme.getRequiredType());
        message.put("value", tme.getValue());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                tme.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException cnse,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("ConversionNotSupported: {}", cnse);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                cnse.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException srbe,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("ServletRequestBindingException: {}", srbe);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                srbe.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException msrpe,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("MissingServletRequestParameter: {}", msrpe);
        Map<String, Object> message = new HashMap<>();
        message.put("parameterName", msrpe.getParameterName());
        message.put("parameterType", msrpe.getParameterType());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                msrpe.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException mpve,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("MissingPathVariable: {}", mpve);
        Map<String, Object> message = new HashMap<>();
        message.put("variableName", mpve.getVariableName());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                mpve.getMessage()), message),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException hmtnae,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("HttpMediaTypeNotAcceptable: {}", hmtnae);
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                hmtnae.getMessage())),
                hs);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException hmtnse,
            HttpHeaders hh,
            HttpStatus hs,
            WebRequest wr) {
        logger.error("HttpMediaTypeNotSupported: {}", hmtnse);
        Map<String, Object> message = new HashMap<>();
        message.put("contentType", hmtnse.getContentType());
        return new ResponseEntity<>(new ExceptionResponse(new ApiError(
                hs,
                LocalDateTime.now(),
                hmtnse.getMessage()), message),
                hs);
    }
}
