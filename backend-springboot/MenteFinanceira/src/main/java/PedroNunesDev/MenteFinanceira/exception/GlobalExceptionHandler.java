package PedroNunesDev.MenteFinanceira.exception;

import PedroNunesDev.MenteFinanceira.exception.model.DefaultExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(ResourceNotFoundException e){

        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = status.name();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), e.getMessage());

        return ResponseEntity.status(status).body(exceptionModel);
    }

    @ExceptionHandler(UsuarioNaoVerificadoException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(UsuarioNaoVerificadoException e){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = status.name();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), e.getMessage());

        return ResponseEntity.status(status).body(exceptionModel);
    }

    @ExceptionHandler(RecursoInvalidoException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(RecursoInvalidoException e){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = status.name();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), e.getMessage());

        return ResponseEntity.status(status).body(exceptionModel);
    }

    @ExceptionHandler(ConflitoRecursosException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(ConflitoRecursosException e){

        HttpStatus status = HttpStatus.CONFLICT;
        String error = status.name();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), e.getMessage());

        return ResponseEntity.status(status).body(exceptionModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(MethodArgumentNotValidException e){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = status.name();

        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), message);

        return ResponseEntity.status(status).body(exceptionModel);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultExceptionModel> defaultExceptionModelResponseEntity(IllegalArgumentException e){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = status.name();

        DefaultExceptionModel exceptionModel = new DefaultExceptionModel(Instant.now(), error, status.value(), "Argumento inválido: "+e.getMessage());

        return ResponseEntity.status(status).body(exceptionModel);
    }
}
