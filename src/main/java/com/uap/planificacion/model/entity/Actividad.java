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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="actividad")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Actividad implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_actividad;
    @Column(length = 10485760)
    private String nombre_actividad;
    @Column(length = 10485760)
    private String descripcion_actividad;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_registro;
    private Double evaluacion_actividad_total;
    private String estado;
    private Boolean programdo;
    private String usuarioRegistro;
    @Column
    private String avance_actividad;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<Responsable> responsables;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
    private List<Evento> eventos;


    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_funcional")
    private UnidadFuncional unidadFuncional;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_afluencia")
    private Afluencia afluencia;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_actividad")
    private TipoActividad tipoActividad;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<Evaluacion> evaluacions;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<DetalleActividad> detalleActividads;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
	private List<Programacion> programacions;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_actividad")
    private EstadoActividad estadoActividad;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "actividad", fetch = FetchType.LAZY)
	private Transmision transmision;
}
