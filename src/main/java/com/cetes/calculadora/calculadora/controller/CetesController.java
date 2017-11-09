package com.cetes.calculadora.calculadora.controller;

import com.cetes.calculadora.calculadora.util.ReadFileCSVBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("calculadora")
public class CetesController {

    @Autowired
    private ReadFileCSVBuild readFileCSVBuild;
    /**
     * Leer archivo CVS
     * */
    @RequestMapping("cetes28")
    @ResponseBody
    public Object getValoresVCS(){
        return readFileCSVBuild.readCSV("/opt/calc_cetes/docs/cetes_28.csv");
    }


    /**
     * Realizar el calculo de cetes
     * */

}
