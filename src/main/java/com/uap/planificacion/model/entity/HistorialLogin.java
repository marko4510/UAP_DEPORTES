package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "historial_login")
@Getter
@Setter
public class HistorialLogin implements Serializable {
    
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historial;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_ingreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal_administrativo")
    private PersonalAdministrativo personalAdministrativo;

}