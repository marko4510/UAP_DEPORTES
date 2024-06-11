!(function (i) {
    "use strict";

    function e() {
    }

    (e.prototype.init = function () {
        if (i.isFunction(i.fn.fullCalendar)) {
            i("#external-events .fc-event").each(function () {
                var e = {title: i.trim(i(this).text())};
                i(this).data("eventObject", e), i(this).draggable({zIndex: 999, revert: !0, revertDuration: 0});
            });
            var e = new Date(),
                t = e.getDate(),
                a = e.getMonth(),
                n = e.getFullYear();

            let calendar = i("#calendar");
            calendar.fullCalendar({
                
                header: {left: "prev,next today", center: "title", right: "month,basicWeek,basicDay"},
                editable: !0,
                eventLimit: !0,
                droppable: !0,
                drop: function (e, t) {
                    var a = i(this).data("eventObject"),
                        n = i.extend({}, a);
                    (n.start = e), (n.allDay = t), i("#calendar").fullCalendar("renderEvent", n, !0), i("#drop-remove").is(":checked") && i(this).remove();
                },
                events: function (start, end, timezone, callback) {
                    loadEvents(calendar.fullCalendar('getDate').format('M'), callback);
                },
                eventClick: function (calEvent, jsEvent, view) {
                    // Rellenar el contenido del modal con los datos del evento
                    let evento = calEvent.extendedProps;
                    let fechaActividadFormatted = moment(evento.fecha_detalle_actividad).format('DD/MM/YYYY');
                    $('#modalEvento .modal-title').text(evento.id_actividad+" - "+evento.descripcion);
                    $('#modalEvento #lugarActividad').val(evento.nombre_lugar);
                    $('#modalEvento #fechaActividad').val(fechaActividadFormatted);
                    $('#modalEvento #horaInicio').val(evento.hora_inicio);
                    $('#modalEvento #horaFin').val(evento.hora_final);//id_detalle_actividad
                    $('#modalEvento #avanceActividad').val(evento.avance_actividad);//avance_actividad
                    $('#modalEvento #idDetalleActividad').val(evento.id_detalle_actividad);
                    $('#modalEvento #idLugar').val(evento.id_lugar);
                    $('#modalEvento #idSubDetAct').val(evento.id_sub_detalle_actividad);
                    
                    // Mostrar el modal
                    $('#modalEvento').modal('show');
                    actualizarSelect();
                },
                eventRender: function (event, element) {
                    // Eliminar la clase fc-time para ocultar la parte de la hora que era 4a
                    element.find('.fc-time').remove();
                },
                editable: false,//paralizando el movimiento de eventos a otro fecha en calendario
            })
            function loadEvents(mes, callback) {
                $.ajax({
                    url: '/api/eventos-especiales/' + mes,
                    success: function (subdetalleActividades) {
                        let eventos = [];
                        let lugaresOptions = '';
                        for (let subdetalle of subdetalleActividades) {
                            console.log(subdetalle);
                            let lugaresConcatenados = "";
                            let id_lugar;
                            let idsub = subdetalle.id_sub_detalle_actividad;
                            console.log(idsub);
                            let lugares = subdetalle.lugares;
            
                            for (let lugar of lugares) {
                                id_lugar = lugar.id_lugar;
                                lugaresConcatenados += " " + lugar.nombre_lugar; // Concatenate lugar names
                                console.log(lugar);
                            }
            
                            let id_actividad = subdetalle.detalleActividad.actividad.id_actividad;
                            console.log("id actividad " + id_actividad);
                            let nombre_lugar = lugaresConcatenados;
                            let descripcion = subdetalle.detalleActividad.actividad.descripcion_actividad;
                            let fecha_detalle_actividad = subdetalle.detalleActividad.fecha_detalle_actividad; // Obtener la fecha sin modificar
                            let id_detalle_actividad = subdetalle.detalleActividad.id_detalle_actividad;
                            let id_sub_detalle_actividad = subdetalle.id_sub_detalle_actividad;
            
                            let avance_actividad = subdetalle.detalleActividad.actividad.avance_actividad;
            
                            console.log("avance actividad " + avance_actividad);
            
                            console.log(id_detalle_actividad);
                            let fechaInicio = new Date(subdetalle.hora_inicio);
                            let hora1 = fechaInicio.getHours().toString().padStart(2, '0');
                            let minutos1 = fechaInicio.getMinutes().toString().padStart(2, '0');
                            let hora_inicio = hora1 + ':' + minutos1;
            
                            let fechaFinal = new Date(subdetalle.hora_final);
                            let hora2 = fechaFinal.getHours().toString().padStart(2, '0');
                            let minutos2 = fechaFinal.getMinutes().toString().padStart(2, '0');
                            let hora_final = hora2 + ':' + minutos2;
            
                            // Obtener el color del primer lugar (si hay múltiples lugares, podrías necesitar ajustar esto)
                            let color = lugares.length > 0 ? lugares[0].color_lugar : '#000000'; // Default color if none is provided
            
                            let evento = {
                                title: hora_inicio + ' - ' + hora_final,  //nombre_lugar,
                                start: fecha_detalle_actividad, // Utilizar la fecha directamente
                                color: color,
                                extendedProps: {
                                    id_detalle_actividad: id_detalle_actividad,
                                    id_sub_detalle_actividad: id_sub_detalle_actividad,
                                    id_lugar: id_lugar,
                                    id_actividad: id_actividad,
                                    nombre_lugar: nombre_lugar,
                                    descripcion: descripcion,
                                    fecha_detalle_actividad: fecha_detalle_actividad,
                                    hora_inicio: hora_inicio,
                                    hora_final: hora_final,
                                    avance_actividad: avance_actividad,
                                }
                            };
            
                            eventos.push(evento);
                        }
            
                        callback(eventos);
                    }
                });
            }
            
            
            function updateEvents() {
                calendar.fullCalendar('refetchEvents');
                calendar.fullCalendar('rerenderEvents');
                
            }
            function actualizarSelect() {
                var idLugar = document.getElementById("idLugar").value;
                var select = document.getElementById("ilugaresA");
        
                for (var i = 0; i < select.options.length; i++) {
                    var option = select.options[i];
                    if (option.value === idLugar) {
                        option.selected = true;
                    }
                }
            }
            function actualizarSelectAvance() {
                var avanceA = document.getElementById("avanceActividad");
                var select = ['solicitado','aceptado','concluido','cancelado'];
        
                for (var i = 0; i < select.options.length; i++) {
                    var option = select.options[i];
                    if (option.value === avanceA) {
                        option.selected = true;
                    }
                }
            }
            
            calendar.find('.fc-prev-button, .fc-next-button, .fc-today-button').on('click', function () {
                updateEvents();
            });

            calendar.find('.fc-month-button, .fc-agendaWeek-button, .fc-agendaDay-button').on('click', function () {
                updateEvents();
            });

        } else alert("Calendar plugin is not installed");
    }),
        (i.CalendarPage = new e()),
        (i.CalendarPage.Constructor = e);
})(window.jQuery),
    (function () {
        "use strict";
        window.jQuery.CalendarPage.init();
    })();
