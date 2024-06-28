package com.uap.planificacion.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.DetalleActividad;
import com.uap.planificacion.model.entity.EstadoActividad;
import com.uap.planificacion.model.entity.Evento;
import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.Programacion;
import com.uap.planificacion.model.entity.SubDetalleActividad;
import com.uap.planificacion.model.entity.TipoActividad;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.IDetalleActividadService;
import com.uap.planificacion.model.service.IEstadoActividadService;
import com.uap.planificacion.model.service.IEventoService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.IProgramacionService;
import com.uap.planificacion.model.service.ISubDetalleActividadService;
import com.uap.planificacion.model.service.ITipoActividadService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

@Controller
public class publicoController {
    @Autowired
    IUnidadFuncionalService unidadService;

    @Autowired
    IEventoService eventoService;

    @Autowired
    IDetalleActividadService detalleActividadService;

    @Autowired
    ISubDetalleActividadService subDetalleActividadService;

    @Autowired
    ILugarService lugarService;

    @Autowired
    IEventoDao eventoDao;

    @Autowired
    IActividadService actividadService;

    @Autowired
    private ITipoActividadService tipoActividadService;

    @Autowired
    private IProgramacionService programacionService;

    @Autowired
    private IEstadoActividadService estadoActividadService;

    @Autowired
    private IEventoService eventoServive;

