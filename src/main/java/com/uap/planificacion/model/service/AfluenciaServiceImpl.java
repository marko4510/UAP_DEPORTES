package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IAfluenciaDao;
import com.uap.planificacion.model.entity.Afluencia;
import org.springframework.stereotype.Service;
@Service
public class AfluenciaServiceImpl implements IAfluenciaService{
    
    @Autowired
    private IAfluenciaDao afluenciaDao;

    @Override
    public List<Afluencia> findAll() {

        return (List<Afluencia>) afluenciaDao.findAll();
    }

    @Override
    public void save(Afluencia entidad) {

        afluenciaDao.save(entidad);
    }

    @Override
    public Afluencia findOne(Long id) {

        return afluenciaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        afluenciaDao.deleteById(id);
    }
}
