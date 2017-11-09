package com.cetes.calculadora.calculadora.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilCommon {

    /**
     * Redondear un Double a n decimales
     * */
    public static Double roundDouble(Double val, int decimales){
        BigDecimal bd = new BigDecimal(val);
        bd = bd.setScale(decimales, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
