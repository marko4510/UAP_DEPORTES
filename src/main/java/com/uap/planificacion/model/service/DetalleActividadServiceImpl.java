package com.uap.planificacion.model.service;

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
}
