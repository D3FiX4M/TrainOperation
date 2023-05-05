package d3fix4m.ru.train_operation_test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MyError> handleAnyException(MyException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(ex.getError()
                        .getStatus())
                .body(ex.getError());
    }
}
