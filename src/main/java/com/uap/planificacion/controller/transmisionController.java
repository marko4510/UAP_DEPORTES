package com.uap.planificacion.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.Evaluacion;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.Programacion;
import com.uap.planificacion.model.entity.Transmision;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.ITransmisionService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

@Controller
public class transmisionController {

    @Autowired
    private IUnidadFuncionalService unidadService;
    @Autowired
    private IActividadService actividadService;
    @Autowired
    private ITransmisionService transmisionService;



    @GetMapping(value="/actividades_uap")
    public String actividades_uap(Model model, HttpServletRequest request){
        
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            UnidadFuncional u = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cale = cal2;
            cale.add(Calendar.DATE, +6); // domingo de la siguiente semana
    
            Date fechaLunes = cal.getTime();//fecha lunes pasado
            Date fechaDomingo = cale.getTime();//fecha domingo pasado

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

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("nivel", u.getNivelFuncional());

            model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));

            model.addAttribute("listaActividadesMiUnidadActual", actividadService
                        .listaActividadPorSemanaAllUnidades(fechaLunesSemanaActual, fechaDomingoSemanaActual));
                model.addAttribute("listaActividadesMiUnidadPasado", actividadService
                        .listaActividadPorSemanaAllUnidades(fechaLunesSemanaPasado, fechaDomingoSemanaPasado));
                model.addAttribute("listaActividadesMiUnidadFuturo",
                        actividadService.listaActividadPorSemanaAllUnidades(fechaLunes, fechaDomingo));
        return "transmision/actividadesuap";

        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/agendarTransmision")
    public String agendarTransmision(@RequestParam(value = "id_activida") Long id_actividad,
            RedirectAttributes redirectAttrs){
            Actividad actividad = actividadService.findOne(id_actividad);
            Transmision transmision = new Transmision();
            transmision.setActividad(actividad);
            transmision.setEstado("pendiente");
            transmisionService.save(transmision);

            redirectAttrs
                .addFlashAttribute("mensaje", "Actividad agendada para transmision.")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/actividades_uap";
    }

    @GetMapping(value="/transmision")
    public String transmision(Model model, HttpServletRequest request){

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            UnidadFuncional u = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cale = cal2;
            cale.add(Calendar.DATE, +6); // domingo de la siguiente semana
    
            Date fechaLunes = cal.getTime();//fecha lunes pasado
            Date fechaDomingo = cale.getTime();//fecha domingo pasado

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

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("nivel", u.getNivelFuncional());

            model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));

            model.addAttribute("listaActividadesActual", transmisionService.findAll()); // actividadService.listaActividadPorSemanaAllUnidades(fechaLunesSemanaActual, fechaDomingoSemanaActual));
            model.addAttribute("listaActividadesPasado", transmisionService.findAll()); // actividadService.listaActividadPorSemanaAllUnidades(fechaLunesSemanaPasado, fechaDomingoSemanaPasado));
            model.addAttribute("listaActividadesFuturo", transmisionService.findAll()); // actividadService.listaActividadPorSemanaAllUnidades(fechaLunes, fechaDomingo));
            
            return "transmision/transmision";

        } else {
            return "redirect:/login";
        }
    }

}
