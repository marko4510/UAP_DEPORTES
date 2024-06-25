package com.uap.planificacion.model.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uap.planificacion.model.entity.DetalleActividad;

public interface IDetalleActividadDao extends CrudRepository<DetalleActividad, Long>{
    
    @Query("select da from DetalleActividad da left join da.actividad a where a.id_actividad=?1 and da.estado!='X'")
    List<DetalleActividad> detallePorIdActividad(Long id_actividad);

    @Query(value = "select * from detalle_actividad da left join sub_detalle_actividad sda on da.id_detalle_actividad=sda.id_detalle_actividad where sda.id_sub_detalle_actividad=?1", nativeQuery = true)
    DetalleActividad sacarDetalleActividadPorIdSubDet(Long id_sub_det_act);

    @Query("SELECT d FROM DetalleActividad d WHERE d.fecha_detalle_actividad BETWEEN :fechaInicio AND :fechaFin AND d.estado != 'X'")
    List<DetalleActividad> findActividadesBetweenDates(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

     @Query(value = "select a.descripcion_actividad, ta.nombre_tipo_actividad, uf.nom_unidad, l.nombre_lugar, da.fecha_detalle_actividad, sda.hora_inicio, sda.hora_final, da.costo  FROM actividad a \n" + //
                "left join tipo_actividad ta on ta.id_tipo_actividad = a.id_tipo_actividad \n" + //
                "left join unidad_funcional uf ON uf.id_unidad_funcional = a.id_unidad_funcional \n" + //
                "left join detalle_actividad da on da.id_actividad = a.id_actividad \n" + //
                "LEFT JOIN sub_detalle_actividad sda ON sda.id_detalle_actividad = da.id_detalle_actividad\n" + //
                "LEFT JOIN lugar_subdetalleactividad ls ON ls.id_sub_detalle_actividad = sda.id_sub_detalle_actividad\n" + //
                "LEFT JOIN lugar l ON l.id_lugar = ls.id_lugar\n" + //
                "WHERE da.estado != 'X'\n" + //
                "AND da.fecha_detalle_actividad BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<Object[]> reporteGeneral(Date fecha_inicio, Date fecha_final);

    @Query(value = "select a.descripcion_actividad, ta.nombre_tipo_actividad, uf.nom_unidad, l.nombre_lugar, da.fecha_detalle_actividad, sda.hora_inicio, sda.hora_final, da.costo " +
               "FROM actividad a " +
               "left join tipo_actividad ta on ta.id_tipo_actividad = a.id_tipo_actividad " +
               "left join unidad_funcional uf ON uf.id_unidad_funcional = a.id_unidad_funcional " +
               "left join detalle_actividad da on da.id_actividad = a.id_actividad " +
               "LEFT JOIN sub_detalle_actividad sda ON sda.id_detalle_actividad = da.id_detalle_actividad " +
               "LEFT JOIN lugar_subdetalleactividad ls ON ls.id_sub_detalle_actividad = sda.id_sub_detalle_actividad " +
               "LEFT JOIN lugar l ON l.id_lugar = ls.id_lugar " +
               "WHERE da.estado != 'X' " +
               "AND da.fecha_detalle_actividad BETWEEN ?1 AND ?2 " +
               "AND l.id_lugar = ?3", nativeQuery = true)
public List<Object[]> reportePorInstalacion(Date fecha_inicio, Date fecha_final, Long id_lugar);

}
