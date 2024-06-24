package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "unidad_funcional")
@Getter
@Setter
public class UnidadFuncional implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_unidad_funcional;

    private String nom_unidad;

    private String nom_telefono;

    private String estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadFuncional", fetch = FetchType.LAZY)
    private List<PersonalAdministrativo> personalAdministrativos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion_funcional")
    private DireccionFuncional direccionFuncional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nivel_funcional")
    private NivelFuncional nivelFuncional;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadFuncional", fetch = FetchType.LAZY)
	private List<Actividad> actividads;
}
