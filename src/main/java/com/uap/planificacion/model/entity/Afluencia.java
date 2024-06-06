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
@Table(name="afluencia")
@Getter
@Setter
public class Afluencia implements Serializable {
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_afluencia;

    @Column
    private String nombre_afluencia;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "afluencia", fetch = FetchType.LAZY)
	private List<Actividad> actividads;
}
