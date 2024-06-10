package com.uap.planificacion.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.Lugar;
import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IDireccionFuncionalService;
import com.uap.planificacion.model.service.ILugarService;
import com.uap.planificacion.model.service.INivelFuncionalService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

@Controller
public class LugarController {
    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;

    @Autowired
    private INivelFuncionalService nivelFuncionalService;

    @Autowired
    private ILugarService lugarService;

       //FUNCION PARA REGISTRAR UNIDADES FUNCIONALES
	@RequestMapping(value = "/LugarR", method = RequestMethod.GET) // Pagina principal
	public String LugarR(HttpServletRequest request, Model model) {
        if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            
                
            model.addAttribute("lugar", new Lugar());
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
			model.addAttribute("unidadFuncional", new UnidadFuncional());
            model.addAttribute("lugares", lugarService.findAll());
 
			return "lugar/registrar";
		}
		else{
            return "redirect:/login";
        }
	}


     //FUNCION PARA GUARDAR UNIDADES FUNCIONALES
	
	 @PostMapping(value = "/LugarF")
	 public String LugarF(@Validated Lugar lugar, Model model,RedirectAttributes redirectAttrs) {
 
		 lugar.setEstado_lugar("A");
         lugar.setTipo_lugar("E");
		 lugarService.save(lugar);
		 redirectAttrs
		 .addFlashAttribute("mensaje", "Registro Exitoso de la Unidad Funcional")
		 .addFlashAttribute("clase", "success alert-dismissible fade show");
		 return "redirect:/LugarR";
 
	 }

      @RequestMapping(value= "editar-lugarf/{id_lugar}")
	public String editar_lugarf(@PathVariable("id_lugar") Long id_lugar, Model model, HttpServletRequest request) {
		if(request.getSession().getAttribute("personalAdministrativo")!=null){
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo)request.getSession().getAttribute("personalAdministrativo");
            Lugar lugar = lugarService.findOne(id_lugar);
                  
            model.addAttribute("lugar", lugar);
			UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
	

        model.addAttribute("lugar", lugar);
        model.addAttribute("lugares", lugarService.findAll());

		model.addAttribute("edit", "true");
		return "lugar/registrar";
		}
		else{
            return "redirect:/login";
        }
	}

    @PostMapping(value = "/LugarmodF")
	 public String LugarmodF(@Validated Lugar lugar, Model model) {
 
		 
        lugarService.save(lugar);
 
		 return "redirect:/LugarR";
 
	 }
}
