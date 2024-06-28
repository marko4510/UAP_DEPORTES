package com.uap.planificacion.model.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.IDetalleActividadDao;
import com.uap.planificacion.model.entity.DetalleActividad;

@Service
public class DetalleActividadServiceImpl implements IDetalleActividadService{
    
    @Autowired
    private IDetalleActividadDao detalleActividadDao;

    @Override
    public List<DetalleActividad> findAll() {

        return (List<DetalleActividad>) detalleActividadDao.findAll();
    }

    @Override
    public void save(DetalleActividad entidad) {

        detalleActividadDao.save(entidad);
    }

    @Override
    public DetalleActividad findOne(Long id) {

        return detalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        detalleActividadDao.deleteById(id);
    }

    @Override
    public List<DetalleActividad> detallePorIdActividad(Long id_actividad) {
        return (List<DetalleActividad>) detalleActividadDao.detallePorIdActividad(id_actividad);
    }

    @Override
    public List<DetalleActividad> findActividadesBetweenDates(Date fechaInicio, Date fechaFin) {
        return (List<DetalleActividad>) detalleActividadDao.findActividadesBetweenDates(fechaInicio, fechaFin);
    }

    @Override
    public List<Object[]> reporteGeneral(Date fecha_inicio, Date fecha_final) {
        return (List<Object[]>) detalleActividadDao.reporteGeneral(fecha_inicio, fecha_final);
    }

    @Override
    public List<Object[]> reportePorInstalacion(Date fecha_inicio, Date fecha_final, Long id_lugar) {
        return (List<Object[]>) detalleActividadDao.reportePorInstalacion(fecha_inicio, fecha_final, id_lugar);
    }

    @Override
    public DetalleActividad detalleActividadPorIdActividad(Long id_actividad) {
        return detalleActividadDao.detalleActividadPorIdActividad(id_actividad);
    }
}
