package com.adjudicat.exception;


public class RepositoryException extends AdjudicatBaseException{
    public RepositoryException(Throwable cause, String errorCode) {
        super(cause, new ExceptionDetails(errorCode, null), cause.getMessage());
    }
}