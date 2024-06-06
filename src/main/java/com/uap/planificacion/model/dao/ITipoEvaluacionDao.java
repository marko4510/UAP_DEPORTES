package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.TipoEvaluacion;

public interface ITipoEvaluacionDao extends CrudRepository<TipoEvaluacion, Long>{
    
}
