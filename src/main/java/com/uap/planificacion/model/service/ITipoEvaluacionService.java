package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.TipoEvaluacion;

public interface ITipoEvaluacionService {
    
    public List<TipoEvaluacion> findAll();

	public void save(TipoEvaluacion entidad);

	public TipoEvaluacion findOne(Long id);

	public void delete(Long id);

}
