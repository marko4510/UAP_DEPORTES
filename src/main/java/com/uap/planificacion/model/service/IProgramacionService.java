package com.uap.planificacion.model.service;

import java.util.Date;
import java.util.List;

import com.uap.planificacion.model.entity.Programacion;

public interface IProgramacionService {
    public List<Programacion> findAllOrderFechaDesc();

	public void save(Programacion entidad);

	public Programacion findOne(Long id);

	public void delete(Long id);

	public List<Programacion> listaProgramacionRangoPorFechas(Date fecha1, Date fecha2);
	
	public List<Programacion> listaProgramacionRangoPorFechasProgramadas(Date fecha1, Date fecha2);
	
	public List<Programacion> listaProgramacionRangoPorFechasTipo(Date fecha1, Date fecha2, String tipo);
}
