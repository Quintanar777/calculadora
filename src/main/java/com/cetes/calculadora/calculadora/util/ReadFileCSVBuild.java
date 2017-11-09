package com.cetes.calculadora.calculadora.util;

import au.com.bytecode.opencsv.CSVReader;
import com.cetes.calculadora.calculadora.beans.Valores;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadFileCSVBuild {

    /**
     * Leer el archivo CSV de subastas
     * */
    public List readCSV(String urlFile){
        List<Valores> listaValores = new ArrayList<>();
        try {
            if(!urlFile.isEmpty()){
                CSVReader reader = new CSVReader(new FileReader(urlFile), ',', '\'', 1);
                String[] record = null;

                while ((record = reader.readNext()) != null) {
                    Valores valores = new Valores();

                    valores.setSerie(record[0] != null ? record[0] : "");
                    valores.setPlazo(record[1] != null ? Integer.valueOf(record[1]) : 0);
                    valores.setTasaDescuento(record[2] != null ? Double.valueOf(record[2]) : 0.0);
                    valores.setMontoSolicitado(record[3] != null ? Double.valueOf(record[3]) : 0.0);
                    valores.setMontoAsignado(record[4] != null ? Double.valueOf(record[4]) : 0.0);

                    //Calcular tasa de rendimiento
                    double tasaRendimiento = valores.getTasaDescuento() / (1 - valores.getTasaDescuento() * valores.getPlazo() / 36000);
                    DecimalFormat df = new DecimalFormat("#.00");
                    valores.setTasaRendimiento(Double.valueOf(df.format(tasaRendimiento).replace(",", ".")));
                    //Calcular precio
                    double precio;
                    if(valores.getMontoAsignado() == 0){
                        precio = 0;
                    }else{
                        precio = ((valores.getMontoSolicitado() / (1 + (valores.getTasaRendimiento() / 100 * valores.getPlazo()) / 360)) / valores.getMontoSolicitado()) * 10;
                    }
                    df = new DecimalFormat("#.0000000");
                    valores.setPrecio(Double.valueOf(df.format(precio).replace(",", ".")));

                    listaValores.add(valores);
                }
            }

        } catch (java.io.IOException e) {

        }
        return listaValores;
    }
}
