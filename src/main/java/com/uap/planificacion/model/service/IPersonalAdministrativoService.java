package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.PersonalAdministrativo;

public interface IPersonalAdministrativoService {
    
    public List<PersonalAdministrativo> findAll();

	public void save(PersonalAdministrativo actividad);

	public PersonalAdministrativo findOne(Long id);

	public void delete(Long id);

	public PersonalAdministrativo getPersonalAdministrativoCod(Integer cod);

}
