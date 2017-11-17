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
    isr: '0.00'
  }
})


//Formulario
var app = new Vue({
  el: '#form-cetes',
  data: {

  },
  methods: {
    calcular: function (event) {
      $('#div-result').show();

      var monto = $('#monto').val();
      var plazo = $('#plazo').val();
      var url = '/calculadora/cetes?monto=' + monto + '&plazo=' + plazo

      //Peticion ajax para obtener los calculos
      $.get(url, function(data, status){
        console.log(data);
        result.inversion = data.data.montoInvertir
        result.rend_brutos = data.data.intBrutosCetes
        result.rend_brutos = data.data.montoRealBonddia
        result.compro_bonddia = data.data.montoRealBonddia
        result.rend_brutos_bond = data.data.intBrutosBonddia
        //result.total_invertido = data.data.montoRealBonddia
        result.total_rendimiento = data.data.totalRendimiento
        result.remanente = data.data.remanente
        result.isr = data.data.isr  
      });
    }
  }
})
