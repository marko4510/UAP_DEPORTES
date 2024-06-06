package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Afluencia;

public interface IAfluenciaService {
    
    public List<Afluencia> findAll();

	public void save(Afluencia entidad);

	public Afluencia findOne(Long id);

	public void delete(Long id);

}
