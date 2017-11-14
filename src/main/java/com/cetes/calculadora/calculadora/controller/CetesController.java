package com.cetes.calculadora.calculadora.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class CetesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CetesController.class);

    /**
     * Load cetes.html
     * */
    @RequestMapping("/cetes")
    public String cetes(){
        LOGGER.info("init - path: /cetes method: GET");
        return "cetes";
    }
}
