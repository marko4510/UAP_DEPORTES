package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Lugar;

public interface ILugarService {
    
    public List<Lugar> findAll();

	public void save(Lugar actividad);

	public Lugar findOne(Long id);

	public void delete(Long id);

	public List<Lugar> sacarLugaresPorProgramacion(Long idProgramacion);
	public List<Lugar> sacarLugaresConTipoE(String tipoLugar);

	
}
