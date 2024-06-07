package com.uap.planificacion.controller;

import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.DetalleActividad;
import com.uap.planificacion.model.entity.Evento;
import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.SubDetalleActividad;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.IDetalleActividadService;
import com.uap.planificacion.model.service.IEventoService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.ISubDetalleActividadService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.PostLoad;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CalendarioController {
    private final IUnidadFuncionalService unidadService;
    private final IEventoService eventoService;
    private final IDetalleActividadService detalleActividadService;
    private final ISubDetalleActividadService subDetalleActividadService;
    private final ILugarService lugarService;
    private IEventoDao eventoDao;
    private final IActividadService actividadService;
    
    @GetMapping("/eventos")
    public String vistaCalendario(Model model, HttpServletRequest request){
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            UnidadFuncional u = unidadService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            String lugar1="";
            String lugar2="";
            String lugar3="";
            String lugar4="";
            // String lugar5="";
            // String lugar6="";
            // String lugar7="";
            List<Evento> events = new ArrayList<>();
            for (Evento evento : eventoService.listaEventosSolicitados()) {
              
                events.add(evento);  
                    lugar1="CANCHA LA GUARIDA DEL JAGUAR";
                    lugar2="COLISEO CERRADO";
                    lugar3="COLISEO POLIFUNCIONAL";
                    lugar4="CANCHA DEL FRONTON CAMPUS";
                
            
            
                
            }
            model.addAttribute("lugar1", lugar1);
            model.addAttribute("lugar2", lugar2);
            model.addAttribute("lugar3", lugar3);
            model.addAttribute("lugar4", lugar4);
            // model.addAttribute("lugar5", lugar5);
            // model.addAttribute("lugar6", lugar6);
            // model.addAttribute("lugar7", lugar7);
            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("lugaresE", lugarService.sacarLugaresConTipoE("E"));
            model.addAttribute("eventosSolicitados", events);

        return "eventos/calendario";
        } else {
            return "redirect:/login";
        }

    }

    @PostMapping("/confirmarEvento")
    public String confirmarEvento(@RequestParam(value="id_activida")Long id_evento, RedirectAttributes redirectAttrs){

        Evento evento = eventoService.findOne(id_evento);
        evento.setEstado_evento("C");
        eventoService.save(evento);
        Actividad a = evento.getActividad();
        a.setAvance_actividad("aceptado");
        actividadService.save(a);
        redirectAttrs
        .addFlashAttribute("mensaje", "EVENTO CONFIRMADO CORRECTAMENTE!")
        .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/eventos";
    }
    @PostMapping("/cambiarFechaEventoLugar")
    public String cambiarFechaEventoLugar(@RequestParam(value = "fechaActividad") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fecha_detalle_actividad,
    @RequestParam(value="idDetalleActividad")Long id_detalle_actividad, @RequestParam(value = "ilugaresA")Long id_lugar, 
    @RequestParam(value = "idSubDetAct")Long id_sub_detalle_actividad,
    @RequestParam(value = "horaInicio") @DateTimeFormat(pattern = "HH:mm") Date hora_inicio,
    @RequestParam(value = "horaFin") @DateTimeFormat(pattern = "HH:mm") Date hora_fin, 
     RedirectAttributes redirectAttrs){
        DetalleActividad detalleActividad = detalleActividadService.findOne(id_detalle_actividad);
        System.out.println("hora inicio "+hora_inicio);
        System.out.println("hora fin "+hora_fin);
        Lugar lugar = lugarService.findOne(id_lugar);
        System.out.println("lugar anterior "+lugar.getNombre_lugar());
        Set<Lugar> lugares = new HashSet<>();
        lugares.add(lugar);
        SubDetalleActividad subDetalleActividad = subDetalleActividadService.findOne(id_sub_detalle_actividad);
        subDetalleActividad.setHora_inicio(hora_inicio);
        subDetalleActividad.setHora_final(hora_fin);
        subDetalleActividad.setLugares(lugares);
        subDetalleActividadService.save(subDetalleActividad);

        Evento et = eventoService.findOne(detalleActividad.getActividad().getEventos().get(0).getId_evento());
        //Evento evento = eventoDao.sacarEventoPorIdLugarIdActivdad(detalleActividad.getActividad().getId_actividad(), id_lugar);
        System.out.println("lugar actual "+lugar.getId_lugar()+" lugar a modificar "+id_lugar+" id actividad "+detalleActividad.getActividad().getId_actividad());
        System.out.println(et.getId_evento()+"entrando a modificar ");
        et.setLugar(lugar);
        et.setEstado_evento("C");
        et.setActividad(detalleActividad.getActividad());
        eventoService.save(et);

        detalleActividad.setFecha_detalle_actividad(fecha_detalle_actividad);
        detalleActividadService.save(detalleActividad);
    
        Actividad actividad = actividadService.findOne(detalleActividad.getActividad().getId_actividad());
        
        actividadService.save(actividad);

        redirectAttrs
        .addFlashAttribute("mensaje", "EVENTO ACTUALIZADO CORRECTAMENTE!")
        .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/eventos";
    }

    @ResponseBody
    @GetMapping("/api/eventos-especiales/{mes}")
    public ResponseEntity<?>  listarEventosPorTipoLugar(@PathVariable Integer mes){
        try{
            return ResponseEntity.ok().body(subDetalleActividadService.findAllEspecialesAndMesDeAnioActual(mes));
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
