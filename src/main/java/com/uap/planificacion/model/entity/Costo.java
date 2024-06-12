package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="costo")
@Getter
@Setter
public class Costo implements Serializable{
    

    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_costo;

    @Column(length = 10485760)
    private String nombre_actividad;


    @Column(length = 10485760)
    private String descripcion_actividad;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;
   
}
