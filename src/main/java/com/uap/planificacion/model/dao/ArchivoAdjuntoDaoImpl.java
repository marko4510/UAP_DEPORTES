package com.uap.planificacion.model.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.uap.planificacion.model.dao.IArchivoAdjuntoDao;

import com.uap.planificacion.model.entity.ArchivoAdjunto;

@Repository("IArchivoAdjuntoDao")
public class ArchivoAdjuntoDaoImpl implements IArchivoAdjuntoDao{

    @PersistenceContext
    private javax.persistence.EntityManager em;

    @Transactional
  
    public ArchivoAdjunto registrarArchivoAdjunto(ArchivoAdjunto archivoAdjunto){

        em.persist(archivoAdjunto);
        return archivoAdjunto;
    }

    public ArchivoAdjunto buscarArchivoAdjunto(Long id_archivo_adjunto){
        String sql = " SELECT arc "
        + " FROM ArchivoAdjunto arc "
        + " WHERE arc.id_archivo_adjunto =:id_archivo_adjunto"
        + " AND arc.idEstado = 'A' ";
        Query q = em.createQuery(sql);
        q.setParameter("id_archivo_adjunto", id_archivo_adjunto);
        try {
        return (ArchivoAdjunto) q.getSingleResult();
        } catch (Exception e) {
        return null;
        }
    }


    public void modificarArchivoAdjunto(ArchivoAdjunto archivoAdjunto){

        em.merge(archivoAdjunto);
    }

    @Override
    public List<ArchivoAdjunto> listarArchivoAdjuntoJPQL() {
        String sql = "SELECT adj "
        + " FROM ArchivoAdjunto adj "
        + " WHERE adj.estado = 'A' ";
        Query q = em.createQuery(sql);
        return q.getResultList();
    }
    
    @Override
    public ArchivoAdjunto buscarArchivoAdjuntoPorEvaluacion(Long id_evaluacion) {
        String sql = "SELECT gaa  "
        + " FROM Evaluacion sol LEFT JOIN  sol.archivoAdjunto gaa"
        + " WHERE sol.id_evaluacion =:id_evaluacion "
        + " AND gaa.estado = 'A' ";
        Query q = em.createQuery(sql);
        q.setParameter("id_evaluacion", id_evaluacion);
        try {
        return (ArchivoAdjunto) q.getSingleResult();
        } catch (Exception e) {
        return null;
        }
    }

    @Override
    //@Query(value = "select ar.id_archivo_adjunto, ar.nombre_archivo, ar.ruta_archivo from actividad as ac inner join evaluacion as ev on ac.id_actividad=ev.id_actividad inner join archivo_adjunto as ar on ar.id_archivo_adjunto=ev.id_archivo_adjunto where ac.id_actividad=?1", nativeQuery = true)
    public ArchivoAdjunto buscarArchivoAdjuntoPorActividad(Long id_actividad) {
        String ql = "select arch "
        +"from Actividad ac left JOIN ac.evaluacions ev left join ev.adjuntoArchivo arch "
        +"where ac.id_actividad=:id_actividad";
        Query q = em.createQuery(ql);
        q.setParameter("id_actividad", id_actividad);
        try {
        return (ArchivoAdjunto) q.getSingleResult();
        } catch (Exception e) {
        return null;
        }
    }

}
