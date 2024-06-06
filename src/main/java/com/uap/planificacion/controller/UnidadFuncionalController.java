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
import com.uap.planificacion.model.service.INivelFuncionalService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UnidadFuncionalController {
    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;

    @Autowired
    private INivelFuncionalService nivelFuncionalService;

       //FUNCION PARA REGISTRAR UNIDADES FUNCIONALES
	@RequestMapping(value = "/UnidadR", method = RequestMethod.GET) // Pagina principal
	public String UnidadR(HttpServletRequest request, Model model) {
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                
            model.addAttribute("unidad", unidadFuncional);
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
			model.addAttribute("unidadFuncional", new UnidadFuncional());
            model.addAttribute("unidadedesFuncionales", unidadFuncionalService.findAll());
            model.addAttribute("direccionesFuncionales", direccionFuncionalService.findAll());
            model.addAttribute("nivelesFuncionales", nivelFuncionalService.findAll());
			return "unidadFuncional/registrar";
		}
		else{
            return "redirect:/login";
        }
	}


    //FUNCION PARA GUARDAR UNIDADES FUNCIONALES
	
	 @PostMapping(value = "/UnidadF")
	 public String DireccionF(@Validated UnidadFuncional unidadFuncional, Model model,RedirectAttributes redirectAttrs) {
 
		 unidadFuncional.setEstado("A");
		 unidadFuncionalService.save(unidadFuncional);
		 redirectAttrs
		 .addFlashAttribute("mensaje", "Registro Exitoso de la Unidad Funcional")
		 .addFlashAttribute("clase", "success alert-dismissible fade show");
		 return "redirect:/UnidadR";
 
	 }

	 


	 @RequestMapping(value= "editar-unidadf/{id_unidad_funcional}")
	public String editar_unidadf(@PathVariable("id_unidad_funcional") Long id_unidad_funcional, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                
            model.addAttribute("unidad", unidadFuncional);
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
		UnidadFuncional unidadFuncional1 = unidadFuncionalService.findOne(id_unidad_funcional);

        model.addAttribute("unidadFuncional", unidadFuncional1);
		model.addAttribute("unidadedesFuncionales", unidadFuncionalService.findAll());
        model.addAttribute("direccionesFuncionales", direccionFuncionalService.findAll());
        model.addAttribute("nivelesFuncionales", nivelFuncionalService.findAll());
		model.addAttribute("edit", "true");
		return "unidadFuncional/registrar";
		}
		else{
            return "redirect:/login";
        }
	}
	@PostMapping(value = "/UnidadmodF")
	 public String UnidadmodF(@Validated UnidadFuncional unidadFuncional, Model model) {
 
		 
		 unidadFuncionalService.save(unidadFuncional);
 
		 return "redirect:/UnidadR";
 
	 }


	 @RequestMapping(value = "/eliminar-unidad/{id_unidad_funcional}")
	 public String Eliminar_unidad(@PathVariable("id_unidad_funcional") Long id_unidad_funcional, Model model) {
 
		UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(id_unidad_funcional);
		model.addAttribute("unidadFuncional", unidadFuncional);
		unidadFuncional.setEstado("X");
 
		 unidadFuncionalService.save(unidadFuncional);
 
		 return "redirect:/UnidadR";
 
	 }
 

}