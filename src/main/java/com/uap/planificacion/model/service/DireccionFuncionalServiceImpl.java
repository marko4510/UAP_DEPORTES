package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IDireccionFuncionalDao;
import com.uap.planificacion.model.entity.DireccionFuncional;
import org.springframework.stereotype.Service;
@Service
public class DireccionFuncionalServiceImpl implements IDireccionFuncionalService{
    
    @Autowired
    private IDireccionFuncionalDao direccionFuncionalDao;

    @Override
    public List<DireccionFuncional> findAll() {

        return (List<DireccionFuncional>) direccionFuncionalDao.findAll();
    }

    @Override
    public void save(DireccionFuncional entidad) {

        direccionFuncionalDao.save(entidad);
    }

    @Override
    public DireccionFuncional findOne(Long id) {

        return direccionFuncionalDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        direccionFuncionalDao.deleteById(id);
    }
}
