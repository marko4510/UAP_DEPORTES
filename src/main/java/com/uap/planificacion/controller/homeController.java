package com.uap.planificacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.service.IPersonaService;
import com.uap.planificacion.model.service.IPersonalAdministrativoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class homeController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPersonalAdministrativoService personalAdministrativoService;
    
    @GetMapping(value={"/","/login"})
    public String login(){
        return "login";
    }

    @PostMapping(value="/login")
    public String login(@RequestParam("cod_f") Integer cod_f, @RequestParam("ci") String ci, HttpServletRequest request){

        Persona persona = personaService.getPersonaCi(ci);
        PersonalAdministrativo personalAdministrativo = personalAdministrativoService.getPersonalAdministrativoCod(cod_f);

        if (persona != null && personalAdministrativo != null) {

            HttpSession session = request.getSession(false);
            session = request.getSession(true);
            session.setAttribute("persona", persona);
            session.setAttribute("personalAdministrativo", personalAdministrativo);
            /*HistorialLogin historialLogin = new HistorialLogin();
            historialLogin.setFecha_ingreso(new Date());
            historialLogin.setPersonalAdministrativo(personalAdministrativo);
            historialLoginService.guardarHistorialLogin(historialLogin);*/
            return "redirect:/actividadR";
        } else {
            return "redirect:/login";
        }

    }

    @RequestMapping("/cerrar_sesion")
	public String cerrarSesion(HttpServletRequest request, RedirectAttributes flash) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
			flash.addAttribute("validado", "Se cerro sesion con exito!");
		}
		return "redirect:/login";
	}
}
