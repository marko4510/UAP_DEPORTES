package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.ITipoActividadDao;
import com.uap.planificacion.model.entity.TipoActividad;
import org.springframework.stereotype.Service;
@Service
public class TipoActividadServiceImpl implements ITipoActividadService{
    
    @Autowired
    private ITipoActividadDao tipoActividadDao;

    @Override
    public List<TipoActividad> findAll() {

        return (List<TipoActividad>) tipoActividadDao.findAll();
    }

    @Override
    public void save(TipoActividad entidad) {

        tipoActividadDao.save(entidad);
    }

    @Override
    public TipoActividad findOne(Long id) {

        return tipoActividadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        tipoActividadDao.deleteById(id);
    }

}
