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
@Table(name = "tipo_evaluacion")
@Getter
@Setter
public class TipoEvaluacion implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tipo_evaluacion;

    private String nombre_evaluacion; // autoevaluacion, coevaluacion, eteroevaluacion

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEvaluacion", fetch = FetchType.LAZY)
	private List<Evaluacion> evaluacions;
}
