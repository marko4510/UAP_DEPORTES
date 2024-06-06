package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Evaluacion;

public interface IEvaluacionDao extends CrudRepository<Evaluacion, Long>{
    
}
