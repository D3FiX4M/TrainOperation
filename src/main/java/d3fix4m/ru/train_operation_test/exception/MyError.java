package d3fix4m.ru.train_operation_test.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MyError {
    ALREADY_EXIST(
            1L,
            HttpStatus.BAD_REQUEST
    ),
    FORBIDDEN(
            2L,
            HttpStatus.BAD_REQUEST
    ),
    EXCEEDED_WEIGHT(3L,
            HttpStatus.BAD_REQUEST
    ),
    WAGON_ALREADY_USED(
            4L,
            HttpStatus.BAD_REQUEST
    ),
    PATH_DOES_NOT_BELONG_TO_THE_STATION(
            5L,
            HttpStatus.BAD_REQUEST
    ),
    USER_NOT_FOUND(
            6L,
            HttpStatus.NOT_FOUND
    ),
    USER_ALREADY_EXIST(
            7L,
            HttpStatus.BAD_REQUEST
    ),
    BAD_USERNAME_OR_PASSWORD(
            8L,
            HttpStatus.BAD_REQUEST
    ),
    NOT_ENOUGH_WAGONS(
            9L,
            HttpStatus.BAD_REQUEST
    ),
    WAGON_IS_ALREADY_ON_STATION_PATH(
            10L,
            HttpStatus.BAD_REQUEST
    ),
    WAGON_IS_NOT_AT_THE_STATION(
            11L,
            HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND_WITH_ID(
            12L,
            HttpStatus.BAD_REQUEST
    ),
    CARGO_NOT_FOUND_WITH_ID(
            13L,
            HttpStatus.BAD_REQUEST
    ),
    WAGON_NOT_FOUND_WITH_ID(
            14L,
            HttpStatus.BAD_REQUEST
    ),
    PLACED_WAGON_NOT_FOUND_WITH_ID(
            15L,
            HttpStatus.BAD_REQUEST
    ),
    STATION_NOT_FOUND_WITH_ID(
            16L,
            HttpStatus.BAD_REQUEST
    ),
    STATION_PATH_NOT_FOUND_WITH_ID(
            17L,
            HttpStatus.BAD_REQUEST
    ),
    ;
    private final Long code;
    private final HttpStatus status;
}
