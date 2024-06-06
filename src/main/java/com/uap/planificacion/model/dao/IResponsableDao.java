package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Responsable;

public interface IResponsableDao extends CrudRepository<Responsable, Long>{
    
}
