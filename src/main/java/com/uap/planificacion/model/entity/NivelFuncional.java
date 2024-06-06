package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "nivel_funcional")
@Getter
@Setter
public class NivelFuncional implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_nivel_funcional;

    private String nom_nivel;

    private String estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelFuncional", fetch = FetchType.LAZY)
	private List<UnidadFuncional> unidadFuncionals;
}
