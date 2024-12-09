package controllers;

import models.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DAO.LoginDAO;
import utils.Conexao;

public class LoginController {
    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;

    conexao = Conexao.obterConexao();
    LoginDAO loginDAO = new LoginDAO();
    public void logar(String senha, String usuario){
        LoginModel login = new LoginModel();
        login.setUsuario(usuario);
        login.setSenha(senha);
        loginDAO.logar();
    }
}
