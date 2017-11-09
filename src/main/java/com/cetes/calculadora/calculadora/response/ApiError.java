package com.cetes.calculadora.calculadora.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    private int error_code;
    private String description;
}
