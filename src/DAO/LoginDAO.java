package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import utils.Conexao;
import view.FrmCadastro;
import view.FrmLogin;
import view.FrmMenu;
import models.LoginModel;
import utils.Conexao;

public class LoginDAO {
    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;

    public void logar(){
        conexao = Conexao.obterConexao();
        LoginModel login = new LoginModel();
        FrmLogin frmLogin = new FrmLogin();
        String sql = "select * from login" + "where usuario=? and senha=?";
        
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, login.getUsuario());
            pst.setString(2, login.getSenha());
            rs = pst.executeQuery();
            if(rs.next()){
                FrmMenu menu = new FrmMenu();
                menu.setVisible(true);
                frmLogin.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuários ou senha inválida");
                login.setUsuario("");
                login.setSenha("");
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }        
    }

    public void cadastrar(){
        conexao = Conexao.obterConexao();
        LoginModel login = new LoginModel();
        FrmCadastro frmCadastro = new FrmCadastro();
        String sql = "insert into login(nome, usuario, senha) values(?, ?, ?)";

        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, login.getNome());
            pst.setString(2, login.getUsuario());
            pst.setString(3, login.getSenha());
            pst.executeQuery();

            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");

            FrmLogin frmLogin = new FrmLogin();
            frmCadastro.dispose();
            frmLogin.setVisible(true);

            login.setNome("");
            login.setUsuario("");
            login.setSenha("");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
