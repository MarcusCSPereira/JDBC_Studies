package aula03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import aula03.model.Cliente;

public class Main {
    public static void main(String[] args) {

      EntityManagerFactory emf = Persistence.createEntityManagerFactory("aula3PU");

      EntityManager em = emf.createEntityManager();

      //Insere um novo registro no banco de dados ou atualiza um registro existente
      Cliente cliente = new Cliente();
      cliente.setNome("Marcus César");
      //Caso já possua a chave primária, o método merge atualiza o registro com um update, caso contrário, insere um novo registro no banco de dados.
      cliente.setId(6);
      cliente.setCpf("123.456.789-00");
      cliente.setTelefone("11 1234-5678");
      cliente.setTelefoneCelular("11 98765-4321");
      cliente.setRenda(1000.0);

      em.getTransaction().begin();
      try {
        em.merge(cliente);
        em.getTransaction().commit();
        System.out.println("Cliente salvo com sucesso!");
      } catch (Exception e) {
        e.printStackTrace();
        em.getTransaction().rollback();
      }


      //Busca um registro no banco de dados pelo id informado no método find da classe EntityManager e retorna um objeto do tipo Cliente ou null caso não encontre o registro.
      Cliente cliente2 = em.find(Cliente.class, 6);

      //Exibe o registro encontrado no banco de dados pelo metodo toString da classe Cliente
      if (cliente2 != null) {
        System.out.println("Cliente" + cliente2);
      }

      //Remove um registro do banco de dados  pelo id informado no método find da classe EntityManager e retorna um objeto do tipo Cliente ou null caso não encontre o registro.
      Cliente cliente3 = em.find(Cliente.class, 5);
      if (cliente3 != null) {
        em.getTransaction().begin();
        try {
          em.remove(cliente3);
          em.getTransaction().commit();
          System.out.println("Cliente removido com sucesso!");
        } catch (Exception e) {
          e.printStackTrace();
          em.getTransaction().rollback();
        }
      }
      


    }
}