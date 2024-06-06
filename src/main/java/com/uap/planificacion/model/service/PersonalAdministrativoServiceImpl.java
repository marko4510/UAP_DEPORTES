package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IPersonalAdministrativoDao;
import com.uap.planificacion.model.entity.PersonalAdministrativo;
import org.springframework.stereotype.Service;
@Service
public class PersonalAdministrativoServiceImpl implements IPersonalAdministrativoService{
    
    @Autowired
    private IPersonalAdministrativoDao personalAdministrativoDao;

    @Override
    public List<PersonalAdministrativo> findAll() {

        return (List<PersonalAdministrativo>) personalAdministrativoDao.findAll();
    }

    @Override
    public void save(PersonalAdministrativo entidad) {

        personalAdministrativoDao.save(entidad);
    }

    @Override
    public PersonalAdministrativo findOne(Long id) {

        return personalAdministrativoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        personalAdministrativoDao.deleteById(id);
    }

    @Override
    public PersonalAdministrativo getPersonalAdministrativoCod(Integer cod) {
        // TODO Auto-generated method stub
        return personalAdministrativoDao.getPersonalAdministrativoCod(cod);
    }

}
