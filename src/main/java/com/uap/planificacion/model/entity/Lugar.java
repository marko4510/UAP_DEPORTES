package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lugar")
@Getter
@Setter
public class Lugar implements Serializable {
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lugar;
    @Column
    private String nombre_lugar;
    @Column
    private String tipo_lugar;
    @Column
    private String estado_lugar;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "lugares", fetch = FetchType.LAZY)
	private List<SubDetalleActividad> subDetalleActividads;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lugar", fetch = FetchType.LAZY)
	private List<Programacion> programacions;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lugar", fetch = FetchType.LAZY)
	private List<Evento> eventos;
}
