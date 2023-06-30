package com.example.doctorbackend.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBaseException{
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String message,Throwable throwable){
        super(message);
    }
    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
    public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
