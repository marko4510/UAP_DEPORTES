package com.uap.planificacion.model.service;

import java.util.Date;
import java.util.List;

import com.uap.planificacion.model.entity.UnidadFuncional;

public interface IUnidadFuncionalService {
    public List<UnidadFuncional> findAll();

	public void save(UnidadFuncional unidadFuncional);

	public UnidadFuncional findOne(Long id);

	public void delete(Long id);

	public List<UnidadFuncional> listaUnidadesSinActividad(Date fechainicio, Date fechafin);
	
	public List<UnidadFuncional> listaUnidadesConActividad(Date fechainicio, Date fechafin);

	public List<UnidadFuncional> listaTodasLasUnidades();

	public UnidadFuncional buscarResponsable(String nombre);
}
