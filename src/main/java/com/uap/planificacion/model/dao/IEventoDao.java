package com.uap.planificacion.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uap.planificacion.model.entity.Evento;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEventoDao extends JpaRepository <Evento, Long> {

    @Query(value = "select * from evento where estado_evento = 'P'", nativeQuery = true)
    public List<Evento> listaEventosSolicitados();     

    @Query(value = "select * from evento where id_actividad = ?1", nativeQuery = true)
    public Evento sacarEventoPorIdActividad(Long id_actividad);

    @Query(value="select * from evento e left join lugar l on e.id_lugar=l.id_lugar "+
    "left join actividad a on e.id_actividad=a.id_actividad where l.id_lugar=?1 and a.id_actividad=?2", nativeQuery = true)
    public Evento sacarEventoPorLugarYActividad(long id_lugar, Long id_actividad);

    @Query(value="delete from evento e where e.id_evento=?1 and e.id_actividad=?2", nativeQuery = true)
    public void eliminarEvento(Long id_evento, long id_actividad);
    
}
