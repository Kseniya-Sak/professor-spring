package spring.service;

import org.springframework.stereotype.Component;
import spring.exception.RepositoryException;

import java.util.Objects;

@Component
public class ParameterHandler {

    private ParameterHandler() {}

    public static void throwExceptionIfParameterNull(Object parameter, String message) {
        if (Objects.isNull(parameter)) {
            throw new RepositoryException(message);
        }
    }

    public static void throwExceptionIfParameterNull(int parameter, String message) {
        if (parameter <= 0) {
            throw new RepositoryException(message);
        }
    }
}
