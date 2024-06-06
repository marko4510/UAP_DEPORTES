package com.uap.planificacion.model.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uap.planificacion.model.dao.IArchivoAdjuntoDao;
import com.uap.planificacion.model.entity.ArchivoAdjunto;

@Service
@Transactional

public class ArchivoAdjuntoServiceImpl implements IArchivoAdjuntoService{
    @Autowired
    private IArchivoAdjuntoDao archivoAdjuntoDao;

    @Override
    public ArchivoAdjunto registrarArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        return archivoAdjuntoDao.registrarArchivoAdjunto(archivoAdjunto);
    }

    @Override
    public ArchivoAdjunto buscarArchivoAdjunto(Long id_archivo_adjunto) {
        return archivoAdjuntoDao.buscarArchivoAdjunto(id_archivo_adjunto);
    }

    @Override
    public void modificarArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        archivoAdjuntoDao.modificarArchivoAdjunto(archivoAdjunto);
    }

    @Override
    public List<ArchivoAdjunto> listarArchivoAdjunto() {
        return archivoAdjuntoDao.listarArchivoAdjuntoJPQL();
    }

    @Override
    public ArchivoAdjunto buscarArchivoAdjuntoPorEvaluacion(Long id_evaluacion) {
       return archivoAdjuntoDao.buscarArchivoAdjuntoPorEvaluacion(id_evaluacion);
    }

    @Override
    public ArchivoAdjunto buscarArchivoAdjuntoPorActividad(Long id_actividad) {
        return archivoAdjuntoDao.buscarArchivoAdjuntoPorActividad(id_actividad);
    }


    
}
