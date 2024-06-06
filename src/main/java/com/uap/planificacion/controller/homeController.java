package com.uap.planificacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.Evaluacion;
import com.uap.planificacion.model.entity.Persona;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import com.uap.planificacion.model.entity.Programacion;
import com.uap.planificacion.model.entity.UnidadFuncional;
import com.uap.planificacion.model.service.IPersonaService;
import com.uap.planificacion.model.service.IPersonalAdministrativoService;
import com.uap.planificacion.model.service.IUnidadFuncionalService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class homeController {

    @Autowired
    private IPersonaService personaService;

    @Autowired
    private IPersonalAdministrativoService personalAdministrativoService;

    @Autowired
    private IUnidadFuncionalService unidadFuncionalService;
    
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
            return "redirect:/homeR";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping(value = "/homeR")
    public String homeR(Model model, HttpServletRequest request) {

        if (request.getSession().getAttribute("personalAdministrativo") != null) {
            PersonalAdministrativo personalAdministrativo = (PersonalAdministrativo) request.getSession()
                    .getAttribute("personalAdministrativo");
            UnidadFuncional unidadFuncional = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());

            model.addAttribute("unidad", unidadFuncional);
            model.addAttribute("personalADM", personalAdministrativo);
            model.addAttribute("actividad", new Actividad());
            model.addAttribute("evaluacion", new Evaluacion());
            model.addAttribute("programacion", new Programacion());


            UnidadFuncional un = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            long sic = 0;
            // List<TipoEvaluacion> tevaluacion = new ArrayList<>();

            for (Actividad ac : unidadFuncional.getActividads()) {
                sic = (long) ac.getEvaluacions().size();

            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal2.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cal3 = Calendar.getInstance();
            cal3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // establece el día de la semana en lunes
            cal3.add(Calendar.WEEK_OF_YEAR, 1); // suma una semana
            Calendar cale = cal2;
            Calendar cale10 = cal3;

            // cale10.setTime(fechaLunes);
            cale.add(Calendar.DATE, +6); // domingo de la siguiente semana
            cale10.add(Calendar.DATE, -10);// 10 dias antes de de la fecha de registros de actividades
            Date fecha10diasatrasdelunes = cale10.getTime();
            Date fechaLunes = cal.getTime();
            Date fechaDomingo = cale.getTime();

            Calendar calendarDomingoActual = Calendar.getInstance();
            calendarDomingoActual.setTime(fechaLunes);
            calendarDomingoActual.add(Calendar.DAY_OF_MONTH, -1);
            Calendar calendarLunesActual = Calendar.getInstance();
            calendarLunesActual.setTime(fechaLunes);
            calendarLunesActual.add(Calendar.DAY_OF_MONTH, -7);
            Date fechaDomingoSemanaActual = calendarDomingoActual.getTime();
            Date fechaLunesSemanaActual = calendarLunesActual.getTime();

            Calendar calendarDomingoPasado = Calendar.getInstance();
            calendarDomingoPasado.setTime(fechaLunesSemanaActual);
            calendarDomingoPasado.add(Calendar.DAY_OF_MONTH, -1);
            Calendar calendarLunesPasado = Calendar.getInstance();
            calendarLunesPasado.setTime(fechaLunesSemanaActual);
            calendarLunesPasado.add(Calendar.DAY_OF_MONTH, -7);
            Date fechaDomingoSemanaPasado = calendarDomingoPasado.getTime();
            Date fechaLunesSemanaPasado = calendarLunesPasado.getTime();

            Calendar calendarDomingoNoRealizado = Calendar.getInstance();
            calendarDomingoNoRealizado.setTime(fechaLunesSemanaActual);
            calendarDomingoNoRealizado.add(Calendar.DAY_OF_MONTH, +5);
            Date fechaDomingoSemanasNoRealizado = calendarDomingoNoRealizado.getTime();
            // System.out.println(fechaDomingoSemanasNoRealizado);

            // codigo para sacar unidades q no evaluaron antes de la semana pasada
            /*
             * List<Actividad> actividadesSinRealizar = new ArrayList<>();
             * List<Actividad> actividades = actividadService.findAll();
             * for (Actividad actividad : actividades) {
             * List<DetalleActividad> das = actividad.getDetalleActividads();
             * 
             * for (DetalleActividad da : das) {
             * Date f = da.getFecha_detalle_actividad();
             * if (f.before(fechaDomingoSemanasNoRealizado)
             * && actividad.getEstadoActividad().getId_estado_actividad() == 1) {
             * System.out.println(actividad.getUnidadFuncional().getNom_unidad() + "-> "
             * +actividad.getId_actividad()+" => "+
             * actividad.getEstadoActividad().getNombre_estado_actividad() + " ==== "
             * + da.getFecha_detalle_actividad() + " === " +
             * actividad.getNombre_actividad());
             * actividadesSinRealizar.add(actividad);
             * }
             * }
             * }
             * for (Actividad actividad : actividadesSinRealizar) {
             * EstadoActividad estadoActividad = estadoActividadService.findOne(3l);
             * actividad.setEstadoActividad(estadoActividad);
             * actividadService.save(actividad);
             * }
             */
            // codigo para sacar unidades q no evaluaron antes de la semana pasada

            System.out.println(cale10.getTime() + " 10 dias antes de fecha del lunes cercano"); // muestra la fecha del
                                                                                                // lunes cercano de la
                                                                                                // semana siguiente pero
                                                                                                // desde 10 dias antes
            System.out.println(cal.getTime() + " fecha del lunes cercano"); // muestra la fecha del lunes cercano de la
                                                                            // semana siguiente
            System.out.println(cale.getTime() + " fecha del domingo de la sig semana"); // muestra la fecha del lunes
                                                                                        // cercano de la semana
                                                                                        // siguiente

            System.out.println(fechaDomingoSemanaActual + " fecha del domingo semana actual"); // muestra la fecha del
                                                                                               // domingo semana actual
            System.out.println(fechaLunesSemanaActual + " fecha del lunes semana actual"); // muestra la fecha del lunes
                                                                                           // semana actual

            System.out.println(fechaDomingoSemanaPasado + " fecha del domingo semana pasada"); // fecha del domingo
                                                                                               // semana pasada
            System.out.println(fechaLunesSemanaPasado + " fecha del lunes semana pasada"); // fecha del lunes semana
                                                                                           // pasada

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            model.addAttribute("fechalunespasado", sdf.format(fechaLunesSemanaPasado));
            model.addAttribute("fechadomingopasado", sdf.format(fechaDomingoSemanaPasado));
            model.addAttribute("fechalunesactual", sdf.format(fechaLunesSemanaActual));
            model.addAttribute("fechadomingoactual", sdf.format(fechaDomingoSemanaActual));
            model.addAttribute("fechalunesfuturo", sdf.format(fechaLunes));
            model.addAttribute("fechadomingofuturo", sdf.format(fechaDomingo));



            Date hoy = new Date();
            if (hoy.after(fechaLunes)) {
                // System.out.println("la fecha si es posterior a lunes siguiente ");
            } else {// System.out.println("lo sentimo, la fecha no es posterior a lunes siguiente
                    // ");
            }

            Calendar FechaActual = Calendar.getInstance();
            Date fechaHoy = FechaActual.getTime();
            FechaActual.add(Calendar.DATE, -20);// fecha para ver 10 dias actras de la fecha actual
            Date fechaPasada10 = FechaActual.getTime();

            System.out.println("FECHA DE HOY: " + fechaHoy + "  FECHA HACE 10 DÍAS:  " + fechaPasada10);
            System.out.println();

            model.addAttribute("cice", sic);
            UnidadFuncional u = unidadFuncionalService
                    .findOne(personalAdministrativo.getUnidadFuncional().getId_unidad_funcional());
            System.out.println(u.getNom_unidad() + "++++++++++++++++++++");
          
       

            model.addAttribute("nivel", u.getNivelFuncional());
            return "home/home";

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
