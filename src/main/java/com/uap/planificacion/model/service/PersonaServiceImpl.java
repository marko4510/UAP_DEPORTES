package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IPersonaDao;
import com.uap.planificacion.model.entity.Persona;
import org.springframework.stereotype.Service;
@Service
public class PersonaServiceImpl implements IPersonaService{
    
    @Autowired
    private IPersonaDao personaDao;

    @Override
    public List<Persona> findAll() {

        return (List<Persona>) personaDao.findAll();
    }

    @Override
    public void save(Persona entidad) {

        personaDao.save(entidad);
    }

    @Override
    public Persona findOne(Long id) {

        return personaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        personaDao.deleteById(id);
    }

    @Override
    public Persona getPersonaCi(String ci) {
        // TODO Auto-generated method stub
        return personaDao.getPersonaCi(ci);
    }
}
