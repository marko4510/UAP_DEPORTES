package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.IEstadoActividadDao;
import com.uap.planificacion.model.entity.EstadoActividad;

@Service
public class EstadoActividadServiceImpl implements IEstadoActividadService {

    @Autowired
    private IEstadoActividadDao estadoActividadDao;

    @Override
    public List<EstadoActividad> findAll() {
        return (List<EstadoActividad>) estadoActividadDao.findAll();
    }

    @Override
    public void save(EstadoActividad estadoActividad) {
        estadoActividadDao.save(estadoActividad);
    }

    @Override
    public EstadoActividad findOne(Long id_e) {
        return estadoActividadDao.findById(id_e).orElse(null);
    }

    @Override
    public void delete(Long id) {
        estadoActividadDao.deleteById(id);
    }

}
