package com.uap.planificacion.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uap.planificacion.model.dao.ILugarDao;
import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.Programacion;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.IProgramacionService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProgramacionController {
    @Autowired
    private IUnidadFuncionalService unidadService;
    @Autowired
    private IProgramacionService programacionService;
    @Autowired
    private ILugarService lugarService;
    @Autowired
    private IActividadService actividadService;

    @GetMapping(value = {"/programarActividadImpacto","/programarActividadImpacto/{fechaHoy}/{a}"})
    public String programacionRegistros(
        @PathVariable(value = "fechaHoy", required=false)String fechaHoy,
        @PathVariable(value = "a", required=false)String da, Model model, HttpServletRequest request) throws ParseException{
        if(request.getSession().getAttribute("personalAdministrativo")!=null){

            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
            UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
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
            
            //cale10.setTime(fechaLunes);
            cale.add(Calendar.DATE, +6); //domingo de la siguiente semana
            cale10.add(Calendar.DATE, -10);//10 dias antes de de la fecha de registros de actividades
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            List<Date> fechasSemanaPasada = new ArrayList<>();// Obtener el rango de fechas semana pasada           
            LocalDate fechaLunesPasada = fechaLunesSemanaPasado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            while (!fechaLunesPasada.isAfter(fechaDomingoSemanaPasado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                Date fechaPasada = Date.from(fechaLunesPasada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechasSemanaPasada.add(fechaPasada);
                fechaLunesPasada = fechaLunesPasada.plusDays(1);
            }
            List<Date> fechasSemanaActual = new ArrayList<>();// Obtener el rango de fechas semana pasada           
            LocalDate fechaLunesActual = fechaLunesSemanaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            while (!fechaLunesActual.isAfter(fechaDomingoSemanaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                Date fechaActual = Date.from(fechaLunesActual.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechasSemanaActual.add(fechaActual);
                fechaLunesActual = fechaLunesActual.plusDays(1);
            }
            List<Date> fechasSemanaSiguiente = new ArrayList<>();// Obtener el rango de fechas semana pasada           
            LocalDate fechaLunesSiguiente = fechaLunes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            while (!fechaLunesSiguiente.isAfter(fechaDomingo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                Date fechaSiguiente = Date.from(fechaLunesSiguiente.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechasSemanaSiguiente.add(fechaSiguiente);
                fechaLunesSiguiente = fechaLunesSiguiente.plusDays(1);
            }
    
            
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("unidad", unidadFuncional);
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));

            
            //lista de fechas por semana
            model.addAttribute("fechasSemanaPasada", fechasSemanaPasada);
            model.addAttribute("fechasSemanaActual", fechasSemanaActual);
            model.addAttribute("fechasSemanaSiguiente", fechasSemanaSiguiente);
            System.out.println(fechaHoy);
            if (fechaHoy != null) {
                System.out.println(fechaHoy);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha1 = dateFormat.parse(fechaHoy);
                    model.addAttribute("listaActividadesPorFecha", actividadService.listaActividadesImpactoPorFecha(fecha1));
                    
                    // La variable fecha es un objeto de tipo java.util.Date
                    // Puedes utilizarla para realizar operaciones con fechas
                    if (da.equals("a")) {
                    model.addAttribute("fechasSemanaPasada", fechasSemanaPasada);
                    model.addAttribute("moda", "true");
                    model.addAttribute("modb", "false");
                    model.addAttribute("modc", "false");
                    model.addAttribute("active", "");
                    }

                    if (da.equals("b")) {
                    model.addAttribute("fechasSemanaActual", fechasSemanaActual);
                    model.addAttribute("modb", "true");
                    model.addAttribute("moda", "false");
                    model.addAttribute("modc", "false");
                    model.addAttribute("active", "");
                    }
                    if (da.equals("c")) {
                    model.addAttribute("fechasSemanaSiguiente", fechasSemanaSiguiente);
                    model.addAttribute("modc", "true");
                    model.addAttribute("moda", "false");
                    model.addAttribute("modb", "false");
                    model.addAttribute("active", "");
                    }
                } catch (ParseException e) {
                    e.toString();
                }
            } else {
                
                model.addAttribute("listaActividadesPorFecha", actividadService.listaActividadesImpactoPorFecha(fechaLunesSemanaActual));
                model.addAttribute("modb", "true");
                model.addAttribute("active", " active");
            }
            
            return "actividad/misProgramaciones";
        
        }
        else{
            return "redirect:/login";
        }
    }
    @PostMapping(value="/programarActividadI")
    public String guardarProgramacion(
    RedirectAttributes redirectAttrs, HttpServletRequest request){
        String id_a = request.getParameter("id_actividaI");
        Long id_actividad = Long.parseLong(id_a);
        Actividad actividad = actividadService.findOne(id_actividad);
        Programacion programacion = new Programacion();
        programacion.setEstado("A");
        programacion.setActividad(actividad);
        programacion.setLugar(null);
        programacionService.save(programacion);
        redirectAttrs
                .addFlashAttribute("mensaje", "Programación exitoso")
                .addFlashAttribute("clase", "success alert-dismissible fade show");

        return "redirect:/programarActividadImpacto";
    }
    @PostMapping(value = "/cargarFechaProgramacion")
    public String cargarFechaProgramacion(RedirectAttributes redirectAttrs,
        @RequestParam(value = "id_pro")Long id_programacion,
        @RequestParam(value = "fechaProgramacion")@DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_programacion,
        @RequestParam(value = "horaProgramacion")@DateTimeFormat(pattern = "HH:mm") Date hora_programacion,
        @RequestParam(value = "tipoProgramacion")String tipoProgramacion,
        @RequestParam(value = "id_l")Long id_l){

            Lugar lugar = lugarService.findOne(id_l);

            Programacion programacion = programacionService.findOne(id_programacion);
            programacion.setFecha(fecha_programacion);
            programacion.setHora(hora_programacion);
            programacion.setLugar(lugar);
            programacion.setTipo_programacion(tipoProgramacion);
            programacionService.save(programacion);

        redirectAttrs
        .addFlashAttribute("mensaje", "Actividad Programada con éxito")
        .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/programadosL";
    }

    @GetMapping(value = "/sacarLugarPorProgramacion")
    public @ResponseBody List<Lugar> lugaresPorProgramacion(
        @RequestParam(value = "idProgramacion")Long idProgramacion){
        
        System.out.println(idProgramacion+"//////////////");
        List<Lugar> lugares=lugarService.sacarLugaresPorProgramacion(idProgramacion);
        for (Lugar lugar : lugares) {
            System.out.println(lugar.getNombre_lugar());
        }
        return lugares;
    }

    @GetMapping("/programadosL")
    public String programdoslistaSemanas(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
            UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
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
            
            //cale10.setTime(fechaLunes);
            cale.add(Calendar.DATE, +6); //domingo de la siguiente semana
            cale10.add(Calendar.DATE, -10);//10 dias antes de de la fecha de registros de actividades
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

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));
            model.addAttribute("listaProgramacionesSemanaPasada", programacionService.listaProgramacionRangoPorFechas(fechaLunesSemanaPasado, fechaDomingoSemanaPasado));
            model.addAttribute("listaProgramacionesSemanaActual", programacionService.listaProgramacionRangoPorFechas(fechaLunesSemanaActual, fechaDomingoSemanaActual));
            model.addAttribute("listaProgramacionesSemanaSiguiente", programacionService.listaProgramacionRangoPorFechas(fechaLunes, fechaDomingo));
          
            return "programacion/programados";
        }
        else{
            return "redirect:/login";
        }
        
    }


    @GetMapping(value = "/reporte")
    public String reporteImpacto(HttpServletRequest request,
        @RequestParam(value = "fecha1")Date fecha1,
        @RequestParam(value = "fecha2")Date fecha2,
        @RequestParam(value = "tipo")String tipo, Model model){

        if(request.getSession().getAttribute("personalAdministrativo")!=null){
                PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
                UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                
                UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
                model.addAttribute("unidad", unidadFuncional);
            if(tipo.equals("TODO")){
                model.addAttribute("programados", programacionService.listaProgramacionRangoPorFechasProgramadas(fecha1, fecha2));
                System.out.println("entrando a todos");
            }else{
                model.addAttribute("programados", programacionService.listaProgramacionRangoPorFechasTipo(fecha1, fecha2, tipo));
            }
        return "programacion/reporte";
        }
        else{
            return "redirect:/login";
        }
    }


    @GetMapping(value = "/contactos")
    public String contactos(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
            model.addAttribute("unidad", unidadFuncional);
            UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            return "programacion/contacto";
        }else{
            return "redirect:/login";
        }
        
    }
}
