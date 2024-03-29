package aula03.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", length = 100)
  private String nome;

  @Column(name = "cpf", length = 14)
  private String cpf;

  @Column(name = "telefone", length = 20)
  private String telefone;

  @Column(name = "telefone_celular", length = 20)
  private String telefoneCelular;

  //Define o nome da coluna e o tamanho do campo para double
  @Column(name = "renda", length = 10, precision = 2)
  private double renda;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getTelefoneCelular() {
    return telefoneCelular;
  }

  public void setTelefoneCelular(String telefoneCelular) {
    this.telefoneCelular = telefoneCelular;
  }

  public double getRenda() {
    return renda;
  }

  public void setRenda(double renda) {
    this.renda = renda;
  }

  @Override
  public String toString() {
    return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", telefoneCelular="
        + telefoneCelular + ", renda=" + renda + "]";
  }


}
