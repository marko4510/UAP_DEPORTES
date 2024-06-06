package com.uap.planificacion.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Actividad;

public interface IActividadDao extends CrudRepository<Actividad, Long>{

    @Query("select ac from Actividad ac where ac.fecha_registro BETWEEN ?1 AND ?2")
    public List<Actividad> findAllActividadesThen(Date fechaAnterior, Date dechaActual);
    @Query("select ac from Actividad ac where ac.id_actividad=?1 and ac.fecha_registro BETWEEN ?2 AND ?3")
    public List<Actividad> findByIdActividadesThen(Long id_actividad, Date fechaAnterior, Date fechaActual);
    
    @Query(value="SELECT * FROM actividad as ac where  ac.id_unidad_funcional=?1 and ac.fecha_registro BETWEEN ?2 AND ?3 and ac.estado != 'X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorUnidadYRango(Long id_unidad, Date fechaAnterior, Date fechaActual);
    
    @Query("SELECT DISTINCT ac FROM Actividad ac left join ac.unidadFuncional uf left join ac.detalleActividads da "+
    "where uf.id_unidad_funcional=?1 and da.fecha_detalle_actividad >= ?2 "+
    "and da.fecha_detalle_actividad<= ?3 and da.estado != 'X' and ac.estado != 'X'")
    public List<Actividad> listaActividadPorSemanaUnidad(Long id_unidad, Date fechaLunes, Date fechaDomingo);
    @Query("SELECT DISTINCT ac FROM Actividad ac left join ac.unidadFuncional uf left join uf.direccionFuncional df left join ac.detalleActividads da "+
    "where df.id_direccion_funcional=?1 and da.fecha_detalle_actividad >= ?2 "+
    "and da.fecha_detalle_actividad<= ?3 and da.estado != 'X' and ac.estado != 'X'")
    public List<Actividad> listaActividadPorSemanaDireccion(Long id_direccion, Date fechaLunes, Date fechaDomingo);
    @Query(value="SELECT DISTINCT ac.*, da.* FROM actividad as ac left join detalle_actividad as da "+
    "on ac.id_actividad=da.id_actividad "+
     "  where da.fecha_detalle_actividad >= ?1 "+
      "  and da.fecha_detalle_actividad<= ?2 and da.estado != 'X' and ac.estado != 'X'", nativeQuery = true)
    public List<Actividad> listaActividadPorSemanaAllUnidades(Date fechaLunes, Date fechaDomingo);


    @Query(value="SELECT * FROM actividad as ac inner join unidad_funcional as u on ac.id_unidad_funcional=u.id_unidad_funcional where ac.fecha_registro BETWEEN ?2 AND ?3 and u.id_direccion_funcional=?1 and ac.estado != 'X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorDireccionYRango(Long id_direccion, Date fechaAnterior, Date fechaActual);

    

    @Query(value="select * from actividad as ac order by ac.fecha_registro desc", nativeQuery = true)
    public List<Actividad> findAllOrderIdDesc();

    @Query(value="SELECT * FROM actividad as ac where  ac.id_unidad_funcional=?1 and ac.estado != 'X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorUnidad(Long id_unidad);

    
    @Query(value="SELECT * FROM actividad as ac inner join unidad_funcional as u on ac.id_unidad_funcional=u.id_unidad_funcional where  u.id_direccion_funcional=?1 and ac.estado != 'X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorDireccion(Long id_direccion);
    
    @Query(value = "select * "+
    " from actividad as ac inner join detalle_actividad as dta "+
    "        on dta.id_actividad=ac.id_actividad "+
    "        WHERE EXTRACT(MONTH FROM dta.fecha_detalle_actividad)=?1"+
    "        AND EXTRACT(YEAR FROM dta.fecha_detalle_actividad)=?2 AND ac.estado!='X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorMesAnio(int mes, int anio); 

    @Query(value = "select DISTINCT * "+
    " from actividad as ac join detalle_actividad as dta "+
    "        on dta.id_actividad=ac.id_actividad join unidad_funcional as un on un.id_unidad_funcional=ac.id_unidad_funcional"+
    "        WHERE un.id_unidad_funcional=?1"+
    "       AND EXTRACT(MONTH FROM dta.fecha_detalle_actividad)=?2"+
    "        AND EXTRACT(YEAR FROM dta.fecha_detalle_actividad)=?3 AND ac.estado!='X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorUnidadMesAnio(Long id_unidad, int mes, int anio);

    @Query(value = "select * "+
    " from actividad as ac inner join detalle_actividad as dta "+
    "        on dta.id_actividad=ac.id_actividad inner join unidad_funcional as un on un.id_unidad_funcional=ac.id_unidad_funcional"+
    "        inner join direccion_funcional as di on un.id_direccion_funcional=di.id_direccion_funcional"+
    "        WHERE di.id_direccion_funcional=?1"+
    "       AND EXTRACT(MONTH FROM dta.fecha_detalle_actividad)=?2"+
    "        AND EXTRACT(YEAR FROM dta.fecha_detalle_actividad)=?3 AND ac.estado!='X' ORDER BY ac.id_actividad DESC", nativeQuery = true)
    public List<Actividad> listaActividadesPorDireccionMesAnio(Long id_direccion, int mes, int anio);
    
    @Query(value = "SELECT DISTINCT * FROM actividad AS ac " +
        "LEFT JOIN detalle_actividad AS da ON ac.id_actividad = da.id_actividad " +
        "LEFT JOIN tipo_actividad AS ta ON ac.id_tipo_actividad = ta.id_tipo_actividad " +
        "WHERE da.fecha_detalle_actividad = ?1 AND ta.id_tipo_actividad = 2 " +
        "AND da.estado != 'X' AND ac.estado != 'X' ", nativeQuery = true)
    public List<Actividad> listaActividadesImpactoPorFecha(Date fechaPorDia);
}
