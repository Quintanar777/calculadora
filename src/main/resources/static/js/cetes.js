var today = new Date();

var day = today.getDate();
var month = today.getMonth();
var year = today.getFullYear();

//Formulario
var app = new Vue({
  el: '#form-cetes',
  data: {
    fechaActual: day + '/' + month + '/' + year,
    checked: false
  },
  methods: {
    //evento click para el boton Calcular
    calcular: function (event) {
      //mostrar en el div de contenido, el html de calc_cetes.html
      $('#content').load('/calculadoras/calc_cetes', function () {
        $('#div-result').show();
        $('#div-chart').show();
        var monto = $('#monto').val();
        var plazo = $('#plazo').val();

        if(this.checked){ //calculo a n peridos
          //calcular peridos
          var slider = document.getElementById("myRange");
          var periodos = Math.round(slider.value / 28);
          console.log(periodos);
          calcularPeriodos(periodos, monto);
        }else{ //calculo al plazo seleccionado
          calcularCetes(monto, plazo);
        }

      });

    },
    checkInvertir: function (event){
      //Mostrar reinvertir
      if(!this.checked){
        $('#div-reinvertir').show();
      }else{
        $('#div-reinvertir').hide();
      }
    },
    comparar: function (event) {
      $('#content').load('/calculadoras/comp_cetes', function () {
        var monto = $('#monto').val();

        var url = '/calculadoras/cetes/comparar?monto=' + monto

        //Peticion ajax para obtener los calculos
        $.get(url, function(data, status){
          compResult.inversion28 = data.data.comp28.montoInvertir
          compResult.compr_cetes28 = data.data.comp28.montoRealCetes
          compResult.rend_brutos28 = data.data.comp28.intBrutosCetes
          compResult.rend_brutos28 = data.data.comp28.montoRealBonddia
          compResult.compro_bonddia28 = data.data.comp28.montoRealBonddia
          compResult.rend_brutos_bond28 = data.data.comp28.intBrutosBonddia
          compResult.total_invertido28 = data.data.comp28.tinvertido
          compResult.total_rendimiento28 = data.data.comp28.totalRendimiento
          compResult.remanente28 = data.data.comp28.remanente
          compResult.isr28 = data.data.comp28.isr
          compResult.montoFinal28 = data.data.comp28.totalFinal

          compResult.inversion91 = data.data.comp91.montoInvertir
          compResult.compr_cetes91 = data.data.comp91.montoRealCetes
          compResult.rend_brutos91 = data.data.comp91.intBrutosCetes
          compResult.rend_brutos91 = data.data.comp91.montoRealBonddia
          compResult.compro_bonddia91 = data.data.comp91.montoRealBonddia
          compResult.rend_brutos_bond91 = data.data.comp91.intBrutosBonddia
          compResult.total_invertido91 = data.data.comp91.tinvertido
          compResult.total_rendimiento91 = data.data.comp91.totalRendimiento
          compResult.remanente91 = data.data.comp91.remanente
          compResult.isr91 = data.data.comp91.isr
          compResult.montoFinal91 = data.data.comp91.totalFinal

          compResult.inversion182 = data.data.comp182.montoInvertir
          compResult.compr_cetes182 = data.data.comp182.montoRealCetes
          compResult.rend_brutos182 = data.data.comp182.intBrutosCetes
          compResult.rend_brutos182 = data.data.comp182.montoRealBonddia
          compResult.compro_bonddia182 = data.data.comp182.montoRealBonddia
          compResult.rend_brutos_bond182 = data.data.comp182.intBrutosBonddia
          compResult.total_invertido182 = data.data.comp182.tinvertido
          compResult.total_rendimiento182 = data.data.comp182.totalRendimiento
          compResult.remanente182 = data.data.comp182.remanente
          compResult.isr182 = data.data.comp182.isr
          compResult.montoFinal182 = data.data.comp182.totalFinal

          compResult.inversion364 = data.data.comp364.montoInvertir
          compResult.compr_cetes364 = data.data.comp364.montoRealCetes
          compResult.rend_brutos364 = data.data.comp364.intBrutosCetes
          compResult.rend_brutos364 = data.data.comp364.montoRealBonddia
          compResult.compro_bonddia364 = data.data.comp364.montoRealBonddia
          compResult.rend_brutos_bond364 = data.data.comp364.intBrutosBonddia
          compResult.total_invertido364 = data.data.comp364.tinvertido
          compResult.total_rendimiento364 = data.data.comp364.totalRendimiento
          compResult.remanente364 = data.data.comp364.remanente
          compResult.isr364 = data.data.comp364.isr
          compResult.montoFinal364 = data.data.comp364.totalFinal
        });
      });
    }
  }
})

