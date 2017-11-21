package com.cetes.calculadora.calculadora.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Cetes {
    private double montoInvertir;
    private double montoRealCetes;
    private double intBrutosCetes;
    private double montoRealBonddia;
    private double intBrutosBonddia;
    private double tInvertido;
    private double tRendimiento;
    private double remanente;
    private double isr;
    private double totalRendimiento;
    private double totalFinal;
}
