package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.ILugarDao;
import com.uap.planificacion.model.entity.Lugar;
import org.springframework.stereotype.Service;
@Service
public class LugarServiceImpl implements ILugarService{
    
    @Autowired
    private ILugarDao lugarDao;

    @Override
    public List<Lugar> findAll() {

        return (List<Lugar>) lugarDao.findAll();
    }

    @Override
    public void save(Lugar entidad) {

        lugarDao.save(entidad);
    }

    @Override
    public Lugar findOne(Long id) {

        return lugarDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        lugarDao.deleteById(id);
    }

    @Override
    public List<Lugar> sacarLugaresPorProgramacion(Long idProgramacion) {
        return (List<Lugar>) lugarDao.sacarLugaresPorProgramacion(idProgramacion);
    }

    @Override
    public List<Lugar> sacarLugaresConTipoE(String tipoLugar) {
        return (List<Lugar>) lugarDao.sacarLugaresConTipoE(tipoLugar);
    }

  
}
