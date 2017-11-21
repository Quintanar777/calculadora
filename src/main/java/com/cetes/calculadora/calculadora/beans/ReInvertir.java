package com.cetes.calculadora.calculadora.beans;

import lombok.Data;

import java.util.List;

@Data
public class ReInvertir {
    private List<Rendimientos> listRendimientos;
    private List<String> namesPeriodos;
    private Cetes cetes;
}
