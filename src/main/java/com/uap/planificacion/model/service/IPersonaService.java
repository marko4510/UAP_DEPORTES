package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Persona;

public interface IPersonaService {
    
    public List<Persona> findAll();

	public void save(Persona entidad);

	public Persona findOne(Long id);

	public void delete(Long id);

	public Persona getPersonaCi(String ci);

}
