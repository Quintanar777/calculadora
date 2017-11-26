package com.cetes.calculadora.calculadora.controller;

import com.cetes.calculadora.calculadora.beans.Cetes;
import com.cetes.calculadora.calculadora.beans.ReInvertir;
import com.cetes.calculadora.calculadora.beans.Rendimientos;
import com.cetes.calculadora.calculadora.business.CalculadoraDelegate;
import com.cetes.calculadora.calculadora.commons.Status;
import com.cetes.calculadora.calculadora.response.ApiError;
import com.cetes.calculadora.calculadora.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cetes")
public class CetesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CetesController.class);
    @Autowired
    private CalculadoraDelegate calculadoraDelegate;

    /**
     * Rest GET para el calculo de CETES
     */
    @RequestMapping(value = "invertir", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse cetes(
            @RequestParam(value = "plazo", required = true) Integer plazo,
            @RequestParam(value = "monto", required = true) Double monto
    ) {
        LOGGER.info("init - path: calculadora/cetes method: GET");
        LOGGER.debug("plazo: " + plazo);
        LOGGER.debug("monto: " + monto);

        try {
            return new ApiResponse(Status.OK, calculadoraDelegate.getCalcCetes(plazo, monto));
        } catch (Exception e) {
            LOGGER.error("error - Ocurrio un error: " + e.getMessage());
            return new ApiResponse(Status.ERROR,
                    new ApiError(500, "Ocurrio un error al realizar el calculo de cetes")
            );
        }
    }

    /**
     * Rest GET para obtener los calculos de re-invertir
     */
    @RequestMapping(value = "re_invertir", method = RequestMethod.GET)
    public ApiResponse reInvertir(
            @RequestParam(value = "periodo", required = true) Integer periodo,
            @RequestParam(value = "montoInicial", required = true) Double montoInicial
    ) {
        LOGGER.info("init - path: /re_invertir method: GET");
        LOGGER.debug("periodo: " + periodo);
        LOGGER.debug("montoInicial: " + montoInicial);

        try {
            ReInvertir reInvertir = new ReInvertir();
            Cetes cetes = new Cetes();
            List<Rendimientos> listRendimientos = new ArrayList<>();
            List<Double> dataCetes = new ArrayList<>();
            List<Double> dataBonddia = new ArrayList<>();

            List<String> namesPeriodos = new ArrayList<>();

            //Se obtiene los rendimientos de los n periodos
            Rendimientos rendimientosCetes = new Rendimientos();
            Rendimientos rendimientosBonddia = new Rendimientos();

            rendimientosCetes.setName("Remanente Cetes");
            rendimientosBonddia.setName("Remanente Bonddia");

            for (int i = 1; i <= periodo; i++) {

                namesPeriodos.add("Periodo " + i);

                if (i == 1) {
                    cetes = calculadoraDelegate.getCalcCetes(28, montoInicial);
                } else {
                    cetes = calculadoraDelegate.getCalcCetes(28, cetes.getTotalFinal());
                }
                dataCetes.add(cetes.getIntBrutosCetes());
                dataBonddia.add(cetes.getIntBrutosBonddia());
            }

            rendimientosCetes.setData(dataCetes);
            rendimientosBonddia.setData(dataBonddia);

            listRendimientos.add(rendimientosCetes);
            listRendimientos.add(rendimientosBonddia);

            reInvertir.setListRendimientos(listRendimientos);
            reInvertir.setNamesPeriodos(namesPeriodos);

            reInvertir.setCetes(cetes);
            return new ApiResponse(Status.OK, reInvertir);
        } catch (Exception e) {
            LOGGER.error("error - Ocurrio un error: " + e.getMessage());
            return new ApiResponse(Status.ERROR,
                    new ApiError(500, "Ocurrio un error al realizar el calculo de re invertir")
            );
        }
    }

    /**
     * RM para obtener los calculos para comparar
     * */
    @RequestMapping(value = "comparar", method = RequestMethod.GET)
    public ApiResponse compararCetes(@RequestParam(value = "monto") Double monto){
        LOGGER.info("init - path: /comparar method: GET");
        LOGGER.debug("monto: " + monto);

        try{
            Map<String, Object> responseComp = new HashMap<>();
            //28 dias
            responseComp.put("comp28", calculadoraDelegate.getCalcCetes(28, monto));
            //91 dias
            responseComp.put("comp91", calculadoraDelegate.getCalcCetes(91, monto));
            //182 dias
            responseComp.put("comp182", calculadoraDelegate.getCalcCetes(182, monto));
            //364 dias
            responseComp.put("comp364", calculadoraDelegate.getCalcCetes(364, monto));

            return new ApiResponse(Status.OK, responseComp);
        }catch (Exception e){
            LOGGER.error("error - Ocurrio un error: " + e.getMessage());
            return new ApiResponse(Status.ERROR,
                    new ApiError(500, "Ocurrio un error al realizar la comparacion por plazos")
            );
        }
    }
}
