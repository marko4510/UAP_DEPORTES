package com.uap.planificacion.model.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uap.planificacion.model.entity.Lugar;

public interface ILugarDao extends CrudRepository<Lugar, Long>{

    /*@Query(value="select * from programacion pr join actividad a"+
	"on a.id_actividad=pr.id_actividad join detalle_actividad da"+
	"on da.id_actividad=a.id_actividad join sub_detalle_actividad sda"+
	"on sda.id_detalle_actividad=da.id_detalle_actividad join lugar_subdetalleactividad sdal"+
	"on sda.id_sub_detalle_actividad=sdal.id_sub_detalle_actividad join lugar l"+
	"on sdal.id_lugar=l.id_lugar"+
	"where 	pr.id_programacion=?1 and"+
    "a.estado!='X' and"+
			"da.estado!='X' and"+
			"sda.estado!='X'",nativeQuery = true)*/
	@Query("select l from Lugar l left join l.subDetalleActividads  sda left join sda.detalleActividad da left join da.actividad a left join a.programacions p where p.id_programacion=?1 and a.estado!='X' and da.estado!='X' and sda.estado!='X'")
    public List<Lugar> sacarLugaresPorProgramacion(Long idProgramacion);

	@Query("select l from Lugar l where l.tipo_lugar = ?1")
	public List<Lugar> sacarLugaresConTipoE(String tipoLugar);

	@Query(value="select * from lugar l left join lugar_subdetalleactividad ls\r\n" + //
			"on l.id_lugar = ls.id_lugar \r\n" + //
			"where ls.id_sub_detalle_actividad = ?1", nativeQuery = true)
	public Set<Lugar> sacarLugarPorSubdetalle(Long id_sub_detalle_Actividad);

	    
}
