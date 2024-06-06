package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.entity.Evento;

@Service
public class EventoServiceImpl implements IEventoService {

    @Autowired
    private IEventoDao eventoDao;

    @Override
    public List<Evento> findAll() {
        return eventoDao.findAll();
    }

    @Override
    public void save(Evento entidad) {
        eventoDao.save(entidad);
    }

    @Override
    public Evento findOne(Long id) {
        return eventoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        eventoDao.deleteById(id);
    }

    @Override
    public List<Evento> listaEventosSolicitados() {
        return eventoDao.listaEventosSolicitados();
    }

}
