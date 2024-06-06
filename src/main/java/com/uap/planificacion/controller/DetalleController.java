package com.uap.planificacion.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

@Controller
public class DetalleController {
    @Autowired
    private IActividadService actividadService;
    @Autowired
    private IUnidadFuncionalService unidadService;

    @GetMapping(value = "/estadoUnidades")
    public String mostrarUnidadesExistentes(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("nivel", u.getNivelFuncional());

            Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
        cal3.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana

        //Date fechaLunes = cal.getTime();
        Calendar cale = cal2;
        Calendar cale10 = cal3;
        
        //cale10.setTime(fechaLunes);
        cale.add(Calendar.DATE, +6); //domingo de la siguiente semana
        cale10.add(Calendar.DATE, -10);//10 dias antes de de la fecha de registros de actividades

        Date fecha10diasatrasdelunes = cale10.getTime();
        Date fechaLunes = cal.getTime();
        Date fechaDomingo = cale.getTime();

        //fecha manual para esta semana
        String lunes = "14-08-2023";
        String domin = "20-08-2023";
        
        DateTimeFormatter formatterl2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate l= LocalDate.parse(lunes, formatterl2);
        LocalDate d= LocalDate.parse(domin, formatterl2);

        Date lun = java.sql.Date.valueOf(l);
        Date dom = java.sql.Date.valueOf(d);
        System.out.println(lun+"---"+dom);

        System.out.println(cale10.getTime()+" 10 dias antes de fecha del lunes cercano"); // muestra la fecha del lunes cercano de la semana siguiente pero desde 10 dias antes
        System.out.println(cal.getTime()+" fecha del lunes cercano"); // muestra la fecha del lunes cercano de la semana siguiente
        System.out.println(cale.getTime()+" fecha del domingo de la sig semana"); // muestra la fecha del lunes cercano de la semana siguiente

        List<UnidadFuncional> sinactividad=unidadService.listaUnidadesSinActividad(fechaLunes, fechaDomingo);
        System.out.println(sinactividad.size()+" unidades sin actividad");
        List<UnidadFuncional> conactividad=unidadService.listaUnidadesConActividad(fechaLunes, fechaDomingo);
        System.out.println(conactividad.size()+" unidades con actividad");


        //CONVIRTIENTO FECHA DE LUNES Y DOMINGO EN STRING Y MOSTRANDO EN TITLE
        LocalDate fechaLocalDatel = fechaLunes.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterl = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaStringl = fechaLocalDatel.format(formatterl);
        LocalDate fechaLocalDated = fechaDomingo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterd = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaStringd = fechaLocalDated.format(formatterd);

        model.addAttribute("unidadSinActividad", sinactividad);
        model.addAttribute("unidadConActividad", conactividad);
        model.addAttribute("unidadAll", unidadService.listaTodasLasUnidades());
        model.addAttribute("title", "SISTEMA SIASE   DE "+fechaStringl+" A "+fechaStringd);
        model.addAttribute("edit", "true");
            return "estadoUnidades";
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/detalleT")
    public String detalle(Model model, HttpServletRequest request){

        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            UnidadFuncional u= unidadService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("nivel", u.getNivelFuncional());
            return "detalle";
        }
        else{
            return "redirect:/login";
        }
    }
}
