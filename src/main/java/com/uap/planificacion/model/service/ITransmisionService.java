package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.Transmision;

public interface ITransmisionService {
    public List<Transmision> findAll();

	public void save(Transmision transmision);

	public Transmision findOne(Long id);

	public void delete(Long id);
}
