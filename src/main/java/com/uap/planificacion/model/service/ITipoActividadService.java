package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.TipoActividad;

public interface ITipoActividadService {
    
    public List<TipoActividad> findAll();

	public void save(TipoActividad entidad);

	public TipoActividad findOne(Long id);

	public void delete(Long id);

}
