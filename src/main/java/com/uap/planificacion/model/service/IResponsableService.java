package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Responsable;

public interface IResponsableService {
    
    public List<Responsable> findAll();

	public void save(Responsable entidad);

	public Responsable findOne(Long id);

	public void delete(Long id);

}
