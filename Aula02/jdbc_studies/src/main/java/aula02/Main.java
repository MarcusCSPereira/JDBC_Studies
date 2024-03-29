package aula02;

import aula02.DAO.UserDAO;
import aula02.entity.User;

public class Main {
    public static void main(String[] args) {
      User usuario = new User("Marcus César", "cesinha", "123456", "marcus.iga100@gmail.com");

      new UserDAO().cadastrar(usuario);
    }
}