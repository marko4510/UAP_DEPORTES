package com.uap.planificacion.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.entity.Evento;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IActividadService;
import com.uap.planificacion.model.service.IDetalleActividadService;
import com.uap.planificacion.model.service.IEventoService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.ISubDetalleActividadService;
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

     @GetMapping("/reservas")
    public String vistaCalendario(Model model, HttpServletRequest request){
       
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
         
            
            model.addAttribute("lugaresE", lugarService.sacarLugaresConTipoE("E"));
            model.addAttribute("eventosSolicitados", events);

        return "publico/calendario";
        

    }
    
}
