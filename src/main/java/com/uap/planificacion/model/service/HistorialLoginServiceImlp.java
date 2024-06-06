package com.uap.planificacion.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.IHistorialLoginDao;
import com.uap.planificacion.model.entity.HistorialLogin;

@Service
public class HistorialLoginServiceImlp implements IHistorialLoginService {

    @Autowired
    private IHistorialLoginDao historialLoginDao;

    @Override
    public List<HistorialLogin> listaHistorialLogin() {
        return historialLoginDao.findAll();
    }

    @Override
    public void guardarHistorialLogin(HistorialLogin historial) {
        historialLoginDao.save(historial);
    }
    
}
