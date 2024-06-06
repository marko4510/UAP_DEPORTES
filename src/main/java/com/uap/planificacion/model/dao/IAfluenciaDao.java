package com.uap.planificacion.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Afluencia;

public interface IAfluenciaDao extends CrudRepository<Afluencia,Long>{
    
}
