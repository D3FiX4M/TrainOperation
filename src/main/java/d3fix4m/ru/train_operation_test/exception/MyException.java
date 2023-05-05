package d3fix4m.ru.train_operation_test.exception;

import lombok.Getter;

@Getter

public class MyException extends RuntimeException {

    private final MyError error;

    public MyException(MyError error) {
        super(error.name());
        this.error = error;
    }

}