    @GetMapping("/reservas")
    public String vistaCalendario(Model model, HttpServletRequest request) {

        String lugar1 = "";
        String lugar2 = "";
        String lugar3 = "";
        String lugar4 = "";
        // String lugar5="";
        // String lugar6="";
        // String lugar7="";
        List<Evento> events = new ArrayList<>();
        for (Evento evento : eventoService.listaEventosSolicitados()) {

            events.add(evento);
            lugar1 = "CANCHA LA GUARIDA DEL JAGUAR";
            lugar2 = "COLISEO CERRADO";
            lugar3 = "COLISEO POLIFUNCIONAL";
            lugar4 = "CANCHA DEL FRONTON CAMPUS";

        }
        model.addAttribute("lugar1", lugar1);
        model.addAttribute("lugar2", lugar2);
        model.addAttribute("lugar3", lugar3);
        model.addAttribute("lugar4", lugar4);
        // model.addAttribute("lugar5", lugar5);
        // model.addAttribute("lugar6", lugar6);
        // model.addAttribute("lugar7", lugar7);

        model.addAttribute("actividad", new Actividad());
        model.addAttribute("programacion", new Programacion());
        model.addAttribute("listaProgramaciones", programacionService.findAllOrderFechaDesc());
        model.addAttribute("tipoActividades", tipoActividadService.findAll());
        // model.addAttribute("afluencias", afluenciaService.findAll());
        model.addAttribute("unidadFuncionales", unidadService.findAll());

        model.addAttribute("lugares", lugarService.findAll());
        model.addAttribute("lugaresE", lugarService.sacarLugaresConTipoE("E"));
        model.addAttribute("eventosSolicitados", events);

        return "publico/calendario";

    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private LocalTime convertToLocalTime(Date date) {
        return new java.sql.Time(date.getTime()).toLocalTime();
    }

    @PostMapping(value = "/postPreReserva")
    public String guardarPreReserva(@Validated Actividad actividad,
            @RequestParam(value = "fechasA") @DateTimeFormat(pattern = "yyyy-MM-dd") Date[] fechas,
            @RequestParam(value = "horasI") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_inicio,
            @RequestParam(value = "horasF") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_fin,
            @RequestParam(value = "lugaresA") List<Long> id_lugares,
            @RequestParam(value = "responsable") String responsable,
            @RequestParam(value = "celular") String celular,
            @RequestParam(value = "motivo") String motivo,
            RedirectAttributes redirectAttrs,
            HttpServletRequest request) throws ParseException {

        UnidadFuncional unidadFuncional = unidadService.buscarResponsable(responsable);
        if (unidadFuncional == null) {
            unidadFuncional = new UnidadFuncional(); // Inicializar la unidadFuncional
            unidadFuncional.setEstado("A");
            unidadFuncional.setNom_unidad(responsable);
            unidadFuncional.setNom_telefono(celular);
            unidadService.save(unidadFuncional);
        }

        for (int i = 0; i < fechas.length; i++) {
            LocalDate fechaReserva = convertToLocalDate(fechas[i]);
            LocalTime horaInicio = convertToLocalTime(hora_inicio.get(i));
            LocalTime horaFin = convertToLocalTime(hora_fin.get(i));
            LocalTime horaFinMenosUnSegundo = horaFin.minusSeconds(1);
            Long idLugar = id_lugares.get(i);
            Lugar lugar = lugarService.findOne(idLugar);

            Object resultado = subDetalleActividadService.validarHoraReservasPublicas(fechaReserva, horaInicio,
                    horaFinMenosUnSegundo, lugar.getNombre_lugar());
            // System.out.println("FechaReserva: "+ fechaReserva);
            // System.out.println("horaInicio: "+ horaInicio);
            // System.out.println("horaFin: "+ horaFin);
            // System.out.println("lugar: "+ lugar.getNombre_lugar());
            if (resultado != null) {
                redirectAttrs
                        .addFlashAttribute("mensaje",
                                "La fecha y hora que intenta registrar ya se encuentra reservada en "
                                        + lugar.getNombre_lugar())
                        .addFlashAttribute("clase", "danger alert-dismissible fade show");
                return "redirect:/reservas";
            }

        }

        String usuarioRegistro = request.getParameter("usuarioRegistro");

        EstadoActividad ea = estadoActividadService.findOne((long) 1);
        actividad.setUnidadFuncional(unidadFuncional);
        actividad.setEstado("S");
        actividad.setAvance_actividad("solicitado");
        actividad.setProgramdo(true);
        actividad.setEstadoActividad(ea);
        actividad.setFecha_registro(new Date());
        actividad.setMotivo(motivo);
        actividad.setUsuarioRegistro(usuarioRegistro);
        actividadService.save(actividad);
        List<Lugar> lugares = new ArrayList<>();
        for (Long long1 : id_lugares) {
            Lugar lugar = lugarService.findOne(long1);
            lugares.add(lugar);
        }
        List<Lugar> lugaresParaEvento = new ArrayList<>();
        for (int i = 0; i < lugares.size(); i++) {

            DetalleActividad detalleActividad = new DetalleActividad();
            detalleActividad.setFecha_detalle_actividad(fechas[i]);
            detalleActividad.setActividad(actividad);

            detalleActividad.setFecha_registro(new Date());
            detalleActividad.setEstado("S");
            detalleActividadService.save(detalleActividad);

            Lugar lugar = lugarService.findOne(lugares.get(i).getId_lugar());
            Set<Lugar> lugarSet = new HashSet<>();
            lugarSet.add(lugar);
            SubDetalleActividad subDetalleActividad = new SubDetalleActividad();
            subDetalleActividad.setHora_inicio(hora_inicio.get(i));
            LocalTime horaFin = convertToLocalTime(hora_fin.get(i));
            LocalTime horaFinMenosUnSegundo = horaFin.minusSeconds(1);// 9:08
            LocalDate fechaActual = LocalDate.now();// 5/5/5 11:02
            LocalDateTime localDateTime = LocalDateTime.of(fechaActual, horaFinMenosUnSegundo);
            Date date = java.sql.Timestamp.valueOf(localDateTime);
            subDetalleActividad.setHora_final(date);
            subDetalleActividad.setDetalleActividad(detalleActividad);
            subDetalleActividad.setLugares(lugarSet);
            subDetalleActividad.setEstado("S");
            subDetalleActividadService.save(subDetalleActividad);
            if (lugar.getTipo_lugar().equals("E")) {
                lugaresParaEvento.add(lugar);
            }
        }
        // Set<Long> id_lugaress = new HashSet<>();
        // for (Lugar lugar2 : lugaresParaEvento) {
        // long id_lugar = lugar2.getId_lugar();
        // if (!id_lugaress.contains(id_lugar)) {
        // id_lugaress.add(id_lugar);
        // }
        // }
        Set<Long> id_lugaress = new HashSet<>();
        for (Lugar lugar2 : lugaresParaEvento) {
            id_lugaress.add(lugar2.getId_lugar());
        }
        for (Long id_lugar : id_lugaress) {
            System.out.println("ID del lugar único: " + id_lugar);
            Lugar ll = lugarService.findOne(id_lugar);
            Evento evento = new Evento();
            evento.setLugar(ll);
            evento.setActividad(actividad);
            evento.setEstado_evento("S");
            eventoServive.save(evento);
        }
        redirectAttrs
                .addFlashAttribute("mensaje",
                        "Se Registro la Pre Reserva satisfactoriamente!")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/reservas";// "redirect:/detalleActividad/" + actividad.getId_actividad();

    }

}
