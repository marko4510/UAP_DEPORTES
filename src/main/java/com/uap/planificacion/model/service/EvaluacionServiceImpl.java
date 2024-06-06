package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IEvaluacionDao;
import com.uap.planificacion.model.entity.Evaluacion;
import org.springframework.stereotype.Service;
@Service
public class EvaluacionServiceImpl implements IEvaluacionService{
    
    @Autowired
    private IEvaluacionDao evaluacionDao;

    @Override
    public List<Evaluacion> findAll() {

        return (List<Evaluacion>) evaluacionDao.findAll();
    }

    @Override
    public void save(Evaluacion entidad) {

        evaluacionDao.save(entidad);
    }

    @Override
    public Evaluacion findOne(Long id) {

        return evaluacionDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        evaluacionDao.deleteById(id);
    }

}
