package com.cetes.calculadora.calculadora.controller;

import com.cetes.calculadora.calculadora.business.CalculadoraDelegate;
import com.cetes.calculadora.calculadora.commons.Status;
import com.cetes.calculadora.calculadora.response.ApiError;
import com.cetes.calculadora.calculadora.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("calculadora")
public class CalculadorasController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculadorasController.class);
    @Autowired
    private CalculadoraDelegate calculadoraDelegate;

    /**
     * Rest GET para el calculo de CETES
     * */
    @RequestMapping(value = "cetes", method = RequestMethod.GET)
    @ResponseBody
    public Object cetes(
            @RequestParam(value = "plazo", required = true) Integer plazo,
            @RequestParam(value = "monto", required = true) Double monto
    ){
        LOGGER.info("init - path: calculadora/cetes method: GET");
        LOGGER.debug("plazo: " + plazo);
        LOGGER.debug("monto: " + monto);

        try{
            return new ApiResponse(Status.OK, calculadoraDelegate.getCalcCetes(plazo, monto));
        }catch (Exception e){
            LOGGER.error("error - Ocurrio un error: " + e.getMessage());
            return new ApiResponse(Status.ERROR,
                    new ApiError(500, "Ocurrio un error al realizar el calculo de cetes")
            );
        }
    }
}
