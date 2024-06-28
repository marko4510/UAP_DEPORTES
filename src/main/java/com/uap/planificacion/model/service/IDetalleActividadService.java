package com.uap.planificacion.model.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.uap.planificacion.model.entity.DetalleActividad;

public interface IDetalleActividadService {
    public List<DetalleActividad> findAll();

	public void save(DetalleActividad entidad);

	public DetalleActividad findOne(Long id);

	public void delete(Long id);

	List<DetalleActividad> detallePorIdActividad(Long id_actividad);

	List<DetalleActividad> findActividadesBetweenDates(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

	public List<Object[]> reporteGeneral(Date fecha_inicio, Date fecha_final);

	public List<Object[]> reportePorInstalacion(Date fecha_inicio, Date fecha_final, Long id_lugar);

	public DetalleActividad detalleActividadPorIdActividad(Long id_actividad);
}
