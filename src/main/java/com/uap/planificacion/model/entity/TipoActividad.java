package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="tipo_actividad")
@Getter
@Setter
public class TipoActividad implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_actividad;
    @Column
    private String nombre_tipo_actividad;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoActividad", fetch = FetchType.LAZY)
	private List<Actividad> actividads;
}
