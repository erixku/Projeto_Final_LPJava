package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import models.ClienteModel;
import utils.Conexao;

public class ClienteDAO {
    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;

    public void cadastrar(ClienteModel cliente){
        Connection conexao = Conexao.obterConexao();
        try{
            String sql = "insert into cliente (nome, telefone, endereco, email) values (?,?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getEndereco());
            pst.setString(4, cliente.getEmail());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
        }
    }

    public List<ClienteModel> listar(String pesquisa){
        conexao = Conexao.obterConexao();
        List<ClienteModel> lista = new ArrayList<>();
        try{
            String sql = "select * from cliente where nome like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, pesquisa+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                ClienteModel cliente = new ClienteModel();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
            }
            pst.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    public void alterar(ClienteModel cliente){
        conexao = Conexao.obterConexao();
        try{
            String sql = "update cliente set nome=?, telefone=?, endereco=?, email=? where codigo=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getEndereco());
            pst.setString(4, cliente.getEmail());
            pst.setInt(5, cliente.getCodigo());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar");
        }
    }

    public void excluir(int codigo){
        conexao = Conexao.obterConexao();
        try{
            String sql = "delete from cliente where codigo=?";
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir");
        }
    }
}
