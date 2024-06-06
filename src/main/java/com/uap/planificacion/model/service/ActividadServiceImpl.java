package com.uap.planificacion.model.service;

import java.lang.annotation.Retention;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uap.planificacion.model.dao.IActividadDao;
import com.uap.planificacion.model.entity.Actividad;

@Service
public class ActividadServiceImpl implements IActividadService{
    @Autowired
    private IActividadDao almacenDao;

    @Override
    public List<Actividad> findAll() {

        return (List<Actividad>) almacenDao.findAll();
    }

    @Override
    public List<Actividad> findAllOrderIdDesc() {

        return (List<Actividad>) almacenDao.findAllOrderIdDesc();
    }

    @Override
    public List<Actividad> findAllActividadesThen(Date fechaAnterior, Date dechaActual){
        return (List<Actividad>) almacenDao.findAllActividadesThen(fechaAnterior, dechaActual);
    }

    @Override
    public List<Actividad> findByIdActividadesThen(Long id_actividad, Date fechaAnterior, Date dechaActual){
        return (List<Actividad>) almacenDao.findByIdActividadesThen(id_actividad, fechaAnterior, dechaActual);
    }

    @Override
    public void save(Actividad actividad) {

        almacenDao.save(actividad);
    }

    @Override
    public Actividad findOne(Long id) {

        return almacenDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        
        almacenDao.deleteById(id);
    }

    @Override
    public List<Actividad> listaActividadesPorUnidad(Long id_unidad) {
        return (List<Actividad>) almacenDao.listaActividadesPorUnidad(id_unidad); 
    }

    @Override
    public List<Actividad> listaActividadesPorUnidadYRango(Long id_unidad, Date fechaAnterior, Date fechaActual){
        return (List<Actividad>) almacenDao.listaActividadesPorUnidadYRango(id_unidad, fechaAnterior, fechaActual);
    }

    @Override
    public List<Actividad> listaActividadPorSemanaUnidad(Long id_unidad, Date fechaLunes, Date fechaDomingo){
        return (List<Actividad>) almacenDao.listaActividadPorSemanaUnidad(id_unidad, fechaLunes, fechaDomingo);
    }
    @Override
    public List<Actividad> listaActividadPorSemanaDireccion(Long id_direccion, Date fechaLunes, Date fechaDomingo){
        return (List<Actividad>) almacenDao.listaActividadPorSemanaDireccion(id_direccion, fechaLunes, fechaDomingo);
    }
    @Override
    public List<Actividad> listaActividadPorSemanaAllUnidades(Date fechaLunes, Date fechaDomingo){
        return (List<Actividad>) almacenDao.listaActividadPorSemanaAllUnidades(fechaLunes, fechaDomingo);
    }


    @Override
    public List<Actividad> listaActividadesPorDireccionYRango(Long id_direccion, Date fechaAnterior, Date fechaActual){
        return (List<Actividad>) almacenDao.listaActividadesPorDireccionYRango(id_direccion, fechaAnterior, fechaActual);
    }

    @Override
    public List<Actividad> listaActividadesPorMesAnio(int mes, int anio) {
        return (List<Actividad>) almacenDao.listaActividadesPorMesAnio(mes, anio);
    }

    @Override
    public List<Actividad> listaActividadesPorDireccion(Long id_direccion) {
        return (List<Actividad>)almacenDao.listaActividadesPorDireccion(id_direccion);
    }

    @Override
    public List<Actividad> listaActividadesPorUnidadMesAnio(Long id_unidad, int mes, int anio) {
        return (List<Actividad>)almacenDao.listaActividadesPorUnidadMesAnio(id_unidad, mes, anio);
    }

    @Override
    public List<Actividad> listaActividadesPorDireccionMesAnio(Long id_direccion, int mes, int anio) {
        return (List<Actividad>)almacenDao.listaActividadesPorDireccionMesAnio(id_direccion, mes, anio);
    }

    @Override
    public List<Actividad> listaActividadesImpactoPorFecha(Date fechaPorDia) {
        return (List<Actividad>)almacenDao.listaActividadesImpactoPorFecha(fechaPorDia);
    }

}
