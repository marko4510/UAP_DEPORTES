package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Evaluacion;

public interface IEvaluacionService {
    
    public List<Evaluacion> findAll();

	public void save(Evaluacion entidad);

	public Evaluacion findOne(Long id);

	public void delete(Long id);
    
}
