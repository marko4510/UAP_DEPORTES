package com.uap.planificacion.model.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uap.planificacion.model.entity.Actividad;
import com.uap.planificacion.model.entity.DetalleActividad;
import com.uap.planificacion.model.entity.Evento;
import com.uap.planificacion.model.entity.Lugar;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;

import com.uap.planificacion.model.dao.IDetalleActividadDao;
import com.uap.planificacion.model.dao.IEventoDao;
import com.uap.planificacion.model.dao.ILugarDao;
import com.uap.planificacion.model.dao.ISubDetalleActividadDao;
import com.uap.planificacion.model.entity.SubDetalleActividad;
import org.springframework.stereotype.Service;

@Service
public class SubDetalleActividadServiceImpl implements ISubDetalleActividadService{
    
    @Autowired
    private ISubDetalleActividadDao subDetalleActividadDao;
    @Autowired
    private ILugarDao iLugarDao;
    @Autowired
    private IDetalleActividadDao detalleActividadDao;
    @Autowired
    private IEventoDao eventoDao;

    @Override
    public List<SubDetalleActividad> findAll() {

        return (List<SubDetalleActividad>) subDetalleActividadDao.findAll();
    }

    @Override
    public void save(SubDetalleActividad entidad) {

        subDetalleActividadDao.save(entidad);
    }

    @Override
    public SubDetalleActividad findOne(Long id) {

        return subDetalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        subDetalleActividadDao.deleteById(id);
    }

    @Override
    public List<SubDetalleActividad> subDetallePorIdDetalles(Long id_sub_detalle) {
        return (List<SubDetalleActividad>) subDetalleActividadDao.subDetallePorIdDetalles(id_sub_detalle);
    }

    @Override
    public List<SubDetalleActividad> findAllEspecialesAndMesDeAnioActual(Integer mes){
        List<SubDetalleActividad> listaSubDetalle = new ArrayList<>();
        List<Object[]> listaSql = subDetalleActividadDao.findAllEspecialesAndMesDeAnioActual(mes);
        for(Object[] sql : listaSql){
            SubDetalleActividad sda = new SubDetalleActividad();
            DetalleActividad da = new DetalleActividad();
            Actividad a = new Actividad();
            
            sda.setId_sub_detalle_actividad(((BigInteger)sql[0]).longValue());
            java.util.Set<Lugar> lugares = iLugarDao.sacarLugarPorSubdetalle(((BigInteger)sql[0]).longValue());
            sda.setLugares(lugares);
            //List<Lugar> lugaresL = new ArrayList<>(lugares);
            //ev.setId_evento(lugaresL.get(0).getEventos().get(0).getId_evento());
            DetalleActividad detalleActividad = detalleActividadDao.sacarDetalleActividadPorIdSubDet(((BigInteger)sql[0]).longValue());
            da.setId_detalle_actividad(detalleActividad.getId_detalle_actividad());//para sacar id detalle actividad
            a.setId_actividad(detalleActividad.getActividad().getId_actividad());//mara mostrar id actividad
            a.setDescripcion_actividad(sql[1].toString());

            a.setAvance_actividad(detalleActividad.getActividad().getAvance_actividad());
            //ev.setAvance_evento(sql[5].toString());
            //System.out.println(sql[5].toString());
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora =  new SimpleDateFormat("HH:mm:ss");
            try {
                da.setFecha_detalle_actividad(formatoFecha.parse(sql[2].toString()));
                Date horaInicio = formatoHora.parse(sql[3].toString());
                Date horaFinal = formatoHora.parse(sql[4].toString());
                sda.setHora_inicio(horaInicio);
                sda.setHora_final(horaFinal);

                da.setActividad(a);
                sda.setDetalleActividad(da);
                listaSubDetalle.add(sda);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return listaSubDetalle;
    }
}
