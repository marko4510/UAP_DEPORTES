package com.uap.planificacion.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.DireccionFuncional;
import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.TipoActividad;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IDireccionFuncionalService;
import com.uap.planificacion.model.service.INivelFuncionalService;
import com.uap.planificacion.model.service.ITipoActividadService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TipoReservaController {
    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;

    @Autowired
    private INivelFuncionalService nivelFuncionalService;

	@Autowired
    private ITipoActividadService tipoActividadService;

	      //FUNCION PARA REGISTRAR UNIDADES FUNCIONALES
	@RequestMapping(value = "/TipoReservaR", method = RequestMethod.GET) // Pagina principal
	public String TipoReservaR(HttpServletRequest request, Model model) {
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            
                
            model.addAttribute("tipoR", new TipoActividad());
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());

            model.addAttribute("tiposR", tipoActividadService.findAll());
 
			return "tiporeserva/registrar";
		}
		else{
            return "redirect:/login";
        }
	}


     //FUNCION PARA GUARDAR UNIDADES FUNCIONALES
	
	 @PostMapping(value = "/TipoReservaF")
	 public String LugarF(@Validated TipoActividad tipoActividad, Model model,RedirectAttributes redirectAttrs) {
		tipoActividad.setEstado_tipo_actividad("A");
	
		 tipoActividadService.save(tipoActividad);
		 redirectAttrs
		 .addFlashAttribute("mensaje", "Registro Exitoso del Tipo de Reserva")
		 .addFlashAttribute("clase", "success alert-dismissible fade show");
		 return "redirect:/TipoReservaR";
 
	 }

      @RequestMapping(value= "editar-tiporeservaf/{id_tipo_actividad}")
	public String editar_tiporeservaf(@PathVariable("id_tipo_actividad") Long id_tipo_actividad, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
  
            TipoActividad tipoActividad = tipoActividadService.findOne(id_tipo_actividad);
            model.addAttribute("tipoR", tipoActividad);
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
	

      
				model.addAttribute("tiposR", tipoActividadService.findAll());
		model.addAttribute("edit", "true");
		return "tiporeserva/registrar";
		}
		else{
            return "redirect:/login";
        }
	}

    @PostMapping(value = "/TipoReservamodF")
	 public String TipoReservamodF(@Validated TipoActividad tipoActividad, Model model) {
 
		tipoActividad.setEstado_tipo_actividad("A");
    
		tipoActividadService.save(tipoActividad);
 
		 return "redirect:/TipoReservaR";
 
	 }

     @RequestMapping(value = "/eliminar-tiporeserva/{id_tipo_actividad}")
	 public String Eliminar_tiporeserva(@PathVariable("id_tipo_actividad") Long id_tipo_actividad, Model model) {
 
		TipoActividad tipoActividad = tipoActividadService.findOne(id_tipo_actividad);
		model.addAttribute("tipoActividad", tipoActividad);
		tipoActividad.setEstado_tipo_actividad("X");
 
		tipoActividadService.save(tipoActividad);
		 return "redirect:/TipoReservaR";
 
	 }
 

}