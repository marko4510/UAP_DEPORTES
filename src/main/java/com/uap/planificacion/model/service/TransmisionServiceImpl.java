package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.ITransmisionDao;
import com.uap.planificacion.model.entity.Transmision;

@Service
public class TransmisionServiceImpl implements ITransmisionService{

    @Autowired
    private ITransmisionDao transmisionDao;

    @Override
    public List<Transmision> findAll() {
        return (List<Transmision>) transmisionDao.findAll();
    }

    @Override
    public void save(Transmision transmision) {
        transmisionDao.save(transmision);
    }

    @Override
    public Transmision findOne(Long id) {
        return transmisionDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        transmisionDao.deleteById(id);
    }
    
}
