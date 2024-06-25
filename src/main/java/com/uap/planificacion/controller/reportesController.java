package com.uap.planificacion.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.service.IDetalleActividadService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.IPersonaService;
import com.uap.planificacion.model.service.IPersonalAdministrativoService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;



@Controller
public class reportesController {
    
    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPersonalAdministrativoService personalAdministrativoService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;

    @Autowired
    private ILugarService lugarService;

     @Autowired
    private IDetalleActividadService detalleActividadService;

     @GetMapping(value="/reportes")
    public String reportes(Model model, HttpServletRequest request){

        if (request.getSession().getAttribute("personalAdministrativo") != null) {

            HttpSession session = request.getSession(false);
            session = request.getSession(true);
            model.addAttribute("lugares", lugarService.findAll());
           
            return "reportes/inicio";
        } else {
            return "redirect:/login";
        }

    }

     @PostMapping(value = "/reporteGeneral")
    public String reporteGeneral(Model model,
            @RequestParam(value = "fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_ini,
            @RequestParam(value = "fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin) {
                int totalCosto = 0;
                List<Object[]> listaDetalles = detalleActividadService.reporteGeneral(fecha_ini, fecha_fin);
                for (Object[] objects : listaDetalles) {
                    if (objects[7] != null) {
                        int costo = (int) objects[7];
                        totalCosto += costo;
                    }

                }
                model.addAttribute("detalles", listaDetalles);
                model.addAttribute("totalCosto", totalCosto);


                return "reportes/general";
            }

            @PostMapping(value = "/reporteInstalacion")
            public String reporteInstalacion(Model model,
                    @RequestParam(value = "fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_ini,
                    @RequestParam(value = "fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha_fin,
                    @RequestParam(value = "lugar") Long id_lugar) {
                        int totalCosto = 0;
                        List<Object[]> listaDetalles = detalleActividadService.reportePorInstalacion(fecha_ini, fecha_fin, id_lugar);
                        for (Object[] objects : listaDetalles) {
                            int costo = (int) objects[7]; 
                            if (costo != 0) {
                                totalCosto += costo;
                            }
        
                        }
                        model.addAttribute("detalles", listaDetalles);
                        model.addAttribute("totalCosto", totalCosto);
        
        
                        return "reportes/general";
                    }

}
