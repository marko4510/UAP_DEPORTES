package com.uap.planificacion.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IDireccionFuncionalService;
import com.uap.planificacion.model.service.IPersonaService;
import com.uap.planificacion.model.service.IPersonalAdministrativoService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

@Controller
public class PersonaController {
    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPersonalAdministrativoService administrativoService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;

    @Autowired
    private IDireccionFuncionalService direccionFuncionalService;

   @GetMapping(value = "/Persona")
    public String persona(HttpServletRequest request, Model model) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
            model.addAttribute("persona", new Persona());
            model.addAttribute("personas", personaService.findAll());
            model.addAttribute("personaAdministrativas", administrativoService.findAll());
            return "persona/RegistrarPersona";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/PersonaF")
    public String personaF(@Validated Persona persona, Model model, RedirectAttributes redirectAttrs) {

        List<Persona> personas = personaService.findAll();
        boolean salida = false;
         for (Persona p : personas) {
            if(persona.getCi().equals(p.getCi()) && p.getEstado()!="X"){
                salida = true;
            }
         }
        if(salida==true){
            redirectAttrs
                .addFlashAttribute("mensaje", "La persona existe en la base de datos")
                .addFlashAttribute("clase", "warning alert-dismissible fade show");
        return "redirect:/Persona";
        }else{
        persona.setEstado("A");
        personaService.save(persona);
        redirectAttrs
                .addFlashAttribute("mensaje", "Registro Exitoso de la Persona")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/Persona";
        }
    }

    @GetMapping(value = "/PersonaL")
    public String personaL(Model model) {

        model.addAttribute("personas", personaService.findAll());
        return "persona/listarPersona";
    }

    @RequestMapping(value = "editar-persona/{id_persona}")
    public String editar_persona(@PathVariable("id_persona") Long id_persona, Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            model.addAttribute("nivel", u.getNivelFuncional());
            Persona persona = personaService.findOne(id_persona);

            model.addAttribute("persona", persona);
            model.addAttribute("personas", personaService.findAll());
            model.addAttribute("personaAdministrativas", administrativoService.findAll());

            return "persona/RegistrarPersona";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/eliminar-persona/{id_persona}")
    public String Eliminar_persona(@PathVariable("id_persona") Long id_persona) {

        Persona persona = personaService.findOne(id_persona);

        persona.setEstado("X");

        personaService.save(persona);

        return "redirect:/Persona";

    }
    // ===================FIN PERSONA===================//

    // ===================INICIO PERSONA ADMINISTRATIVA===================//
    @GetMapping(value = "/Credencial")
    public String credencial(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession().getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

        model.addAttribute("unidad", unidadFuncional);
        model.addAttribute("personalADM", personalAdministrativo);
        UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
        model.addAttribute("nivel", u.getNivelFuncional());
        model.addAttribute("personaAdministrativo", new PersonalAdministrativo());
        model.addAttribute("personalAdministrativas", administrativoService.findAll());
        model.addAttribute("personas", personaService.findAll());
        model.addAttribute("unidadFuncionales", unidadFuncionalService.findAll());
        model.addAttribute("direccionFuncionales", direccionFuncionalService.findAll());
        return "persona/RegistrarCredencial";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/CredencialF")
    public String credencialF(@Validated PersonalAdministrativo personalAdministrativo, Model model,
            RedirectAttributes redirectAttrs) {
                
        
        
        personalAdministrativo.setEstado("A");
        administrativoService.save(personalAdministrativo);
        redirectAttrs
                .addFlashAttribute("mensaje", "Registro Exitoso del Usuario")
                .addFlashAttribute("clase", "success alert-dismissible fade show");
        return "redirect:/Credencial";

    }

    @GetMapping(value = "/CredencialL")
    public String credencialL(Model model) {
        

        model.addAttribute("personalAdministrativo", administrativoService.findAll());
        return "Persona/listarCredencial";
    }

    @RequestMapping(value = "editar-credencial/{id_personal_administrativo}")
    public String editar_personalAdministrativo(HttpServletRequest request,
            @PathVariable("id_personal_administrativo") Long id_personal_administrativo, Model model) {
        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            UnidadFuncional u= unidadFuncionalService.findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
                model.addAttribute("nivel", u.getNivelFuncional());
            PersonalAdministrativo personaAdm = administrativoService.findOne(id_personal_administrativo);

            model.addAttribute("personaAdministrativo", personaAdm);
            model.addAttribute("personalAdministrativas", administrativoService.findAll());
            model.addAttribute("personas", personaService.findAll());
            model.addAttribute("unidadFuncionales", unidadFuncionalService.findAll());

            return "persona/RegistrarCredencial";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/eliminar-credencial/{id_personal_administrativo}")
    public String Eliminar_personalAdministrativo(
            @PathVariable("id_personal_administrativo") Long id_personal_administrativo) {

        PersonalAdministrativo personaAdm = administrativoService.findOne(id_personal_administrativo);

        personaAdm.setEstado("X");

        administrativoService.save(personaAdm);

        return "redirect:/Credencial";

    }
    // ===================FIN PERSONA ADMINISTRATIVA===================//

}
