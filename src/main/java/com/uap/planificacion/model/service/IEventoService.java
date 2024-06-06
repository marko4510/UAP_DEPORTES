package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Evento;

public interface IEventoService {
    
     
    public List<Evento> findAll();

	public void save(Evento entidad);

	public Evento findOne(Long id);

	public void delete(Long id);

	public List<Evento> listaEventosSolicitados();

}
