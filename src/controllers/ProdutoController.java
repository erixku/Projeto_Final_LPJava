package controllers;

import java.util.List;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Date;

import DAO.ProdutoDAO;
import javax.swing.Icon;
import models.ProdutoModel;

public class ProdutoController {
    ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoController(){}

    public void cadastrar(String cod, String status, String nome, String descricao, int qtd_estoque, int estoque_minimo, int estoque_maximo, float preco_compra, float preco_venda, long bar_code, String ncm, Date data, String caminho) {
        ProdutoModel produto = new ProdutoModel();
        produto.setCod(cod);
        produto.setStatus(status);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setQtd_estoque(qtd_estoque);
        produto.setEstoque_minimo(estoque_minimo);
        produto.setEstoque_maximo(estoque_maximo);
        produto.setPreco_compra(preco_compra);
        produto.setPreco_venda(preco_venda);
        produto.setBar_code(bar_code);
        produto.setNcm(ncm);
        produto.setData_cadastro(data);
        produto.setCaminho(caminho);
        produtoDAO.cadastrar(produto);
    }

    public List<ProdutoModel> listar(){
        return produtoDAO.listar();
    }

    public void alterar(String cod, String status, String nome, String descricao, int qtd_estoque, int estoque_minimo, int estoque_maximo, float preco_compra, float preco_venda, long bar_code, String ncm, Date data, String caminho) {
        ProdutoModel produto = new ProdutoModel();
        produto.setCod(cod);
        produto.setStatus(status);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setQtd_estoque(qtd_estoque);
        produto.setEstoque_minimo(estoque_minimo);
        produto.setEstoque_maximo(estoque_maximo);
        produto.setPreco_compra(preco_compra);
        produto.setPreco_venda(preco_venda);
        produto.setBar_code(bar_code);
        produto.setNcm(ncm);
        produto.setData_cadastro(data);
        produto.setCaminho(caminho);
        produtoDAO.alterar(produto);
    }

    public void excluir(String codigo){
        produtoDAO.excluir(codigo);
    }

    public void inserirImagem(FileInputStream imagem, int tamanho){
        ProdutoModel produto = new ProdutoModel();
        produto.setImagem(imagem);
        produto.setTamanho(tamanho);
    }
}
