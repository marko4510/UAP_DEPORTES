package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.TipoActividad;

public interface ITipoActividadDao extends CrudRepository<TipoActividad, Long>{
    
}
