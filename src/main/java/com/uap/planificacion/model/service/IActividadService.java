package com.uap.planificacion.model.service;

import java.util.Date;
import java.util.List;

import com.uap.planificacion.model.entity.Actividad;

public interface IActividadService {
    
    public List<Actividad> findAllOrderIdDesc();

	public List<Actividad> findAllActividadesThen(Date fechaAnterior, Date dechaActual);

	public List<Actividad> findByIdActividadesThen(Long id_actividad, Date fechaAnterior, Date dechaActual);
	
	public List<Actividad> findAll();

	public void save(Actividad actividad);

	public Actividad findOne(Long id);

	public void delete(Long id);

	public List<Actividad> listaActividadesPorUnidad(Long id_unidad);
	public List<Actividad> listaActividadesPorUnidadYRango(Long id_unidad, Date fechaAnterior, Date fechaActual);
	
	public List<Actividad> listaActividadPorSemanaUnidad(Long id_unidad, Date fechaLunes, Date fechaDomingo);
	public List<Actividad> listaActividadPorSemanaDireccion(Long id_direccion, Date fechaLunes, Date fechaDomingo);
	public List<Actividad> listaActividadPorSemanaAllUnidades(Date fechaLunes, Date fechaDomingo);

	public List<Actividad> listaActividadesPorDireccionYRango(Long id_direccion, Date fechaAnterior, Date fechaActual);


	public List<Actividad> listaActividadesPorMesAnio(int mes, int anio);

	public List<Actividad> listaActividadesPorDireccion(Long id_direccion);
	public List<Actividad> listaActividadesPorUnidadMesAnio(Long id_unidad, int mes, int anio);
	public List<Actividad> listaActividadesPorDireccionMesAnio(Long id_direccion, int mes, int anio);
	public List<Actividad> listaActividadesImpactoPorFecha(Date fechaPorDia);

}
