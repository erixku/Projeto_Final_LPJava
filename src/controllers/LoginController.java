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

    LoginDAO loginDAO = new LoginDAO();
    public void logar(String senha, String usuario){
        conexao = Conexao.obterConexao();
        LoginModel login = new LoginModel();
        login.setUsuario(usuario);
        login.setSenha(senha);
        loginDAO.logar();
    }

    public void cadastrar(String nome, String usuario, String senha){
        LoginModel login = new LoginModel();
        login.setNome(nome);
        login.setUsuario(usuario);
        login.setSenha(senha);
        loginDAO.cadastrar();
    }
}
