package com.uap.planificacion.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IProgramacionDao;
import com.uap.planificacion.model.entity.Programacion;
import org.springframework.stereotype.Service;
@Service
public class ProgramacionServiceImpl implements IProgramacionService{
    
    @Autowired
    private IProgramacionDao programacionDao;

    @Override
    public List<Programacion> findAllOrderFechaDesc() {
        return (List<Programacion>) programacionDao.findAllOrderFechaDesc();
    }

    @Override
    public void save(Programacion entidad) {

        programacionDao.save(entidad);
    }

    @Override
    public Programacion findOne(Long id) {

        return programacionDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        programacionDao.deleteById(id);
    }

    @Override
    public List<Programacion> listaProgramacionRangoPorFechas(Date fecha1, Date fecha2) {
        return (List<Programacion>) programacionDao.listaProgramacionRangoPorFechas(fecha1, fecha2);
    }

    @Override
    public List<Programacion> listaProgramacionRangoPorFechasTipo(Date fecha1, Date fecha2, String tipo) {
        return (List<Programacion>) programacionDao.listaProgramacionRangoPorFechasTipo(fecha1, fecha2, tipo);
    }

    @Override
    public List<Programacion> listaProgramacionRangoPorFechasProgramadas(Date fecha1, Date fecha2) {
        return (List<Programacion>) programacionDao.listaProgramacionRangoPorFechasProgramadas(fecha1, fecha2);
    }

}
