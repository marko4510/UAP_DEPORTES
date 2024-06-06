package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detalle_actividad")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalleActividad implements Serializable {
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detalle_actividad;
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha_detalle_actividad;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_modificacion;

    private String estado;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detalleActividad", fetch = FetchType.LAZY)
	private List<SubDetalleActividad> subDetalleActividads;
}
