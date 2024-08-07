package com.uap.planificacion.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.entity.Actividad;

import com.uap.planificacion.model.entity.DetalleActividad;
import com.uap.planificacion.model.entity.EstadoActividad;

import com.uap.planificacion.model.entity.Evento;
import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.Programacion;
import com.uap.planificacion.model.entity.Responsable;
import com.uap.planificacion.model.entity.SubDetalleActividad;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.IAfluenciaService;

import com.uap.planificacion.model.service.IDetalleActividadService;
import com.uap.planificacion.model.service.IDireccionFuncionalService;
import com.uap.planificacion.model.service.IEstadoActividadService;

import com.uap.planificacion.model.service.IEventoService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.IPersonaService;
import com.uap.planificacion.model.service.IPersonalAdministrativoService;
import com.uap.planificacion.model.service.IProgramacionService;
import com.uap.planificacion.model.service.IResponsableService;
import com.uap.planificacion.model.service.ISubDetalleActividadService;
import com.uap.planificacion.model.service.ITipoActividadService;

import com.uap.planificacion.model.service.IUnidadFuncionalService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class actividadController {
    @Autowired
    private ITipoActividadService tipoActividadService;
    @Autowired
    private IAfluenciaService afluenciaService;
    @Autowired
    private IActividadService actividadService;
    @Autowired
    private IDetalleActividadService detalleActividadService;
    @Autowired
    private ISubDetalleActividadService subDetalleActividadService;
    @Autowired
    private ILugarService lugarService;
    @Autowired
    private IUnidadFuncionalService unidadService;
    @Autowired
    private IEstadoActividadService estadoActividadService;
  
    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;
    @Autowired
    private IProgramacionService programacionService;
    @Autowired
    private IPersonalAdministrativoService personalAdministrativoService;
    @Autowired
    private IEventoService eventoServive;
    @Autowired
    private IPersonaService personaService;
    @Autowired
    private IEventoDao eventoDao;

    @GetMapping(value = "/listaActividades")
    public String listaActividades(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("actividad", new Actividad());
     
            model.addAttribute("programacion", new Programacion());
            model.addAttribute("listaProgramaciones", programacionService.findAllOrderFechaDesc());
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());

            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            if (u.getNivelFuncional().getId_nivel_funcional() == 1) {
                model.addAttribute("listaActividadesMiUnidad", actividadService.findAll());
                model.addAttribute("nivel", u.getNivelFuncional());
            }
            if (u.getNivelFuncional().getId_nivel_funcional() == 2) {

                model.addAttribute("listaActividadesMiUnidad", actividadService
                        .listaActividadesPorDireccion(u.getDireccionFuncional().getId_direccion_funcional()));
                model.addAttribute("nivel", u.getNivelFuncional());
            }

            if (u.getNivelFuncional().getId_nivel_funcional() >= 3) {
                model.addAttribute("listaActividadesMiUnidad", actividadService.listaActividadesPorUnidad(
                        personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
                model.addAttribute("nivel", u.getNivelFuncional());
            }
            return "actividad/listaActividades";

        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/guardarActividadDinamica")
    public String guardarActividadDinamica(
            @RequestParam(value = "fechasA") @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> fecha_detalle_actividad,
            @RequestParam(value = "horasI") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_inicio,
            @RequestParam(value = "horasF") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_fin,
            @RequestParam(value = "costos") List<Integer> costos,
            @RequestParam(value = "lugaresA") List<Long> id_lugares) {
        int cont = 0;
        List<Lugar> lugares = new ArrayList<>();
        for (Long long1 : id_lugares) {
            Lugar lugar = lugarService.findOne(long1);
            lugares.add(lugar);
        }
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat h = new SimpleDateFormat("HH:mm:");
        for (Lugar lugar : lugares) {
            System.out.println(cont + " f: " + f.format(fecha_detalle_actividad.get(cont)) + " hI: "
                    + h.format(hora_inicio.get(cont)) + " hF: " + h.format(hora_fin.get(cont)) + " lugares: "
                    + lugar.getId_lugar() + " " + lugar.getNombre_lugar());
            cont++;
        }
        return "redirect:/actividadR";
    }

    @GetMapping(value = "/actividadR")
    public String actividad(Model model, HttpServletRequest request
   ) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("actividad", new Actividad());

            model.addAttribute("programacion", new Programacion());
            model.addAttribute("listaProgramaciones", programacionService.findAllOrderFechaDesc());
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());

            model.addAttribute("lugares", lugarService.findAll());
            // model.addAttribute("responsablesUnidad", unidadFuncional)

            UnidadFuncional un = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();

           
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal3 = Calendar.getInstance();
            cal3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal3.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cale = cal2;
            Calendar cale10 = cal3;

            // cale10.setTime(fechaLunes);
            cale.add(Calendar.DATE, +6); // domingo de la siguiente semana
            cale10.add(Calendar.DATE, -10);// 10 dias antes de de la fecha de registros de actividades
            Date fecha10diasatrasdelunes = cale10.getTime();
            Date fechaLunes = cal.getTime();
            Date fechaDomingo = cale.getTime();

            Calendar calendarDomingoActual = Calendar.getInstance();
            calendarDomingoActual.setTime(fechaLunes);
            calendarDomingoActual.add(Calendar.DAY_OF_MONTH, -1);
            Calendar calendarLunesActual = Calendar.getInstance();
            calendarLunesActual.setTime(fechaLunes);
            calendarLunesActual.add(Calendar.DAY_OF_MONTH, -7);
            Date fechaDomingoSemanaActual = calendarDomingoActual.getTime();
            Date fechaLunesSemanaActual = calendarLunesActual.getTime();

            Calendar calendarDomingoPasado = Calendar.getInstance();
            calendarDomingoPasado.setTime(fechaLunesSemanaActual);
            calendarDomingoPasado.add(Calendar.DAY_OF_MONTH, -1);
            Calendar calendarLunesPasado = Calendar.getInstance();
            calendarLunesPasado.setTime(fechaLunesSemanaActual);
            calendarLunesPasado.add(Calendar.DAY_OF_MONTH, -7);
            Date fechaDomingoSemanaPasado = calendarDomingoPasado.getTime();
            Date fechaLunesSemanaPasado = calendarLunesPasado.getTime();

            Calendar calendarDomingoNoRealizado = Calendar.getInstance();
            calendarDomingoNoRealizado.setTime(fechaLunesSemanaActual);
            calendarDomingoNoRealizado.add(Calendar.DAY_OF_MONTH, +5);
            Date fechaDomingoSemanasNoRealizado = calendarDomingoNoRealizado.getTime();
            

            // System.out.println(cale10.getTime() + " 10 dias antes de fecha del lunes cercano"); 
                                                                                               
            // System.out.println(cal.getTime() + " fecha del lunes cercano"); 
            // System.out.println(cale.getTime() + " fecha del domingo de la sig semana"); 
                                                                                        

            // System.out.println(fechaDomingoSemanaActual + " fecha del domingo semana actual"); 
            // System.out.println(fechaLunesSemanaActual + " fecha del lunes semana actual"); 

            // System.out.println(fechaDomingoSemanaPasado + " fecha del domingo semana pasada"); 
            // System.out.println(fechaLunesSemanaPasado + " fecha del lunes semana pasada"); 

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            // model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            // model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            // model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            // model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            // model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));

            List<UnidadFuncional> sinactividad = unidadService.listaUnidadesSinActividad(fechaLunes, fechaDomingo);
     
            List<UnidadFuncional> conactividad = unidadService.listaUnidadesConActividad(fechaLunes, fechaDomingo);
        

            Date hoy = new Date();
            if (hoy.after(fechaLunes)) {
                // System.out.println("la fecha si es posterior a lunes siguiente ");
            } else {// System.out.println("lo sentimo, la fecha no es posterior a lunes siguiente
                    // ");
            }

            Calendar FechaActual = Calendar.getInstance();
            Date fechaHoy = FechaActual.getTime();
            FechaActual.add(Calendar.DATE, -20);// fecha para ver 10 dias actras de la fecha actual
            Date fechaPasada10 = FechaActual.getTime();

         
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
          
            model.addAttribute("listaActividadesMiUnidadPasado", actividadService.findAll());
         

        
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/ruta-del-controlador-que-obtiene-los-lugares")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerLugares() {
        Map<String, Object> response = new HashMap<>();
        List<Lugar> lugares = lugarService.findAll();
        response.put("lugares", lugares);
        return ResponseEntity.ok(response);
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private LocalTime convertToLocalTime(Date date) {
        return new java.sql.Time(date.getTime()).toLocalTime();
    }
    
    @PostMapping(value="/actvidadPost")
    public String guardarActividada(@Validated Actividad actividad,
            @RequestParam(value = "fechasA") @DateTimeFormat(pattern = "yyyy-MM-dd") Date[] fechas,
            @RequestParam(value = "horasI") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_inicio,
            @RequestParam(value = "horasF") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_fin,
            @RequestParam(value = "lugaresA") List<Long> id_lugares,
            @RequestParam(value = "costos") List<Integer> costos,
            RedirectAttributes redirectAttrs,
            HttpServletRequest request) throws ParseException {
     
                for (int i = 0; i < fechas.length; i++) {
                    LocalDate fechaReserva = convertToLocalDate(fechas[i]);
                    LocalTime horaInicio = convertToLocalTime(hora_inicio.get(i));
                    LocalTime horaFin = convertToLocalTime(hora_fin.get(i));
                    LocalTime horaFinMenosUnSegundo = horaFin.minusSeconds(1);
                    Long idLugar = id_lugares.get(i);
                    Lugar lugar = lugarService.findOne(idLugar);
        
                    Object resultado = subDetalleActividadService.validarHoraReservas(fechaReserva, horaInicio, horaFinMenosUnSegundo, lugar.getNombre_lugar());
                    // System.out.println("FechaReserva: "+ fechaReserva);
                    // System.out.println("horaInicio: "+ horaInicio);
                    // System.out.println("horaFin: "+ horaFin);
                    // System.out.println("lugar: "+ lugar.getNombre_lugar());
                    if (resultado != null) {
                        redirectAttrs
                        .addFlashAttribute("mensaje",
                                "La fecha y hora que intenta registrar ya se encuentra reservada en "+ lugar.getNombre_lugar())
                        .addFlashAttribute("clase", "danger alert-dismissible fade show");
                        return "redirect:/actividadR";
                    }

                }

            
        String usuarioRegistro = request.getParameter("usuarioRegistro");


            EstadoActividad ea = estadoActividadService.findOne((long) 1);
            actividad.setEstado("A");
            actividad.setAvance_actividad("solicitado");
            actividad.setProgramdo(true);
            actividad.setEstadoActividad(ea);
            actividad.setFecha_registro(new Date());
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
                detalleActividad.setCosto(costos.get(i));
                detalleActividad.setFecha_registro(new Date());
                detalleActividad.setEstado("A");
               detalleActividadService.save(detalleActividad);

                Lugar lugar = lugarService.findOne(lugares.get(i).getId_lugar());
                Set<Lugar> lugarSet = new HashSet<>();
                lugarSet.add(lugar);
                SubDetalleActividad subDetalleActividad = new SubDetalleActividad();
                subDetalleActividad.setHora_inicio(hora_inicio.get(i));
                LocalTime horaFin = convertToLocalTime(hora_fin.get(i));
                LocalTime horaFinMenosUnSegundo = horaFin.minusSeconds(1);//9:08
                LocalDate fechaActual = LocalDate.now();//5/5/5 11:02
                LocalDateTime localDateTime = LocalDateTime.of(fechaActual, horaFinMenosUnSegundo);
                Date date = java.sql.Timestamp.valueOf(localDateTime);
                subDetalleActividad.setHora_final(date);
                subDetalleActividad.setDetalleActividad(detalleActividad);
                subDetalleActividad.setLugares(lugarSet);
                subDetalleActividad.setEstado("A");
                subDetalleActividadService.save(subDetalleActividad);
                if (lugar.getTipo_lugar().equals("E")) {
                        lugaresParaEvento.add(lugar);
                    }
                }
                // Set<Long> id_lugaress = new HashSet<>();
                // for (Lugar lugar2 : lugaresParaEvento) {
                //     long id_lugar = lugar2.getId_lugar();
                //     if (!id_lugaress.contains(id_lugar)) {
                //         id_lugaress.add(id_lugar);
                //     }
                // }
                Set<Long> id_lugaress = new HashSet<>();
                for (Lugar lugar2 : lugaresParaEvento) {
                    id_lugaress.add(lugar2.getId_lugar());
                }
                for (Long id_lugar : id_lugaress) {
                    System.out.println("ID del lugar único: " + id_lugar);
                    Lugar ll=lugarService.findOne(id_lugar);
                    Evento evento = new Evento();
                    evento.setLugar(ll);
                    evento.setActividad(actividad);
                    evento.setEstado_evento("A");
                    eventoServive.save(evento);
                }
            redirectAttrs
                    .addFlashAttribute("mensaje",
                            "Se creo la actividad satisfactoriamente!")
                    .addFlashAttribute("clase", "success alert-dismissible fade show");
            return "redirect:/actividadR";// "redirect:/detalleActividad/" + actividad.getId_actividad();
        
        
    }
    @PostMapping(value = "/actvidadPostADM")
    public String guardarActividadADM(@Validated Actividad actividad,
            @RequestParam(value = "fechasA") @DateTimeFormat(pattern = "yyyy-MM-dd") Date[] fechas,
            @RequestParam(value = "horasI") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_inicio,
            @RequestParam(value = "horasF") @DateTimeFormat(pattern = "HH:mm") List<Date> hora_fin,
            @RequestParam(value = "lugaresA") List<Long> id_lugares,
            RedirectAttributes redirectAttrs, HttpServletRequest request) throws ParseException {

        String usuarioRegistro = request.getParameter("usuarioRegistro");

        EstadoActividad ea = estadoActividadService.findOne((long) 1);
        actividad.setEstado("A");
        actividad.setAvance_actividad("solicitado");
        actividad.setEstadoActividad(ea);
        actividad.setProgramdo(true);
        actividad.setFecha_registro(new Date());
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
            detalleActividad.setEstado("A");
            detalleActividadService.save(detalleActividad);

            Lugar lugar = lugarService.findOne(lugares.get(i).getId_lugar());
            Set<Lugar> lugarSet = new HashSet<>();
            lugarSet.add(lugar);
            SubDetalleActividad subDetalleActividad = new SubDetalleActividad();
            subDetalleActividad.setHora_inicio(hora_inicio.get(i));
            subDetalleActividad.setHora_final(hora_fin.get(i));
            subDetalleActividad.setDetalleActividad(detalleActividad);
            subDetalleActividad.setLugares(lugarSet);
            subDetalleActividad.setEstado("A");
            subDetalleActividadService.save(subDetalleActividad);
            if (lugar.getTipo_lugar().equals("E")) {
                        lugaresParaEvento.add(lugar);
                    }
                }
                Set<Long> id_lugaress = new HashSet<>();
                for (Lugar lugar2 : lugaresParaEvento) {
                    long id_lugar = lugar2.getId_lugar();
                    if (!id_lugaress.contains(id_lugar)) {
                        id_lugaress.add(id_lugar);
                    }
                }
                for (Long id_lugar : id_lugaress) {
                    System.out.println("ID del lugar único: " + id_lugar);
                    Lugar ll=lugarService.findOne(id_lugar);
                    Evento evento = new Evento();
                    evento.setLugar(ll);
                    evento.setActividad(actividad);
                    evento.setEstado_evento("P");
                    eventoServive.save(evento);
                }
        return "redirect:/actividadR";//"redirect:/detalleActividad/" + actividad.getId_actividad();
    }

    @GetMapping(value = "/actividadM/{id_actividad}")
    public String formModificarActividad(@PathVariable(value = "id_actividad") Long id_actividad, Model model,
            HttpServletRequest request) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("edit", "true");
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", actividadService.findOne(id_actividad));
            model.addAttribute("personalADM", personalAdministrativo);
            //model.addAttribute("evaluacion", new Evaluacion());
            model.addAttribute("programacion", new Programacion());
            model.addAttribute("listaProgramaciones", programacionService.findAllOrderFechaDesc());
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());
            //model.addAttribute("tipoEvaluacion", tipoEvaluacionService.findAll());

            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();
           
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            if (u.getNivelFuncional().getId_nivel_funcional() == 1) {
                model.addAttribute("listaActividadesMiUnidad", actividadService.findAllOrderIdDesc());
                model.addAttribute("nivel", u.getNivelFuncional());
            }
            if (u.getNivelFuncional().getId_nivel_funcional() == 2) {

                model.addAttribute("listaActividadesMiUnidad", actividadService
                        .listaActividadesPorDireccion(u.getDireccionFuncional().getId_direccion_funcional()));
                model.addAttribute("nivel", u.getNivelFuncional());
            }
            if (u.getNivelFuncional().getId_nivel_funcional() >= 3) {
                model.addAttribute("listaActividadesMiUnidad", actividadService.listaActividadesPorUnidad(
                        personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
                model.addAttribute("nivel", u.getNivelFuncional());
            }

            
            return "actividad/formulario";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "editarActividad/{id_actividad}")
    public @ResponseBody Actividad actividadIdEditar(
            @PathVariable(value = "id_actividad") Long id_actividad) {
        Actividad actividad = actividadService.findOne(id_actividad);
        System.out.println(actividad.getNombre_actividad() + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return actividad;
    }

    @GetMapping(value = "/detalleActividad/{id_actividad}")
    public String formDetalleActividad(@PathVariable(value = "id_actividad") Long id_actividad, Model model,
            HttpServletRequest request) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("actividad", actividadService.findOne(id_actividad));
            model.addAttribute("subDetalle", new SubDetalleActividad());
            model.addAttribute("lugares", lugarService.findAll());// lugarService.sacarLugaresConTipoE("N")

            model.addAttribute("unidadFuncionales", unidadService.findAll());
            model.addAttribute("tiposActividades", tipoActividadService.findAll());
            model.addAttribute("ListAfluencias", afluenciaService.findAll());
            personalAdministrativo = personalAdministrativoService
                    .findOne(personalAdministrativo.getId_personal_administrativo());
            model.addAttribute("personalAdministrativo", personalAdministrativo);
            model.addAttribute("persona", personaService.findOne(personalAdministrativo.getPersona().getId_persona()));
            return "actividad/detalleFormulario";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/lugares")
    @ResponseBody
    public List<Lugar> obtenerLugaresPorTipo(@RequestParam("tipo_lugar") String tipoLugar) {
    
        List<Lugar> listaLugaresE = lugarService.sacarLugaresConTipoE(tipoLugar);
        return listaLugaresE;
    }

    @RequestMapping(value = "/actvidadPostp", method = RequestMethod.POST)
    public String modificarActividad(Model model, @Validated Actividad actividad,
            @RequestParam(value = "fechaActual") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            HttpServletRequest request) {
        System.out.println("*************************************************************");
        actividad.setFecha_registro(fecha);
        System.out.println("*************************************************************");
        actividadService.save(actividad);
        System.out.println("*************************************************************");
        return "redirect:/detalleActividad/" + actividad.getId_actividad();
    }

    @PostMapping(value = "/detalleActividadPost")
public String detalleActividadPost(
        @Validated SubDetalleActividad subDetalleActividad,
        @RequestParam("inicio") String inicio,
        @RequestParam("fin") String fin,
        @RequestParam("id_da") Long id_detalle_actividad,
        @RequestParam("lugar") Long id_lugar,
        @RequestParam("costo") Integer costo,
        RedirectAttributes redirectAttrs) throws ParseException {

    // Convertir las horas de inicio y fin
    Date date1 = new SimpleDateFormat("HH:mm").parse(inicio);
    Date date2 = new SimpleDateFormat("HH:mm").parse(fin);

    // Recuperar DetalleActividad y Lugar desde la base de datos
    DetalleActividad dac = detalleActividadService.findOne(id_detalle_actividad);
    Lugar lugarN = lugarService.findOne(id_lugar);

    // Convertir Date a LocalTime
    LocalTime horaInicio = convertToLocalTime(date1);
    LocalTime horaFin = convertToLocalTime(date2);
    LocalTime horaFinMenosUnSegundo = horaFin.minusSeconds(1);

    // Obtener la fecha actual y convertirla a LocalDateTime
    LocalDate fechaActual = LocalDate.now();
    LocalDateTime localDateTime = LocalDateTime.of(fechaActual, horaFinMenosUnSegundo);
    Date horaFinMenos = java.sql.Timestamp.valueOf(localDateTime);

    // Obtener la fecha de la actividad y convertirla a LocalDate
    Date fechaDetalleActividad = dac.getFecha_detalle_actividad();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    String fechaFormateada = formatoFecha.format(fechaDetalleActividad);
    Date fechaFormateadaDate = formatoFecha.parse(fechaFormateada);
    LocalDate fechaReserva = convertToLocalDate(fechaFormateadaDate);
   
    // Validar la hora de reservas
    Object resultado = subDetalleActividadService.validarHoraReservas(fechaReserva, horaInicio, horaFinMenosUnSegundo, lugarN.getNombre_lugar());
    if (resultado != null) {
        redirectAttrs.addFlashAttribute("mensaje", "La fecha y hora que intenta registrar ya se encuentra reservada")
                      .addFlashAttribute("clase", "danger alert-dismissible fade show");
        return "redirect:/detalleActividad/" + dac.getActividad().getId_actividad();
    }

    // Inicializar el conjunto de lugares si es null
    if (subDetalleActividad.getLugares() == null) {
        subDetalleActividad.setLugares(new HashSet<>());
    }

    // Añadir el lugar al conjunto de lugares
    subDetalleActividad.getLugares().add(lugarN);

    // Procesar lógica adicional relacionada con lugares especiales
    List<Lugar> l = new ArrayList<>(subDetalleActividad.getLugares());
    List<Lugar> listaLugares = new ArrayList<>();
    Actividad actividad = actividadService.findOne(dac.getActividad().getId_actividad());
    for (DetalleActividad da : actividad.getDetalleActividads()) {
        for (SubDetalleActividad ssda : da.getSubDetalleActividads()) {
            for (Lugar lu : ssda.getLugares()) {
                if (lu.getTipo_lugar().equals("E") && da.getEstado().equals("A") && ssda.getEstado().equals("A")) {
                    listaLugares.add(lu);
                }
            }
        }
    }

    int cant = 0;
    boolean noEsEspecial = false;
    List<Evento> leventos = new ArrayList<>();
    if (l.get(0).getTipo_lugar().equals("E")) {
        Evento event = eventoDao.sacarEventoPorLugarYActividad(l.get(0).getId_lugar(), actividad.getId_actividad());
        leventos.add(event);
        for (Lugar lugar : listaLugares) {
            Lugar lua = lugarService.findOne(l.get(0).getId_lugar());
            if (lua.equals(lugar)) {
                cant++;
            }
        }
    } else {
        noEsEspecial = true;
    }

    // Guardar SubDetalleActividad con su correspondiente DetalleActividad y lugares
    subDetalleActividad.setDetalleActividad(dac);
    subDetalleActividad.setHora_inicio(date1);
    subDetalleActividad.setHora_final(horaFinMenos);
    subDetalleActividad.setEstado("A");

    dac.setCosto(costo);
    detalleActividadService.save(dac);

    if (noEsEspecial) {
        subDetalleActividadService.save(subDetalleActividad);
    } else {
        if (cant == 0) {
            subDetalleActividadService.save(subDetalleActividad);
            Evento evento = new Evento();
            evento.setLugar(l.get(0));
            evento.setActividad(actividad);
            evento.setEstado_evento("P");
            eventoServive.save(evento);
        } else {
            subDetalleActividadService.save(subDetalleActividad);
        }
    }

    System.out.println("id detalle actividad en post agregar hora y lugar " + dac.getFecha_detalle_actividad());

    return "redirect:/detalleActividad/" + dac.getActividad().getId_actividad();
}

    @PostMapping(value = "/modificarDetalleActividad")
    public String modificarDetalleActividad(
            @RequestParam(value = "fec") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_detalle_actividad,
            @RequestParam(value = "id_dtact") Long id_detalle_actividad, RedirectAttributes redirectAttrs) {
        LocalDate d = LocalDate.now();
        LocalDate nuevoFecha = convertToLocalDateViaInstant(fecha_detalle_actividad);
        // System.out.println(d+" "+fechaMinima);
        DetalleActividad detalleActividad = detalleActividadService.findOne(id_detalle_actividad);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
        Date fechal = cal.getTime();
        Calendar fDomingoActual = Calendar.getInstance();
        fDomingoActual.setTime(fechal);
        fDomingoActual.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaDomingoActual = fDomingoActual.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
        Date fechaDPas = cal2.getTime();
        Calendar fechaDomPas = Calendar.getInstance();
        fechaDomPas.setTime(fechaDPas);
        fechaDomPas.add(Calendar.DAY_OF_MONTH, -8);
        Date fechaDomingoPasado = fechaDomPas.getTime();
        
        if (fecha_detalle_actividad.after(fechaDomingoActual)
                && detalleActividad.getFecha_detalle_actividad().after(fechaDomingoActual)) {
            detalleActividad.setFecha_detalle_actividad(fecha_detalle_actividad);
            detalleActividad.setFecha_modificacion(new Date());
            detalleActividadService.save(detalleActividad);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Fecha modificado exitoso Semana Actual")
                    .addFlashAttribute("clase", "success alert-dismissible fade show");
            return "redirect:/detalleActividad/" + detalleActividad.getActividad().getId_actividad();
        } else if (fecha_detalle_actividad.before(fechal)
                && detalleActividad.getFecha_detalle_actividad().after(fechaDomingoActual)) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "la fecha tiene que ser del lunes siguiente en adelante!")
                    .addFlashAttribute("clase", "danger alert-dismissible fade show");
            return "redirect:/detalleActividad/" + detalleActividad.getActividad().getId_actividad();
        }

        if (detalleActividad.getFecha_detalle_actividad().before(fechal)
                && fecha_detalle_actividad.after(fechaDomingoPasado)) {
            detalleActividad.setFecha_detalle_actividad(fecha_detalle_actividad);
            detalleActividad.setFecha_modificacion(new Date());
            detalleActividadService.save(detalleActividad);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Fecha modificado exitoso")
                    .addFlashAttribute("clase", "success alert-dismissible fade show");
            return "redirect:/detalleActividad/" + detalleActividad.getActividad().getId_actividad();
        } else {
            redirectAttrs
                    .addFlashAttribute("mensaje", "la fecha tiene que ser semana actual en adelante")
                    .addFlashAttribute("clase", "danger alert-dismissible fade show");
            return "redirect:/detalleActividad/" + detalleActividad.getActividad().getId_actividad();
        }
    }

    @PostMapping(value = "/agregarFechaActividad")
    public String agregarFechaActividad(
            @RequestParam(value = "fech") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_detalle_actividad,
            @RequestParam(value = "id_acti") Long id_actividad, RedirectAttributes redirectAttrs) {

        LocalDate d = LocalDate.now();
        LocalDate nuevoFecha = convertToLocalDateViaInstant(fecha_detalle_actividad);
        // System.out.println(d+" "+fechaMinima);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
        Date fechal = cal.getTime();
        Calendar fDomingoActual = Calendar.getInstance();
        fDomingoActual.setTime(fechal);
        fDomingoActual.add(Calendar.DAY_OF_MONTH, -1);
        Date fechaDomingoActual = fDomingoActual.getTime();

        if (nuevoFecha.isBefore(d)) {
            System.out.println("La fecha agregada es anterior a la fecha actual");
            redirectAttrs
                    .addFlashAttribute("mensaje", "la fecha tiene que ser actual o en adelante")
                    .addFlashAttribute("clase", "danger alert-dismissible fade show");
            return "redirect:/detalleActividad/" + id_actividad;
        } else if (fecha_detalle_actividad.after(fechaDomingoActual)) {

            Actividad actividad = actividadService.findOne(id_actividad);
            System.out.println(actividad.getId_actividad() + " id acti");
            DetalleActividad detalleActividad = new DetalleActividad();
            detalleActividad.setFecha_detalle_actividad(fecha_detalle_actividad);
            detalleActividad.setActividad(actividad);
            detalleActividad.setFecha_registro(new Date());
            detalleActividad.setEstado("A");
            detalleActividadService.save(detalleActividad);
            redirectAttrs
                    .addFlashAttribute("mensaje", "Fecha Agregada con éxito")
                    .addFlashAttribute("clase", "success alert-dismissible fade show");
            return "redirect:/detalleActividad/" + id_actividad;
        } else {
            redirectAttrs
                    .addFlashAttribute("mensaje", "la fecha tiene que ser de la siguiente semana")
                    .addFlashAttribute("clase", "danger alert-dismissible fade show");
            return "redirect:/detalleActividad/" + id_actividad;
        }

    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @RequestMapping(value = "/sacarFechasAactividad", method = RequestMethod.GET)
    public @ResponseBody List<DetalleActividad> sacarFechasAactividad(
            @RequestParam(value = "idDetAct") Long id_actividad) {
        System.out.println(id_actividad + "*******************");
        List<DetalleActividad> detalles = detalleActividadService.detallePorIdActividad((long) 3);
        return detalles;
    }

    @RequestMapping(value = "/sacarHrs", method = RequestMethod.GET)
    public @ResponseBody List<SubDetalleActividad> findAllHoras(
            @RequestParam(value = "detAcId", required = true) Long id_detalle) {
        System.out.println(id_detalle);
        List<SubDetalleActividad> subDetalles = subDetalleActividadService.subDetallePorIdDetalles(id_detalle);
        for (SubDetalleActividad subDetalleActividad : subDetalles) {
            System.out.println(subDetalleActividad.getHora_inicio());
        }
        return subDetalles;
    }

    @GetMapping(value = "/programarActividad/{id_activida}")
    public String guardarProgramacion(@PathVariable(value = "id_activida") Long id_actividad,
            RedirectAttributes redirectAttrs) {

        Actividad actividad = actividadService.findOne(id_actividad);
        Programacion programacion = new Programacion();
        programacion.setEstado("A");
        programacion.setActividad(actividad);
        programacion.setLugar(null);
        programacionService.save(programacion);
        redirectAttrs
                .addFlashAttribute("mensaje", "Programación exitoso")
                .addFlashAttribute("clase", "success alert-dismissible fade show");

        return "redirect:/actividadR";
    }

    @GetMapping("/eliminar-actividad/{id_actividad}")
    public String eliminarActividad(@PathVariable(value = "id_actividad") Long id_actividad,
            RedirectAttributes redirectAttrs) {
        Actividad actividad = actividadService.findOne(id_actividad);
        
        int existeE = 0;
        for (DetalleActividad da : actividad.getDetalleActividads()) {
            for (SubDetalleActividad ssda : da.getSubDetalleActividads()) {
                for (Lugar l : ssda.getLugares()) {
                    if (l.getTipo_lugar().equals("E") && da.getEstado().equals("A") && ssda.getEstado().equals("A")) {
                        existeE++;                        
                    }                                                                                   
                }                                 
            }            
        }
        // if (existeE>0) {
        //     //Evento e = actividad.getEventos().get(0);
        //     for (Evento ee : actividad.getEventos()) {
        //         System.out.println(ee.getEstado_evento()+"-----------------"+existeE);
        //         eventoDao.eliminarEvento(ee.getId_evento(), id_actividad);
        //     }
            
        // }
        for (DetalleActividad da : actividad.getDetalleActividads()) {
            for (SubDetalleActividad sda : da.getSubDetalleActividads()) {
                sda.setEstado("X");
                subDetalleActividadService.save(sda);
            }
            da.setEstado("X");
            detalleActividadService.save(da);
        }
        actividad.setEstado("X");
        actividadService.save(actividad);
        redirectAttrs
                .addFlashAttribute("mensaje", "Actividad # " + id_actividad + " eliminado con éxito")
                .addFlashAttribute("clase", "danger alert-dismissible fade show");

        return "redirect:/actividadR";
    }

    @GetMapping("/eliminar-detalle/{id_detalle}/{id_actividad}")
    public String eliminarDetalle(@PathVariable("id_detalle") Long id_detalle,
        @PathVariable("id_actividad") Long id_actividad, RedirectAttributes redirectAttrs) {
        System.out.println(id_detalle + " /////////" + id_actividad);
        DetalleActividad detalleActividad = detalleActividadService.findOne(id_detalle);
        
        List<SubDetalleActividad> sdal = new ArrayList<>();//detalleActividad.getSubDetalleActividads();
        List<SubDetalleActividad> sdalE = detalleActividad.getSubDetalleActividads();
        //todos los lugares de la actividad
        List<Lugar> listaLugares = new ArrayList<>();
        Actividad actividad = actividadService.findOne(id_actividad);
        for (DetalleActividad da : actividad.getDetalleActividads()) {
            for (SubDetalleActividad ssda : da.getSubDetalleActividads()) {
                for (Lugar l : ssda.getLugares()) {
                    if (l.getTipo_lugar().equals("E") && da.getEstado().equals("A") && ssda.getEstado().equals("A")) {
                        listaLugares.add(l);   
                        sdal.add(ssda);                          
                    }                                                                 
                }                    
            }                
        }
        //aqui tengo lugares que voy a eliminar de la fecha
        List<Lugar> llugares = new ArrayList<>();
        List<SubDetalleActividad> lsubcap = new ArrayList<>();
        List<SubDetalleActividad> lsubdet = detalleActividad.getSubDetalleActividads();
        for (SubDetalleActividad subDetalleActividad : lsubdet) {
            Set<Lugar> lugaresDeSub = subDetalleActividad.getLugares();
            for (Lugar lugare : lugaresDeSub) {
                if (subDetalleActividad.getEstado().equals("A")) {
                    llugares.add(lugare);    
                    lsubcap.add(subDetalleActividad);
                }
                
            }
        }

        boolean noEsEspecial = false;
        int cant=0;
        int a=0;
        List<Evento> leventos = new ArrayList<>();
        for (Lugar lugarCapturado : llugares) {    
            if (lugarCapturado.getTipo_lugar().equals("E")) {
                Evento event = eventoDao.sacarEventoPorLugarYActividad(lugarCapturado.getId_lugar(), id_actividad);
                leventos.add(event);
                for (Lugar lugar : listaLugares) {
                    if (lugarCapturado.equals(lugar)) {
                        cant++;
                        a=1;
                    }
                }
            }else{//System.out.println("eliminando lugar N");
            noEsEspecial=true;}
        } 
        if(noEsEspecial){
            System.out.println("eliminando lugar N");
            for (SubDetalleActividad sda : lsubcap) {                
                    for (Lugar llll : sda.getLugares()) {
                        if (llll.getTipo_lugar().equals("E") && a==1) {
                            System.out.println("cantidad q existe E "+cant);
                            for (Lugar lus : sda.getLugares()) {
                                System.out.println(sda.toString());
                                System.out.println(lus.getId_lugar()+"======="+id_actividad);
                                Evento event = eventoDao.sacarEventoPorLugarYActividad(lus.getId_lugar(), id_actividad);
                                eventoServive.delete(event.getId_evento()); 
                            }    
                        }                        
                    }
                sda.setEstado("X");
                subDetalleActividadService.save(sda);                  
            }            
        }
        else{
            if (cant>1){
                System.out.println("el lugar aun existe en otra fecha");
                for (SubDetalleActividad sda : sdalE) {
                    sda.setEstado("X");
                    subDetalleActividadService.save(sda);    
                }
            }
            else {
                System.out.println("eliminando el ultimo lugar E");
                for (SubDetalleActividad sda : sdal) {
                    System.out.println("hay lugares ////////// "+sda.getLugares().iterator().next());
                    for (Lugar lus : sda.getLugares()) {
                        System.out.println(sda.toString());
                        System.out.println(lus.getId_lugar()+"======="+id_actividad);
                        Evento event = eventoDao.sacarEventoPorLugarYActividad(lus.getId_lugar(), id_actividad);
                        System.out.println(event.getId_evento()+">>>>>");
                        eventoServive.delete(event.getId_evento()); 
                    }
                    sda.setEstado("X");
                    subDetalleActividadService.save(sda);
                }
            }
        }
        detalleActividad.setEstado("X");
        detalleActividadService.save(detalleActividad);
        redirectAttrs
                .addFlashAttribute("mensaje", "Fecha eliminado con éxito")
                .addFlashAttribute("clase", "danger alert-dismissible fade show");
        return "redirect:/detalleActividad/" + id_actividad;
    }

    @GetMapping("/eliminar-sub-detalle/{id_sub_detalle}/{id_actividad}")
    public String eliminarSubDetalle(@PathVariable("id_sub_detalle") Long id_sub_detalle,
            @PathVariable("id_actividad") Long id_actividad, RedirectAttributes redirectAttrs) {

        //sacando todos los lugares de la actividad
        List<Lugar> listaLugares = new ArrayList<>();
        Actividad actividad = actividadService.findOne(id_actividad);
        for (DetalleActividad da : actividad.getDetalleActividads()) {
            for (SubDetalleActividad ssda : da.getSubDetalleActividads()) {
                for (Lugar l : ssda.getLugares()) {
                    if (l.getTipo_lugar().equals("E") && da.getEstado().equals("A") && ssda.getEstado().equals("A")) {
                        listaLugares.add(l);                             
                    }                                                                 
                }                    
            }                
        }
        SubDetalleActividad subDetalleActividad = subDetalleActividadService.findOne(id_sub_detalle);
        Set<Lugar> lug = subDetalleActividad.getLugares();
        boolean noEsEspecial = false;
        int cant=0;
        List<Evento> leventos = new ArrayList<>();
        for (Lugar lugarCapturado : lug) {    
            if (lugarCapturado.getTipo_lugar().equals("E")) {
                Evento event = eventoDao.sacarEventoPorLugarYActividad(lugarCapturado.getId_lugar(), id_actividad);
                leventos.add(event);
                for (Lugar lugar : listaLugares) {
                    if (lugarCapturado.equals(lugar)) {
                        cant++;
                    }
                }
            }else{//System.out.println("eliminando lugar N");
            noEsEspecial=true;}
        } 
        if(noEsEspecial){
            System.out.println("eliminando lugar N");
            subDetalleActividad.setEstado("X");
            subDetalleActividadService.save(subDetalleActividad);
        }
        else{
            if (cant>1){
                System.out.println("el lugar aun existe en otra fecha");
                subDetalleActividad.setEstado("X");
                subDetalleActividadService.save(subDetalleActividad);
            }
            else {
                System.out.println("eliminando el ultimo lugar E");
                subDetalleActividad.setEstado("X");
                subDetalleActividadService.save(subDetalleActividad);
                for (Lugar lu : lug) {
                    Evento event = eventoDao.sacarEventoPorLugarYActividad(lu.getId_lugar(), id_actividad);
                    eventoServive.delete(event.getId_evento());
                }
                
            }
        }
        redirectAttrs
                .addFlashAttribute("mensaje", "Hora y lugar eliminado con éxito.")
                .addFlashAttribute("clase", "danger alert-dismissible fade show");

        System.out.println("Actividad eliminado del evento " + id_actividad);

        return "redirect:/detalleActividad/" + id_actividad;
    }

 

   

    @GetMapping("/formularioMesAnio")
    public String formularioMesAnio() {

        return "content :: content1";
    }

    @GetMapping("/formularioPorCarrera")
    public String formularioPorCarrera(Model model) {
        model.addAttribute("direccionFuncionales", direccionFuncionalService.findAll());
        return "content :: content2";
    }

    @GetMapping("/formularioPorCarreraMesAnio")
    public String formularioPorCarreraMesAnio(Model model) {
        model.addAttribute("direccionFuncionales", direccionFuncionalService.findAll());
        return "content :: content3";
    }

    @GetMapping("/formularioPorDireccion")
    public String formularioPorDireccion(Model model) {
        model.addAttribute("direccionFuncionales", direccionFuncionalService.findAll());
        return "content :: content4";
    }

    @GetMapping("/formularioPorDireccionMesAnio")
    public String formularioPorDireccionMesAnio(Model model) {
        model.addAttribute("direccionFuncionales", direccionFuncionalService.findAll());
        return "content :: content5";
    }

    @GetMapping(value = "/filtroPorFechasYAnio")
    public String filtroPorFechasYAnio(Model model, HttpServletRequest request,
            @RequestParam(value = "mes") int mes,
            @RequestParam(value = "anio") int anio) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", new Actividad());
 
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());


            long sic = 0;
           
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("listaActividadesMiUnidad", actividadService.listaActividadesPorMesAnio(mes, anio));

            // model.addAttribute("listaActividadesMiUnidad",
            // actividadService.listaActividadesPorUnidad(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/filtroPorUnidad")
    public String filtroPorUnidad(Model model, HttpServletRequest request,
            @RequestParam(value = "id_unidad") Long id_unidad) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", new Actividad());
    
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());
      

            long sic = 0;
           
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("listaActividadesMiUnidad", actividadService.listaActividadesPorUnidad(id_unidad));
            // model.addAttribute("listaActividadesMiUnidad",
            // actividadService.listaActividadesPorUnidad(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/filtroPorUnidadMesAnio")
    public String filtroPorUnidadMesAnio(Model model, HttpServletRequest request,
            @RequestParam(value = "id_unidad") Long id_unidad,
            @RequestParam(value = "mes") int mes,
            @RequestParam(value = "anio") int anio) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", new Actividad());

            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());


            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();
         
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("listaActividadesMiUnidad",
                    actividadService.listaActividadesPorUnidadMesAnio(id_unidad, mes, anio));
            // model.addAttribute("listaActividadesMiUnidad",
            // actividadService.listaActividadesPorUnidad(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/filtroPorDireccion")
    public String filtroPorDireccion(Model model, HttpServletRequest request,
            @RequestParam(value = "id_direccion") Long id_direccion) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", new Actividad());

            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());


            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();
       
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("listaActividadesMiUnidad", actividadService.listaActividadesPorDireccion(id_direccion));
            // model.addAttribute("listaActividadesMiUnidad",
            // actividadService.listaActividadesPorUnidad(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/filtroPorDireccionMesAnio")
    public String filtroPorDireccionMesAnio(Model model, HttpServletRequest request,
            @RequestParam(value = "id_direccion") Long id_direccion,
            @RequestParam(value = "mes") int mes,
            @RequestParam(value = "anio") int anio) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("actividad", new Actividad());
  
            model.addAttribute("tipoActividades", tipoActividadService.findAll());
            model.addAttribute("afluencias", afluenciaService.findAll());
            model.addAttribute("unidadFuncionales", unidadService.findAll());


            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();
         
            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("listaActividadesMiUnidad",
                    actividadService.listaActividadesPorDireccionMesAnio(id_direccion, mes, anio));
            // model.addAttribute("listaActividadesMiUnidad",
            // actividadService.listaActividadesPorUnidad(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional()));
            return "actividad/formulario";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/modeloDatos")
    public String modeloDatos(Model model, HttpServletRequest request) {
        System.out.println("dsfsdfsdfsdffffffffffffffffffffffffffffffff");
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("unidad", unidadFuncional);
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            return "actividad/modelodatos";
        } else {
            return "redirect:/login";
        }

    }

    @PostMapping(value = "/nuevoLugar")
    public String guardarNuevoLugar(
            @RequestParam(value = "id_dtac") Long id_detalle,
            @RequestParam(value = "id_ac") Long id_actividad,
            @RequestParam(value = "nombre_lugar") String nombre_lugar,
            RedirectAttributes redirectAttrs) {

        Lugar lugar = new Lugar();
        lugar.setNombre_lugar(nombre_lugar);
        lugar.setTipo_lugar("N");
        lugar.setEstado_lugar("A");
        lugarService.save(lugar);

        /*
         * DetalleActividad detalleActividad =
         * detalleActividadService.findOne(id_detalle);
         * detalleActividad.setEstado("X");
         * detalleActividadService.save(detalleActividad);
         */
        redirectAttrs
                .addFlashAttribute("mensaje", "Registro Exitoso de Lugar")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/detalleActividad/" + id_actividad;
    }

    @PostMapping(value = "/PerfilMod")
    public String ModificarPerfil(Model model, @Validated PersonalAdministrativo personalAdministrativo,
            @Validated Persona persona) {
        System.out.println("PERFIL-------------------------------------------------------------------------");
        personaService.save(persona);
        personalAdministrativo.setPersona(persona);

        personalAdministrativoService.save(personalAdministrativo);

        return "redirect:/actividadR";
    }
}
