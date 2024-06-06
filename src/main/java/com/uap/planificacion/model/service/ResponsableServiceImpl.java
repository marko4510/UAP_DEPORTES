package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IResponsableDao;
import com.uap.planificacion.model.entity.Responsable;
import org.springframework.stereotype.Service;
@Service
public class ResponsableServiceImpl implements IResponsableService{
    
    @Autowired
    private IResponsableDao responsableDao;

    @Override
    public List<Responsable> findAll() {

        return (List<Responsable>) responsableDao.findAll();
    }

    @Override
    public void save(Responsable entidad) {

        responsableDao.save(entidad);
    }

    @Override
    public Responsable findOne(Long id) {

        return responsableDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        responsableDao.deleteById(id);
    }

}
