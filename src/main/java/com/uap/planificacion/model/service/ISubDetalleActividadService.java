package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.SubDetalleActividad;

public interface ISubDetalleActividadService {
    
    public List<SubDetalleActividad> findAll();

	public void save(SubDetalleActividad entidad);

	public SubDetalleActividad findOne(Long id);

	public void delete(Long id);

	List<SubDetalleActividad> subDetallePorIdDetalles(Long id_sub_detalle);

	List<SubDetalleActividad> findAllEspecialesAndMesDeAnioActual(Integer mes);
}
