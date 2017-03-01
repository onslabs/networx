package onslabs.kit.networx;

import java.io.IOException;

public class RetroError {

    private final int httpErrorCode;
    private String errorMessage;
    private final Kind kind;

    public RetroError(Kind kind, String errorMessage, int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
        this.kind = kind;
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Kind getKind() {
        return kind;
    }

    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}