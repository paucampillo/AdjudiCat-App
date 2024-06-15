package com.adjudicat.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class ExceptionDetails {
    private String errorCode;
    private Object[] errorMessageArguments;
}