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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personal_administrativo")
@Getter
@Setter
public class PersonalAdministrativo implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_personal_administrativo;

    private Integer cod_funcionario;
    
    private String cargo_funcionario; 

    private String estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalAdministrativo", fetch = FetchType.LAZY)
	private List<Responsable> responsables;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_funcional")
	private UnidadFuncional unidadFuncional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalAdministrativo", fetch = FetchType.LAZY)
	private List<HistorialLogin> historiales;
}
