package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.HistorialLogin;

public interface IHistorialLoginService {
    
    public List<HistorialLogin> listaHistorialLogin();
    public void guardarHistorialLogin(HistorialLogin historial);

}
