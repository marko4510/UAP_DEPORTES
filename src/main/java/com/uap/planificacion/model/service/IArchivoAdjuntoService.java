package com.uap.planificacion.model.service;

import java.util.List;

import com.uap.planificacion.model.entity.ArchivoAdjunto;

public interface IArchivoAdjuntoService {
     
    public List<ArchivoAdjunto> listarArchivoAdjunto();
    public ArchivoAdjunto registrarArchivoAdjunto(ArchivoAdjunto archivoAdjunto);

    public ArchivoAdjunto buscarArchivoAdjunto(Long id_archivo_adjunto);

    public void modificarArchivoAdjunto(ArchivoAdjunto archivoAdjunto);

    public ArchivoAdjunto buscarArchivoAdjuntoPorEvaluacion(Long id_evaluacion);

    public ArchivoAdjunto buscarArchivoAdjuntoPorActividad(Long id_actividad);
}
