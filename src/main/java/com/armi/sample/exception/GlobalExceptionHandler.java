package com.armi.sample.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseBody
    public ResponseEntity handleHttpStatusCodeException (HttpStatusCodeException e)  {
        ErrorResponse errorResponse = parseErrorResponse(e);
        int status = Integer.parseInt(errorResponse.getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseBody
    public ResponseEntity handleResourceAccessException( ResourceAccessException e, HttpServletRequest req ){
        ErrorResponse errorResponse = compose(e, HttpStatus.INTERNAL_SERVER_ERROR, req);
        int status = Integer.parseInt(errorResponse.getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler({LoginException.class})
    @ResponseBody
    public ResponseEntity handleLoginException ( LoginException e, HttpServletRequest req ){
        ErrorResponse errorResponse = compose(e, HttpStatus.UNAUTHORIZED, req);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException( Exception e, HttpServletRequest req ){
        ErrorResponse errorResponse = compose(e, HttpStatus.INTERNAL_SERVER_ERROR, req);
        int status = Integer.parseInt(errorResponse.getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    public static ErrorResponse compose( Exception e, HttpStatus httpStatus, HttpServletRequest req ){
        ErrorResponse errorResponse = new ErrorResponse();
        Date date = new Date();
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            time = sdf.format(date);
        }catch(Exception ex){
        }
        errorResponse.setTimestamp(time);
        errorResponse.setStatus(String.valueOf(httpStatus.value()));
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(ExceptionUtils.getRootCauseMessage(e));
        errorResponse.setPath(req !=null ? req.getRequestURI() : "");
        return errorResponse;
    }


    public static ErrorResponse parseErrorResponse(Exception e) {
        try{
            JSONObject j = new JSONObject(((HttpStatusCodeException) e).getResponseBodyAsString());
            String timestamp ,status , error , message, path;
            timestamp = j.getString("timestamp");
            status = j.getString("status");
            error = j.getString("error");
            message = j.getString("message");
            path = j.getString("path");
            return new ErrorResponse(timestamp,status,error,message,path);
        }catch (JSONException ex){
            return compose(ex, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }
}
