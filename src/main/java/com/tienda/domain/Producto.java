package com.tienda.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="producto_id")
    private Integer idProducto;
    private Integer idCategoria;

    @Column(unique=true, nullable=false, length=50)
    @NotBlank
    @Size(max=50)
    private String descripcion;
    
    @Column(columnDefinition="TEXT")
    private String detalle;
    
    @Column(precision=12, scale=2)
    @NotNull
    @DecimalMin(value="0.00", inclusive=true)
    private BigDecimal precio;
    
        @NotNull
    @Min(value=0)
    private Integer existencias;

    @Column(name="ruta_imagen", length=1024)
    @Size(max=1024)
    private String rutaImagen;

    @Column(nullable=false)
    private boolean activo;

}