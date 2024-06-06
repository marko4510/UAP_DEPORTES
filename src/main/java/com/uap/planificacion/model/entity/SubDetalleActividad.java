package com.uap.planificacion.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "sub_detalle_actividad")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubDetalleActividad implements Serializable{
    private static final long serialVersionUID = 2629195288020321924L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sub_detalle_actividad;

    @Column
    @Temporal(TemporalType.TIME)
    private Date hora_inicio;

    @Column
    @Temporal(TemporalType.TIME)
    private Date hora_final;

    private String estado;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_actividad")
    private DetalleActividad detalleActividad;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="lugar_subdetalleactividad",
    joinColumns=@JoinColumn(name = "id_sub_detalle_actividad"),
    inverseJoinColumns = @JoinColumn(name = "id_lugar"))
    private Set<Lugar> lugares;
    
}
