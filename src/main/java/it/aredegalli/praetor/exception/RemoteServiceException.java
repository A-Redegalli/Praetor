package it.aredegalli.praetor.exception;

public class RemoteServiceException extends RuntimeException {
    private final String code;

    public RemoteServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
