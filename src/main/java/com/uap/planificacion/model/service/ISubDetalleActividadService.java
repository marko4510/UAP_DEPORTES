package com.uap.planificacion.model.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.uap.planificacion.model.entity.SubDetalleActividad;

public interface ISubDetalleActividadService {
    
    public List<SubDetalleActividad> findAll();

	public void save(SubDetalleActividad entidad);

	public SubDetalleActividad findOne(Long id);

	public void delete(Long id);

	List<SubDetalleActividad> subDetallePorIdDetalles(Long id_sub_detalle);

	List<SubDetalleActividad> findAllEspecialesAndMesDeAnioActual(Integer mes);

	public Object validarHoraReservas(LocalDate fecha_reserva, LocalTime hora_inicio, LocalTime hora_final, String nombre_lugar );
}
