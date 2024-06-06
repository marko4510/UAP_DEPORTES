package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.EstadoActividad;

public interface IEstadoActividadService {
    
    public List<EstadoActividad> findAll();

	public void save(EstadoActividad estadoActividad);

	public EstadoActividad findOne(Long id_e);

	public void delete(Long id);
}
