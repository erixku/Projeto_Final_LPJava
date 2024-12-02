package DAO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.*;

import javax.swing.JOptionPane;

import utils.Conexao;
import models.ProdutoModel;

public class ProdutoDAO {

    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;
    
    ProdutoModel produto;
    public void cadastrar(ProdutoModel produto){
        conexao = Conexao.obterConexao();
        
        float precoCompra = produto.getPreco_compra();
        float precoVenda = produto.getPreco_venda();
        float percFator;
        
        percFator = ((precoCompra/precoVenda)-1)*-100;
        System.out.println(String.format("%.2f", percFator));
        
        try{
            String sql = "insert into produto (cod, status, nome, descricao, qtd_estoque, estoque_minimo, estoque_maximo, preco_compra, preco_venda, bar_code, ncm, fator, data_cadastro, imagem) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, produto.getCod());
            pst.setString(2, produto.getStatus());
            pst.setString(3, produto.getNome());
            pst.setString(4, produto.getDescricao());
            pst.setInt(5, produto.getQtd_estoque());
            pst.setInt(6, produto.getEstoque_minimo());
            pst.setInt(7, produto.getEstoque_maximo());
            pst.setFloat(8, produto.getPreco_compra());
            pst.setFloat(9, produto.getPreco_venda());
            pst.setLong(10, produto.getBar_code());
            pst.setString(11, produto.getNcm());
            pst.setFloat(12, percFator);
            pst.setString(13, produto.getData_cadastro().toString());
            pst.setBinaryStream(14, produto.getImagem(), produto.getTamanho());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
    }

    public List<ProdutoModel> listar(){
        conexao = Conexao.obterConexao();
        List<ProdutoModel> lista = new ArrayList<>();
        try{
            String sql = "select * from produto";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                ProdutoModel produto = new ProdutoModel();
                produto.setCod(rs.getString("cod"));
                produto.setStatus(rs.getString("status"));
                produto.setData_cadastro(rs.getDate("data_cadastro"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQtd_estoque(rs.getInt("qtd_estoque"));
                produto.setEstoque_minimo(rs.getInt("estoque_minimo"));
                produto.setEstoque_maximo(rs.getInt("estoque_maximo"));
                produto.setPreco_compra(rs.getFloat("preco_compra"));;
                produto.setPreco_venda(rs.getFloat("preco_venda"));;
                produto.setFator(rs.getFloat("fator"));
                lista.add(produto);
            }
            pst.close();
        } catch(Exception e){
            System.out.println("Não foi posível realizar a listagem");
            System.out.println("Erro: " + e);
        }
        return lista;
    }

    public void alterar(ProdutoModel produto){
        conexao = Conexao.obterConexao();
        try{
            String sql = "update produto set status=?, nome=?, descricao=?, qtd_estoque=?, estoque_minimo=?, estoque_maximo=?, preco_compra=?, preco_venda=?, bar_code=?, ncm=?, fator=?, data_cadastro=?, imagem=? where cod=?";
            pst = conexao.prepareStatement(sql);            
            pst.setString(1, produto.getStatus());
            pst.setString(2, produto.getNome());
            pst.setString(3, produto.getDescricao());
            pst.setInt(4, produto.getQtd_estoque());
            pst.setInt(5, produto.getEstoque_minimo());
            pst.setInt(6, produto.getEstoque_maximo());
            pst.setFloat(7, produto.getPreco_compra());
            pst.setFloat(8, produto.getPreco_venda());
            pst.setLong(9, produto.getBar_code());
            pst.setString(10, produto.getNcm());
            pst.setFloat(11, produto.getFator());
            pst.setDate(12, produto.getData_cadastro());
            pst.setBinaryStream(13, produto.getImagem(), produto.getTamanho());
            pst.setString(14, produto.getCod());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar");
        }
    }

    public void excluir(String codigo){
        conexao = Conexao.obterConexao();
        try{
            String sql = "delete from produto where cod=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigo);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir");
        }
    }
}
