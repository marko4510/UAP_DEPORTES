package com.uap.planificacion.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.PersonalAdministrativo;

public interface IPersonalAdministrativoDao extends CrudRepository<PersonalAdministrativo, Long>{

    @Query("select pa from PersonalAdministrativo pa where pa.cod_funcionario = ?1 AND pa.estado='A'")
    public PersonalAdministrativo getPersonalAdministrativoCod(Integer cod);

}
