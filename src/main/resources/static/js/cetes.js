//div de resultados
var result = new Vue({
  el: '#div-result',
  data: {
    inversion: '0.00',
    compr_cetes: '0.00',
    rend_brutos: '0.00',
    compro_bonddia: '0.00',
    rend_brutos_bond: '0.00',
    total_invertido: '0.00',
    total_rendimiento: '0.00',
    remanente: '0.00',
    isr: '0.00',
    montoFinal: '0.0'
  }
})
var today = new Date();

//Formulario
var app = new Vue({
  el: '#form-cetes',
  data: {
    fechaActual: today.toLocaleFormat('%d/%b/%Y'),
    checked: false
  },
  methods: {
    //evento click para el boton Calcular
    calcular: function (event) {
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


    },
    checkInvertir: function (event){
      //Mostrar reinvertir
      if(!this.checked){
        $('#div-reinvertir').show();
      }else{
        $('#div-reinvertir').hide();
      }
    }
  }
})

/**
* Funcion para obtener los calculos de n plazos
*/
function calcularPeriodos(periodo, montoInicial) {
  var url = '/calculadora/re_invertir?periodo=' + periodo + '&montoInicial=' + montoInicial

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
  var url = '/calculadora/cetes?monto=' + monto + '&plazo=' + plazo

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
