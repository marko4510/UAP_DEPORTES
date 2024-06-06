package com.uap.planificacion.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uap.planificacion.model.entity.HistorialLogin;

public interface IHistorialLoginDao extends JpaRepository<HistorialLogin, Long>{
    
}
