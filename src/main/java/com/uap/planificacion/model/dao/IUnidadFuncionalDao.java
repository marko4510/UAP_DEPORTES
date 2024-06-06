package com.uap.planificacion.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.UnidadFuncional;

public interface IUnidadFuncionalDao extends CrudRepository<UnidadFuncional, Long>{
 
    @Query(value="SELECT DISTINCT * "+
    "FROM unidad_funcional as u "+
    "left join direccion_funcional as df on df.id_direccion_funcional = u.id_direccion_funcional "+
    "left join personal_administrativo as  pa on u.id_unidad_funcional=pa.id_unidad_funcional "+
    "left join persona as pe on pa.id_persona=pe.id_persona "+
    "WHERE u.estado != 'X' and pa.estado = 'A' and pe.estado != 'X' and df.estado!='X' "+
    "and u.id_unidad_funcional not in (26,27,28,29,30,31,34,80,118) "+     
    "and u.id_unidad_funcional NOT IN ( "+
    "   SELECT ac.id_unidad_funcional  "+
    "   FROM actividad as ac "+
    "   left join detalle_actividad as da on ac.id_actividad=da.id_actividad "+
    "   left join sub_detalle_actividad AS sda on da.id_detalle_actividad=sda.id_detalle_actividad "+
    "   left join lugar_subdetalleactividad	as lsda "+
    "   ON sda.id_sub_detalle_actividad = lsda.id_sub_detalle_actividad "+
    "   left join lugar AS l "+
    "   ON lsda.id_lugar = l.id_lugar "+
    "   where da.estado != 'X' and ac.estado != 'X' and sda.estado != 'X' "+
    "   and da.fecha_detalle_actividad BETWEEN ?1 AND ?2)", nativeQuery = true)
    public List<UnidadFuncional> listaUnidadesSinActividad(Date fechainicio, Date fechafin);

    @Query(value="SELECT DISTINCT * "+
    "FROM unidad_funcional as u "+
    "left join direccion_funcional as df on df.id_direccion_funcional = u.id_direccion_funcional "+
    "left join personal_administrativo as  pa on u.id_unidad_funcional=pa.id_unidad_funcional "+
    "left join persona as pe on pa.id_persona=pe.id_persona "+
    "WHERE u.estado != 'X' and pa.estado = 'A' and pe.estado != 'X' and df.estado!='X' "+   
    "and u.id_unidad_funcional not in (26,27,28,29,30,31,34,80,118) "+   
    "and u.id_unidad_funcional IN ( "+
    "   SELECT ac.id_unidad_funcional  "+
    "   FROM actividad as ac "+
    "   left join detalle_actividad as da on ac.id_actividad=da.id_actividad "+
    "   left join sub_detalle_actividad AS sda on da.id_detalle_actividad=sda.id_detalle_actividad "+
    "   left join lugar_subdetalleactividad	as lsda "+
    "   ON sda.id_sub_detalle_actividad = lsda.id_sub_detalle_actividad "+
    "   left join lugar AS l "+
    "   ON lsda.id_lugar = l.id_lugar "+
    "   where da.estado != 'X' and ac.estado != 'X' and sda.estado != 'X' "+
    "   and da.fecha_detalle_actividad BETWEEN ?1 AND ?2)", nativeQuery = true)
    public List<UnidadFuncional> listaUnidadesConActividad(Date fechainicio, Date fechafin);

    @Query(value="SELECT DISTINCT * "+
    "FROM unidad_funcional as u "+
    "left join direccion_funcional as df on df.id_direccion_funcional = u.id_direccion_funcional "+
    "left join personal_administrativo as  pa on u.id_unidad_funcional=pa.id_unidad_funcional "+
    "left join persona as pe on pa.id_persona=pe.id_persona "+
    "WHERE u.estado != 'X' and pa.estado = 'A' and pe.estado != 'X' and df.estado!='X' "+
    "and u.id_unidad_funcional not in (26,27,28,29,30,31,34,80,118)", nativeQuery = true)
    public List<UnidadFuncional> listaTodasLasUnidades();
}
