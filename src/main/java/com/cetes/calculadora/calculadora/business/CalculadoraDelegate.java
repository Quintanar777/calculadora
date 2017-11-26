package com.cetes.calculadora.calculadora.business;

import com.cetes.calculadora.calculadora.beans.Cetes;
import com.cetes.calculadora.calculadora.util.UtilCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculadoraDelegate.class);

    @Value("${calc.cetes.precio}")
    private Double valorCetes;
    @Value("${calc.bonddia.precio}")
    private Double valorBonddia;

    @Value("${calc.cetes.tasa}")
    private Double tasaCetes;
    @Value("${calc.bonddia.tasa}")
    private Double tasaBonddia;


    /**
     * Método para realizar el calculo de CETES
     * */
    public Cetes getCalcCetes(int plazo, double montoInvertir){
        LOGGER.info("exec getCalcCetes()");
        Cetes cetes = new Cetes();

        cetes.setMontoInvertir(UtilCommon.roundDouble(montoInvertir,2));

        double tasaCetesAux = UtilCommon.roundDouble(tasaCetes/100,7);
        double tasaBonddiaAux = UtilCommon.roundDouble(tasaBonddia/100,7);

        double precioXcete = UtilCommon.roundDouble(valorCetes/(1+(plazo*tasaCetesAux)/360),7);

        double titulos = UtilCommon.roundDouble(montoInvertir/precioXcete,7);

        long iPart = (long) titulos;
        double fPart = titulos - iPart;

        double montoRealCetes = UtilCommon.roundDouble(iPart*precioXcete,7);
        cetes.setMontoRealCetes(UtilCommon.roundDouble(montoRealCetes, 2));

        double remanente = UtilCommon.roundDouble((montoInvertir - (iPart*precioXcete)),7);
        double intBrutosCetes = UtilCommon.roundDouble(((tasaCetesAux*100)/36000)*plazo*montoRealCetes,7);

        cetes.setIntBrutosCetes(UtilCommon.roundDouble(intBrutosCetes, 2));

        double titulosBondia = UtilCommon.roundDouble(remanente/valorBonddia,7);
        long tBondia = (long) titulosBondia;
        double fBondia = UtilCommon.roundDouble(titulosBondia - tBondia,7);

        double montoRealBonddia = UtilCommon.roundDouble((tBondia * valorBonddia), 7);
        cetes.setMontoRealBonddia(UtilCommon.roundDouble(montoRealBonddia,2));

        double remanenteBonddia = UtilCommon.roundDouble((remanente - montoRealBonddia),7);
        double intBrutosBonddia = UtilCommon.roundDouble((tasaBonddiaAux*100/36000)*plazo*montoRealBonddia,7);
        cetes.setIntBrutosBonddia(UtilCommon.roundDouble(intBrutosBonddia, 2));

        double tInvertido = UtilCommon.roundDouble((montoRealCetes+montoRealBonddia),2);
        double tRendimiento = UtilCommon.roundDouble((intBrutosCetes+intBrutosBonddia),2);
        cetes.setTInvertido(UtilCommon.roundDouble(tInvertido,2));
        cetes.setTRendimiento(UtilCommon.roundDouble(tRendimiento,2));

        double remanenteFinal = UtilCommon.roundDouble((montoInvertir - tInvertido),2);
        cetes.setRemanente(remanenteFinal);

        double ISR = UtilCommon.roundDouble((.1*tRendimiento),2);
        cetes.setIsr(ISR);

        double totalRendimiento = UtilCommon.roundDouble((tInvertido+tRendimiento-ISR),2);
        cetes.setTotalRendimiento(totalRendimiento);

        double totalFinal = UtilCommon.roundDouble((tInvertido+tRendimiento-ISR),2);
        cetes.setTotalFinal(totalFinal);

        return cetes;
    }
}
