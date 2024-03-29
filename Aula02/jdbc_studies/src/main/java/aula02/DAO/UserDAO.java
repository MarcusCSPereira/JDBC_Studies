package aula02.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import aula02.conexao.ConnectionMySql;
import aula02.entity.User;

public class UserDAO {
  
  public void cadastrar(User usuario) {

    String sql = "INSERT INTO user (nome, login, senha, email) VALUES (?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = ConnectionMySql.getConnection().prepareStatement(sql);

      ps.setString(1, usuario.getNome());
      ps.setString(2, usuario.getLogin());
      ps.setString(3, usuario.getSenha());
      ps.setString(4, usuario.getEmail());

      int rowsAffected = ps.executeUpdate();

      ps.close();

      if (rowsAffected > 0) {
        System.out.println("Usuário cadastrado com sucesso!");
      } else {
        System.out.println("Erro ao cadastrar usuário!");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
