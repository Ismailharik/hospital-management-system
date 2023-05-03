package com.example.doctorbackend.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ApiBaseException.class) // this notation tell spring to execute this method when NotFoundException thrown
    public ResponseEntity<ErrorDetails> handleApiExceptions(ApiBaseException ex, WebRequest request){
        ErrorDetails details=new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details,ex.getStatusCode()  );
    }

    /*
    * This method designed to handle Validation Errors like @NotNull @Unique ...
    * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ValidationError validationError=new ValidationError();
        ex.getBindingResult().getFieldErrors().stream().
                forEach(
                        fieldError -> validationError.addError(fieldError.getDefaultMessage())
                );
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}
