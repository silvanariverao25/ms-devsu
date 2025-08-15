// CuentaEntity.java
package com.banco.mscuentas.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="cuenta", schema="bank")
public class CuentaEntity {
  @Id @GeneratedValue
  @Column(name="cuenta_id", columnDefinition="UUID")
  private UUID cuentaId;

  @Column(name="cliente_id", columnDefinition="UUID", nullable=false)
  private UUID clienteId; // viene de ms-clientes

  @Column(name="numero_cuenta", nullable=false, unique=true, length=30)
  private String numeroCuenta;

  @Column(name="tipo_cuenta", nullable=false, length=20) // AHORROS | CORRIENTE
  private String tipoCuenta;

  @Column(name="saldo_inicial", nullable=false)
  private BigDecimal saldoInicial;

  @Column(name="estado", nullable=false) // 1=activa,0=inhabilitada
  private short estado;

  // getters/setters...
  public UUID getCuentaId(){return cuentaId;}
  public void setCuentaId(UUID id){this.cuentaId=id;}
  public UUID getClienteId(){return clienteId;}
  public void setClienteId(UUID id){this.clienteId=id;}
  public String getNumeroCuenta(){return numeroCuenta;}
  public void setNumeroCuenta(String n){this.numeroCuenta=n;}
  public String getTipoCuenta(){return tipoCuenta;}
  public void setTipoCuenta(String t){this.tipoCuenta=t;}
  public BigDecimal getSaldoInicial(){return saldoInicial;}
  public void setSaldoInicial(BigDecimal s){this.saldoInicial=s;}
  public short getEstado(){return estado;}
  public void setEstado(short e){this.estado=e;}
}
