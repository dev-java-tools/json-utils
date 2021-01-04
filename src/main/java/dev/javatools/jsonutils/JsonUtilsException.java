package dev.javatools.jsonutils;

public class JsonUtilsException extends RuntimeException {
    public JsonUtilsException(Throwable throwable) {
        super(throwable);
    }

    public JsonUtilsException(String message) {
        super(message);
    }
}
