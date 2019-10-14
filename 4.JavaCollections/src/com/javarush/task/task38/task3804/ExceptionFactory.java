package com.javarush.task.task38.task3804;

import static com.javarush.task.task38.task3804.ApplicationExceptionMessage.SOCKET_IS_CLOSED;

public class ExceptionFactory {

    public static Throwable getException(Enum type) {
        if (ApplicationExceptionMessage.SOCKET_IS_CLOSED.equals(type)) {
            return new Exception("Socket is closed");
        }
        if (ApplicationExceptionMessage.UNHANDLED_EXCEPTION.equals(type)) {
            return new Exception("Unhandled exception");
        }
        if(DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT.equals(type)){
            return new RuntimeException("No result due to timeout");
        }
        if(DatabaseExceptionMessage.NOT_ENOUGH_CONNECTIONS.equals(type)){
            return new RuntimeException("Not enough connections");
        }
        if(UserExceptionMessage.USER_DOES_NOT_EXIST.equals(type)){
            return new Error("User does not exist");
        }
        if(UserExceptionMessage.USER_DOES_NOT_HAVE_PERMISSIONS.equals(type)){
            return new Error("User does not have permissions");
        }

        return new IllegalArgumentException();
    }
}
