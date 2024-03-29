package aula04.model;

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
  private Integer id;

  @Column(name = "cpf", length = 14, nullable = false, unique = true)
  private String cpf;
  
  @Column(name = "idade", nullable = false)
  private Integer idade;

  @Column(name = "nome", length = 100, nullable = false)
  private String nome;

  public Cliente() {
  }

  public Cliente(String nome, Integer idade, String cpf) {
    this.nome = nome;
    this.idade = idade;
    this.cpf = cpf;
  }

  public Integer getId() {return id;}

  public void setId(Integer id) {this.id = id;}

  public String getNome() {return nome;}

  public void setNome(String nome) {this.nome = nome;}

  public Integer getIdade() {return idade;}

  public void setIdade(Integer idade) {this.idade = idade;}

  public String getCpf() {return cpf;}

  public void setCpf(String cpf) {this.cpf = cpf;}

  @Override
  public String toString() {
    return "Cliente: [Id=" + id + "| Nome=" + nome + "| Idade=" + idade + "| Cpf=" + cpf + "]";
  }


}
