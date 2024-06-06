package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.DetalleActividad;

public interface IDetalleActividadService {
    public List<DetalleActividad> findAll();

	public void save(DetalleActividad entidad);

	public DetalleActividad findOne(Long id);

	public void delete(Long id);

	List<DetalleActividad> detallePorIdActividad(Long id_actividad);
}
