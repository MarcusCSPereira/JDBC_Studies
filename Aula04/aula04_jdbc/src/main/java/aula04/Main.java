package aula04;

import java.io.IOException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import aula04.model.Cliente;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aula04PU");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        clearConsole();

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Adicionar cliente");
            System.out.println("2. Remover cliente");
            System.out.println("3. Atualizar cliente por CPF");
            System.out.println("4. Ver cadastro por CPF");
            System.out.println("5. Sair");
          
            System.out.print("Escolha uma opção:");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada

            switch (opcao) {
                case 1:
                    adicionarCliente(em, scanner);
                    break;
                case 2:
                    removerCliente(em, scanner);
                    break;
                case 3:
                    atualizarCliente(em, scanner);
                    break;
                case 4:
                    verCadastroPorCPF(em, scanner);
                    break;
                case 5:
                    em.close();
                    emf.close();
                    scanner.close();
                    System.out.println("Programa encerrado.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarCliente(EntityManager em, Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite a idade do cliente:");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        if(buscarClientePorCPF(em, cpf) != null) {
          System.out.println("CPF já cadastrado.");
        }else{
          Cliente cliente = new Cliente(nome, idade, cpf);
          em.getTransaction().begin();
          em.persist(cliente);
          em.getTransaction().commit();
          System.out.println("Cliente adicionado com sucesso.");
          System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void removerCliente(EntityManager em, Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("Digite o CPF do cliente que deseja remover:");
        String cpf = scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        if(buscarClientePorCPF(em, cpf) == null) {
          System.out.println("Cliente não existente.");
        }else{
          Cliente cliente = em.find(Cliente.class, buscarClientePorCPF(em, cpf).getId());
          em.getTransaction().begin();
          em.remove(cliente);
          em.getTransaction().commit();
          System.out.println("Cliente removido com sucesso.");
          System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void atualizarCliente(EntityManager em, Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("Digite o CPF do cliente que deseja atualizar:");
        String cpf = scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        if (buscarClientePorCPF(em, cpf) == null){
          System.out.println("Cliente não existente.");
        }else{
          Cliente cliente = em.find(Cliente.class, buscarClientePorCPF(em, cpf).getId());
          System.out.println("Digite o novo nome do cliente:");
          String novoNome = scanner.nextLine();
          System.out.println("Digite a nova idade do cliente:");
          int novaIdade = scanner.nextInt();
          scanner.nextLine(); // Limpar o buffer de entrada

          em.getTransaction().begin();
          cliente.setNome(novoNome);
          cliente.setIdade(novaIdade);
          em.getTransaction().commit();
          System.out.println("Cadastro atualizado com sucesso.");
        }
          System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }

    private static void verCadastroPorCPF(EntityManager em, Scanner scanner) {
        System.out.println("--------------------------");
        System.out.println("Digite o CPF do cliente que deseja visualizar:");
        String cpf = scanner.nextLine();

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        if(buscarClientePorCPF(em, cpf) == null) {
          System.out.println("Cliente não existente.");
        }else{
          Cliente cliente = em.find(Cliente.class, buscarClientePorCPF(em, cpf).getId());
          System.out.println(cliente);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }

    // Método para buscar o cliente pelo CPF
    private static Cliente buscarClientePorCPF(EntityManager em, String cpf) {
      // Consulta JPQL para buscar o cliente pelo CPF 
      String jpql = "SELECT c FROM Cliente c WHERE c.cpf = :cpf";

      // Criar a consulta com a JPQL(Java Persistence Query Language)
      TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
      query.setParameter("cpf", cpf);

      try {
          // Tentar obter o resultado da consulta
          return query.getSingleResult();
      } catch (NoResultException e) {
          // Tratar caso não seja encontrado nenhum cliente com o CPF fornecido
          return null;
      }
    }

    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // Se for Windows, use "cls"
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Se não for Windows, use "clear"
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (final InterruptedException | IOException e) {
            // Tratar exceções, se necessário
            e.printStackTrace();
        }
    }

    

}
