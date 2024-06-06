package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.INivelFuncionalDao;
import com.uap.planificacion.model.entity.NivelFuncional;
import org.springframework.stereotype.Service;
@Service
public class NivelFuncionalServiceImpl implements INivelFuncionalService{

    @Autowired
    private INivelFuncionalDao nivelFuncionalDao;

    @Override
    public List<NivelFuncional> findAll() {

        return (List<NivelFuncional>) nivelFuncionalDao.findAll();
    }

    @Override
    public void save(NivelFuncional entidad) {

        nivelFuncionalDao.save(entidad);
    }

    @Override
    public NivelFuncional findOne(Long id) {

        return nivelFuncionalDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        nivelFuncionalDao.deleteById(id);
    }
    
}