/**
* Funcion para obtener los calculos de n plazos
*/
function calcularPeriodos(periodo, montoInicial) {
  var url = '/calculadoras/cetes/re_invertir?periodo=' + periodo + '&montoInicial=' + montoInicial

  //Peticion ajax para obtener los calculos
  $.get(url, function(data, status){
    result.inversion = data.data.cetes.montoInvertir
    result.compr_cetes = data.data.cetes.montoRealCetes
    result.rend_brutos = data.data.cetes.intBrutosCetes
    result.rend_brutos = data.data.cetes.montoRealBonddia
    result.compro_bonddia = data.data.cetes.montoRealBonddia
    result.rend_brutos_bond = data.data.cetes.intBrutosBonddia
    result.total_invertido = data.data.cetes.tinvertido
    result.total_rendimiento = data.data.cetes.totalRendimiento
    result.remanente = data.data.cetes.remanente
    result.isr = data.data.cetes.isr
    result.montoFinal = data.data.cetes.totalFinal

    graficarPeriodos(data.data.listRendimientos, data.data.namesPeriodos);
  });

}

/**
* Funcion para realizar los calculos en base al monto y plazos
*/
function calcularCetes(monto, plazo){
  var url = '/calculadoras/cetes/invertir?monto=' + monto + '&plazo=' + plazo

  //Peticion ajax para obtener los calculos
  $.get(url, function(data, status){
    result.inversion = data.data.montoInvertir
    result.compr_cetes = data.data.montoRealCetes
    result.rend_brutos = data.data.intBrutosCetes
    result.compro_bonddia = data.data.montoRealBonddia
    result.rend_brutos_bond = data.data.intBrutosBonddia
    result.total_invertido = data.data.tinvertido
    result.total_rendimiento = data.data.trendimiento
    result.remanente = data.data.remanente
    result.isr = data.data.isr
    result.montoFinal = data.data.totalFinal

    graficar(data.data.intBrutosCetes, data.data.intBrutosBonddia);
  });
}

function graficarPeriodos(data, namesPeriodos) {
  console.log('data: ' + data);
  console.log('namesPeriodos: ' + namesPeriodos);

  //Cargar grafica
  var myChart = Highcharts.chart('chart-cetes', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'CETES'
    },
    xAxis: {
        categories: namesPeriodos
    },
    yAxis: {
        title: {
            text: 'Valores'
        }
    },
    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
            }
        }
    },
    series: data
  });
}

/**
* Función para generar la grafica
*/
function graficar(rend_cetes, rend_bonddia) {
  //Cargar grafica
  var myChart = Highcharts.chart('chart-cetes', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'CETES'
    },
    xAxis: {
        categories: ['Periodo 1']
    },
    yAxis: {
        title: {
            text: 'Valores'
        }
    },
    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
            }
        }
    },
    series: [
      {
        name: 'Remanente Bonddia',
        data: [rend_bonddia]
      },
      {
        name: 'Remanente Cetes',
        data: [rend_cetes]
      }
    ]
  });
}

//slider
var slider = document.getElementById("myRange");
var output = document.getElementById("dias-inversion");
output.innerHTML = slider.value;

slider.oninput = function() {
  output.innerHTML = this.value;
}
