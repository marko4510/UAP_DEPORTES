package com.uap.planificacion.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Programacion;

public interface IProgramacionDao extends CrudRepository<Programacion, Long>{

    @Query(value = "select * from programacion p "+
    "where p.fecha between ?1 and ?2 and p.tipo_programacion=?3 and p.estado!='X' ORDER BY p.fecha ASC", nativeQuery = true)
    public List<Programacion> listaProgramacionRangoPorFechasTipo(Date fecha1, Date fecha2, String tipo);

    @Query(value = "select * from programacion as p left join actividad as a "+
    "on p.id_actividad=a.id_actividad left join detalle_actividad as da "+
    "on a.id_actividad=da.id_actividad "+
    "where da.fecha_detalle_actividad between ?1 and ?2 and p.estado!='X'", nativeQuery = true)
    public List<Programacion> listaProgramacionRangoPorFechas(Date fecha1, Date fecha2);
@Query(value = "select * from programacion as p "+
    "where p.fecha between ?1 and ?2 and p.estado!='X' ORDER BY p.fecha asc", nativeQuery = true)
    public List<Programacion> listaProgramacionRangoPorFechasProgramadas(Date fecha1, Date fecha2);
    @Query("select p from Programacion as p ORDER BY p.id_programacion DESC")
    public List<Programacion> findAllOrderFechaDesc();
    
}
