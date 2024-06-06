$(document).ready(function() {
    var calendarContainer = $('#calendar-container');
    var calendarUrl = calendarContainer.data('url');
    
    // Función para actualizar el calendario en tiempo real
    function updateCalendar() {
        $.ajax({
            url: calendarUrl,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                // Genera el HTML del calendario utilizando los datos obtenidos
                var calendarHtml = generateCalendarHtml(data);
                
                // Actualiza el contenido del contenedor del calendario
                calendarContainer.html(calendarHtml);
            },
            error: function(xhr, status, error) {
                console.error('Error al obtener los datos del calendario: ' + error);
            }
        });
    }
    
    // Llama a la función de actualización del calendario inicialmente
    updateCalendar();
    
    // Establece un intervalo para actualizar el calendario cada cierto tiempo (por ejemplo, cada 5 segundos)
    setInterval(updateCalendar, 5000);
    
    // Función para generar el HTML del calendario a partir de los datos obtenidos
    function generateCalendarHtml(data) {
        // Lógica para generar el HTML del calendario utilizando los datos obtenidos
        
        // Devuelve el HTML generado
        var calendarHtml = '';
        // ... Lógica para generar el HTML del calendario en tiempo real
        
        return calendarHtml;
    }
});