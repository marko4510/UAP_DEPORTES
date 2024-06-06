package com.uap.planificacion.model.dao;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.EstadoActividad;

public interface IEstadoActividadDao extends CrudRepository<EstadoActividad, Long>{
    
}
