package com.adjudicat.exception;


import lombok.Getter;
import java.io.Serial;


@Getter
public abstract class AdjudicatBaseException extends Exception implements ExceptionDetailsMessageException {


    @Serial
    private static final long serialVersionUID = -4567456745L;


    protected final ExceptionDetails exceptionDetails;
    protected final String message;


    /**
     * Constructor principal que permet la creació flexible de l'excepció.
     * @param cause                 la causa de l'excepció (pot ser null).
     * @param exceptionDetails      els detalls de l'excepció (pot ser null).
     * @param message               el missatge de l'excepció (pot ser null).
     */
    protected AdjudicatBaseException(Throwable cause, ExceptionDetails exceptionDetails, String message) {
        super(cause);
        this.exceptionDetails = exceptionDetails;
        this.message = message;
    }


    @Override
    public ExceptionDetails getExceptionDetails() {
        return this.exceptionDetails;
    }


    @Override
    public String getMessage() {
        return this.message;
    }
}