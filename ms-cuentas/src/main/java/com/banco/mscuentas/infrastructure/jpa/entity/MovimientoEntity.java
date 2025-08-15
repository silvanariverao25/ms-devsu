// MovimientoEntity.java
package com.banco.mscuentas.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="movimiento", schema="bank")
public class MovimientoEntity {
  @Id @GeneratedValue
  @Column(name="movimiento_id", columnDefinition="UUID")
  private UUID movimientoId;

  @ManyToOne(optional=false)
  @JoinColumn(name="cuenta_id")
  private CuentaEntity cuenta;

  @Column(name="fecha", nullable=false)
  private OffsetDateTime fecha;

  @Column(name="tipo_movimiento", nullable=false, length=20) // DEPOSITO | RETIRO
  private String tipoMovimiento;

  @Column(name="valor", nullable=false)
  private BigDecimal valor;

  @Column(name="saldo", nullable=false)
  private BigDecimal saldo; // saldo resultante después del movimiento

  // getters/setters...
  public UUID getMovimientoId(){return movimientoId;}
  public void setMovimientoId(UUID id){this.movimientoId=id;}
  public CuentaEntity getCuenta(){return cuenta;}
  public void setCuenta(CuentaEntity c){this.cuenta=c;}
  public OffsetDateTime getFecha(){return fecha;}
  public void setFecha(OffsetDateTime f){this.fecha=f;}
  public String getTipoMovimiento(){return tipoMovimiento;}
  public void setTipoMovimiento(String t){this.tipoMovimiento=t;}
  public BigDecimal getValor(){return valor;}
  public void setValor(BigDecimal v){this.valor=v;}
  public BigDecimal getSaldo(){return saldo;}
  public void setSaldo(BigDecimal s){this.saldo=s;}
}
