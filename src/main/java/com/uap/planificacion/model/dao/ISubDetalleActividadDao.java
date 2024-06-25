package com.uap.planificacion.model.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.SubDetalleActividad;

public interface ISubDetalleActividadDao extends CrudRepository<SubDetalleActividad, Long>{

    @Query(value = "select * from sub_detalle_actividad as sd where sd.id_detalle_actividad=?1", nativeQuery = true)
    List<SubDetalleActividad> subDetallePorIdDetalles(Long id_detalle);

    @Query(value = "select sb.id_sub_detalle_actividad, a.descripcion_actividad, da.fecha_detalle_actividad, sb.hora_inicio, sb.hora_final,  a.observacion " +
            "from sub_detalle_actividad sb " +
            "left join lugar_subdetalleactividad ls on sb.id_sub_detalle_actividad = ls.id_sub_detalle_actividad " +
            "left join detalle_actividad da on sb.id_detalle_actividad = da.id_detalle_actividad " +
            "left join actividad a on da.id_actividad = a.id_actividad " +
            "left join lugar l on ls.id_lugar = l.id_lugar " +
            "left join evento ev on l.id_lugar = ev.id_lugar " +
            "where l.tipo_lugar = 'E' and ev.id_actividad = a.id_actividad " +
            "  and DATE_PART('month', da.fecha_detalle_actividad) = ?1 " +
            "  and DATE_PART('year', da.fecha_detalle_actividad) = DATE_PART('year', CURRENT_DATE)" +
            "  and da.estado != 'X' " +
            "  and sb.estado != 'X' " +
            "order by da.id_detalle_actividad", nativeQuery = true)
    List<Object[]> findAllEspecialesAndMesDeAnioActual(Integer mes);


    @Query(value = "SELECT da.fecha_detalle_actividad, sda.hora_inicio, sda.hora_final, l.nombre_lugar\n" + //
                "FROM sub_detalle_actividad sda\n" + //
                "LEFT JOIN detalle_actividad da ON da.id_detalle_actividad = sda.id_detalle_actividad\n" + //
                "LEFT JOIN lugar_subdetalleactividad ls ON ls.id_sub_detalle_actividad = sda.id_sub_detalle_actividad\n" + //
                "LEFT JOIN lugar l ON l.id_lugar = ls.id_lugar\n" + //
                "WHERE da.estado != 'X' AND da.fecha_detalle_actividad = ?1\n" + //
                "AND sda.hora_inicio <= ?3\n" + //
                "AND sda.hora_final > ?2\n" + //
                "AND l.nombre_lugar = ?4", nativeQuery = true)
    public Object validarHoraReservas(LocalDate fecha_reserva, LocalTime hora_inicio, LocalTime hora_final, String nombre_lugar );



   
}
