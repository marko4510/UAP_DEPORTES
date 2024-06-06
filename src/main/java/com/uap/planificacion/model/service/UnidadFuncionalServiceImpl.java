package com.uap.planificacion.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IUnidadFuncionalDao;
import com.uap.planificacion.model.entity.UnidadFuncional;
import org.springframework.stereotype.Service;
@Service
public class UnidadFuncionalServiceImpl implements IUnidadFuncionalService{

    @Autowired
    private IUnidadFuncionalDao unidadFuncionalDao;

    @Override
    public List<UnidadFuncional> findAll() {
        return (List<UnidadFuncional>) unidadFuncionalDao.findAll();
    }

    @Override
    public void save(UnidadFuncional unidadFuncional) {
        unidadFuncionalDao.save(unidadFuncional);
    }

    @Override
    public UnidadFuncional findOne(Long id) {
        return  unidadFuncionalDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        unidadFuncionalDao.deleteById(id);
    }

    @Override
    public List<UnidadFuncional> listaUnidadesSinActividad(Date fechainicio, Date fechafin) {
        return (List<UnidadFuncional>) unidadFuncionalDao.listaUnidadesSinActividad(fechainicio, fechafin);
    }

    @Override
    public List<UnidadFuncional> listaUnidadesConActividad(Date fechainicio, Date fechafin) {
        return (List<UnidadFuncional>) unidadFuncionalDao.listaUnidadesConActividad(fechainicio, fechafin);
    }
    
    @Override
    public List<UnidadFuncional> listaTodasLasUnidades() {
        return (List<UnidadFuncional>) unidadFuncionalDao.listaTodasLasUnidades();
    }
}
