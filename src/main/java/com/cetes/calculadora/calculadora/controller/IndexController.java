package com.cetes.calculadora.calculadora.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(){
        LOGGER.info("init - path: / method: GET");
        return "cetes/main_cetes";
    }

    /**
     * get cetes/calc_cetes.html
     * */
    @RequestMapping("/calc_cetes")
    public String getCalcCetes(){
        return "cetes/calc_cetes";
    }

    /**
     * get cetes/comp_cetes.html
     * */
    @RequestMapping("/comp_cetes")
    public String getCalcComparar(){
        return "cetes/comp_cetes";
    }
}
