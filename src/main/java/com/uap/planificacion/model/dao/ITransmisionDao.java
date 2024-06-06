package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Transmision;

public interface ITransmisionDao extends CrudRepository<Transmision, Long>{
    
}
