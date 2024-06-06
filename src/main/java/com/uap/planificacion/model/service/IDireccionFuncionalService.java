package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.DireccionFuncional;

public interface IDireccionFuncionalService {
    
    public List<DireccionFuncional> findAll();

	public void save(DireccionFuncional entidad);

	public DireccionFuncional findOne(Long id);

	public void delete(Long id);
}
