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
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IDireccionFuncionalService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class DireccionFuncionalController {
    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;

	@Autowired
    private IUnidadFuncionalService unidadFuncionalService;
	
     //FUNCION PARA REGISTRAR DIRECCIONES FUNCIONALES
	@RequestMapping(value = "/DireccionR", method = RequestMethod.GET) // Pagina principal
	public String DireccionR(HttpServletRequest request, Model model) {
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                
            model.addAttribute("unidad", unidadFuncional);
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
			model.addAttribute("direccionFuncional", new DireccionFuncional());
            model.addAttribute("direccionesFuncionales", direccionFuncionalService.findAll());
			model.addAttribute("unidades", unidadFuncionalService.findAll());
			
			return "direccionFuncional/registrar";
		}
		else{
            return "redirect:/login";
        }
	}


    //FUNCION PARA GUARDAR DIRECCIONES FUNCIONALES
	@PostMapping(value = "/DireccionF")
    public String personaF(@Validated DireccionFuncional direccionFuncional, Model model,RedirectAttributes redirectAttrs) {

        direccionFuncional.setEstado("A");
        direccionFuncionalService.save(direccionFuncional);
		redirectAttrs
                .addFlashAttribute("mensaje", "Registro Exitoso de la Direccion Funcional")
                .addFlashAttribute("clase", "success alert-dismissible fade show");

        return "redirect:/DireccionR";

    }



	@RequestMapping(value= "editar-direccionf/{id_direccion_funcional}")
	public String editar_direccionf(@PathVariable("id_direccion_funcional") Long id_direccion_funcional, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                
            model.addAttribute("unidad", unidadFuncional);
		DireccionFuncional direccionFuncional = direccionFuncionalService.findOne(id_direccion_funcional);
		UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
		model.addAttribute("nivel", u.getNivelFuncional());
        model.addAttribute("direccionFuncional", direccionFuncional);
        model.addAttribute("direccionesFuncionales", direccionFuncionalService.findAll());
		model.addAttribute("unidades", unidadFuncionalService.findAll());
        model.addAttribute("edit", "true");

		return "direccionFuncional/registrar";
		}
		else{
            return "redirect:/login";
        }
	}


    @PostMapping(value = "/DireccionmodF")
	 public String UnidadmodF(@Validated DireccionFuncional direccionFuncional, Model model) {
 
		 
		 direccionFuncionalService.save(direccionFuncional);
 
		 return "redirect:/DireccionR";
 
	 }

	 @RequestMapping(value = "/eliminar-direccion/{id_direccion_funcional}")
	 public String Eliminar_direccion(@PathVariable("id_direccion_funcional") Long id_direccion_funcional, Model model) {
 
		DireccionFuncional direccionFuncional = direccionFuncionalService.findOne(id_direccion_funcional);
		model.addAttribute("direccionFuncional", direccionFuncional);
		direccionFuncional.setEstado("X");
 
		 direccionFuncionalService.save(direccionFuncional);
 
		 return "redirect:/DireccionR";
 
	 }
}
