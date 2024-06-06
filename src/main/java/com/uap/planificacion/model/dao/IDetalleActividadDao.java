package com.uap.planificacion.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.DetalleActividad;

public interface IDetalleActividadDao extends CrudRepository<DetalleActividad, Long>{
    
    @Query("select da from DetalleActividad da left join da.actividad a where a.id_actividad=?1 and da.estado!='X'")
    List<DetalleActividad> detallePorIdActividad(Long id_actividad);

    @Query(value = "select * from detalle_actividad da left join sub_detalle_actividad sda on da.id_detalle_actividad=sda.id_detalle_actividad where sda.id_sub_detalle_actividad=?1", nativeQuery = true)
    DetalleActividad sacarDetalleActividadPorIdSubDet(Long id_sub_det_act);
}
