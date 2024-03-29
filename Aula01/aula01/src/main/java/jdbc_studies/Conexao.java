package jdbc_studies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Conexao {
  public static void main(String[] args) {

    Connection conexao = null;
    boolean clienteCadastrado = false;

    System.out.println("Digite o nome do cliente: ");
    Scanner scanner = new Scanner(System.in);
    String nome = scanner.nextLine();

    try {
      // Carrega o driver JDBC
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Conecta ao banco de dados a partir da URL contendo a tabela, usuário e senha
      conexao = DriverManager.getConnection("jdbc:mysql://localhost/aula1_jdbc", "root", "Mc34601373#");

      // Cria um objeto de declaração SQL para executar comandos SQL
      // Executa a consulta SQL para obter o nome dos clientes
      ResultSet consultar = conexao.createStatement().executeQuery("SELECT * FROM cliente");

      // Verifica se o cliente já está cadastrado
      while (consultar.next()) {

        // Se o cliente já estiver cadastrado, exibe uma mensagem
        if (consultar.getString("nome").equals(nome)) {
          clienteCadastrado = true;
          System.out.println("Cliente já cadastrado");
          break;
        }
      }

      // Se o cliente não estiver cadastrado, insere o novo cliente
      if (!clienteCadastrado) {
        PreparedStatement inserir = conexao.prepareStatement("INSERT INTO cliente (nome) VALUES (?)");

        // Define o nome do novo cliente, pois o id é autoincrementado
        inserir.setString(1, nome);
        // Executa a consulta SQL para inserir o novo cliente e recupera o número de
        // linhas afetadas, caso seja maior que 0, exibe uma mensagem de sucesso
        int linhasAfetadas = inserir.executeUpdate();

        // Exibe uma mensagem de sucesso ou falha ao adicionar o cliente
        if (linhasAfetadas > 0) {
          System.out.println("\nCliente adicionado com sucesso!");
        } else {
          System.out.println("Falha ao adicionar cliente.");
        }
      }

      System.out.println("\nTodos os Clientes cadastrados: ");
      // Consulta todos os clientes cadastrados
      ResultSet consultarCliente = conexao.createStatement().executeQuery("SELECT * FROM cliente");

      // Exibe o ID e o nome de todos os clientes cadastrados
      while (consultarCliente.next()) {
        System.out.println("ID: " + consultarCliente.getInt("idcliente"));
        System.out.println("Nome: " + consultarCliente.getString("nome"));
        System.out.println("________________________");
      }

      // Remover cliente
      System.out.println("\nDigite o nome do cliente que deseja remover: ");
      String nomeRemover = scanner.nextLine();

      // Prepara a consulta SQL para remover o cliente
      PreparedStatement removerCliente = conexao.prepareStatement("DELETE FROM cliente WHERE nome = ?");
      // Define o nome do cliente a ser removido
      removerCliente.setString(1, nomeRemover);
      // Executa a consulta SQL para remover o cliente e recupera o número de linhas
      // afetadas
      int linhasRemovidas = removerCliente.executeUpdate();

      // Exibe uma mensagem de sucesso ou falha ao remover o cliente
      if (linhasRemovidas > 0) {
        System.out.println("Cliente removido com sucesso!");
      } else {
        System.out.println("Não foi possível remover o cliente.");
      }

      // Exibe todos os clientes cadastrados após a remoção
      System.out.println("\nTodos os Clientes cadastrados após a remoção:");

      // Consulta todos os clientes cadastrados
      consultarCliente = conexao.createStatement().executeQuery("SELECT * FROM cliente");

      // Exibe o ID e o nome de todos os clientes cadastrados após a remoção
      while (consultarCliente.next()) {
        System.out.println("ID: " + consultarCliente.getInt("idcliente"));
        System.out.println("Nome: " + consultarCliente.getString("nome"));
        System.out.println("________________________");
      }

      // Tratamento de exceções
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("Driver não encontrado");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Erro ao conectar ao banco de dados");
    } finally {

      if (conexao != null) {
        try {
          conexao.close();
          scanner.close();
          System.out.println("\nConexão fechada com sucesso");
        } catch (SQLException e) {
          e.printStackTrace();
          System.out.println("Erro ao fechar a conexão");
        }
      }

    }

  }
}