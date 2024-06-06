package com.uap.planificacion.model.dao;

import java.util.List;

import com.uap.planificacion.model.entity.ArchivoAdjunto;

public interface IArchivoAdjuntoDao {
    
    public ArchivoAdjunto registrarArchivoAdjunto(ArchivoAdjunto archivoAdjunto);

    public ArchivoAdjunto buscarArchivoAdjunto(Long id_archivo_adjunto);

    public ArchivoAdjunto buscarArchivoAdjuntoPorEvaluacion(Long id_evaluacion);

    public ArchivoAdjunto buscarArchivoAdjuntoPorActividad(Long id_actividad);

    public void modificarArchivoAdjunto(ArchivoAdjunto archivoAdjunto);

    public List<ArchivoAdjunto> listarArchivoAdjuntoJPQL();
}
