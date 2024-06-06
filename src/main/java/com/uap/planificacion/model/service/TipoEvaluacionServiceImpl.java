package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.ITipoEvaluacionDao;
import com.uap.planificacion.model.entity.TipoEvaluacion;
import org.springframework.stereotype.Service;
@Service
public class TipoEvaluacionServiceImpl implements ITipoEvaluacionService{

    @Autowired
    private ITipoEvaluacionDao tipoEvaluacionDao;

    @Override
    public List<TipoEvaluacion> findAll() {
        return (List<TipoEvaluacion>) tipoEvaluacionDao.findAll();
    }

    @Override
    public void save(TipoEvaluacion tipoEvaluacion) {
        tipoEvaluacionDao.save(tipoEvaluacion);
    }

    @Override
    public TipoEvaluacion findOne(Long id) {
        return  tipoEvaluacionDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        tipoEvaluacionDao.deleteById(id);
    }
    
}