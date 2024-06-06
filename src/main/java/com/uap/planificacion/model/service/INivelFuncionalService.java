package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.NivelFuncional;

public interface INivelFuncionalService {
    
    public List<NivelFuncional> findAll();

	public void save(NivelFuncional entidad);

	public NivelFuncional findOne(Long id);

	public void delete(Long id);
    
}

