$(document).ready(function () {
  $('#checkbox').on('change', function () {
    var tipoLugar = $(this).is(':checked') ? 'E' : 'N'; // Determina el tipo de lugar según el estado del checkbox

    // Realiza la solicitud AJAX para obtener los lugares
    $.ajax({
      url: '/lugares',  // Reemplaza con tu URL correcta
      method: 'GET',
      data: { tipo_lugar: tipoLugar },  // Parámetro de la solicitud
      success: function (response) {
        // Limpiar el select
        $('#select').empty();
        
        // Agregar las opciones al select con estilo de color
        for (var i = 0; i < response.length; i++) {
          var lugar = response[i];
          var option = $('<option></option>').attr('value', lugar.id_lugar).text(lugar.nombre_lugar);

          // Aplicar estilo de color si tipo_lugar es 'E'
          if (lugar.tipo_lugar === 'E') {
            option.attr('style', 'color: #0f890d;');
          }
          if (lugar.tipo_lugar === 'N') {
            option.attr('style', 'color: #0630d6;');
          }

          $('#select').append(option);
        }
      },
      error: function () {
        console.error('Error al obtener los lugares');
      }
    });
  });
});
/*$(document).ready(function () {
  $('#checkbox').on('change', function () {
    var tipoLugar = $(this).is(':checked') ? 'E' : 'N'; // Determina el tipo de lugar según el estado del checkbox

    // Realiza la solicitud AJAX para obtener los lugares
    $.ajax({
      url: '/lugares',  // Reemplaza con tu URL correcta
      method: 'GET',
      data: { tipo_lugar: tipoLugar },  // Parámetro de la solicitud
      success: function (response) {
        // Limpiar el select
        $('#select').empty();
        
        // Agregar las opciones al select
        for (var i = 0; i < response.length; i++) {
          var lugar = response[i];
          $('#select').append('<option value="' + lugar.id_lugar + '">' + lugar.nombre_lugar + ' </option>');
        }
      },
      error: function () {
        console.error('Error al obtener los lugares');
      }
    });
  });
});*/
/*$(document).ready(function () {
  // Agregar un evento al checkbox para escuchar los cambios
  $('#checkbox').on('change', function () {
    if ($(this).is(':checked')) {
      // Hacer la solicitud AJAX para obtener los lugares con tipo_lugar E
      $.ajax({
        url: '/lugares',  // URL de la API o del controlador que devuelve los lugares
        method: 'GET',
        data: { tipo_lugar: 'E' },  // Parámetros de la solicitud
        success: function (response) {
          // Limpiar el select
          $('#select').empty();
          // Agregar las opciones al select
          for (var i = 0; i < response.length; i++) {
            var lugar = response[i];
            $('#select').append('<option value="' + lugar.id_lugar + '">' + lugar.nombre_lugar + '</option>');
          }
        },
        error: function () {
          console.error('Error al obtener los lugares');
        }
      });
    } else {
      // Hacer la solicitud AJAX para obtener los lugares con tipo_lugar N
      $.ajax({
        url: '/lugares',  // URL de la API o del controlador que devuelve los lugares
        method: 'GET',
        data: { tipo_lugar: 'N' },  // Parámetros de la solicitud
        success: function (response) {
          // Limpiar el select
          $('#select').empty();
          // Agregar las opciones al select

          for (var i = 0; i < response.length; i++) {
            var lugar = response[i];
            $('#select').append('<option value="' + lugar.id_lugar + '">' + lugar.nombre_lugar + '</option>');
          }
        },
        error: function () {
          console.error('Error al obtener los lugares');
        }
      });
    }
  });
});*/



