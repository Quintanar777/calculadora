package com.cetes.calculadora.calculadora.beans;

import lombok.Data;

@Data
public class Valores {
    private String serie;
    private int plazo;
    private double tasaDescuento;
    private double montoSolicitado;
    private double montoAsignado;
    private double tasaRendimiento;
    private double precio;
}
