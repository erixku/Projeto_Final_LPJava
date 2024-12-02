package models;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ProdutoModel {
    private String cod, status, nome, descricao, ncm, caminho;
    private Date data_cadastro;
    private float fator, preco_compra, preco_venda;
    private long bar_code;
    private int qtd_estoque, estoque_maximo, estoque_minimo;
    private Icon imagem;
    
    public String getCod() {
        return cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getNcm() {
        return ncm;
    }
    public void setNcm(String ncm) {
        this.ncm = ncm;
    }
    public Date getData_cadastro() {
        return data_cadastro;
    }
    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
    public float getFator() {
        return fator;
    }
    public void setFator(float fator) {
        this.fator = fator;
    }
    public float getPreco_compra() {
        return preco_compra;
    }
    public void setPreco_compra(float preco_compra) {
        this.preco_compra = preco_compra;
    }
    public float getPreco_venda() {
        return preco_venda;
    }
    public void setPreco_venda(float preco_venda) {
        this.preco_venda = preco_venda;
    }
    public long getBar_code() {
        return bar_code;
    }
    public void setBar_code(long bar_code) {
        this.bar_code = bar_code;
    }
    public int getQtd_estoque() {
        return qtd_estoque;
    }
    public void setQtd_estoque(int qtd_estoque) {
        this.qtd_estoque = qtd_estoque;
    }
    public int getEstoque_maximo() {
        return estoque_maximo;
    }
    public void setEstoque_maximo(int estoque_maximo) {
        this.estoque_maximo = estoque_maximo;
    }
    public int getEstoque_minimo() {
        return estoque_minimo;
    }
    public void setEstoque_minimo(int estoque_minimo) {
        this.estoque_minimo = estoque_minimo;
    }
    public Icon getImagem() {
        return imagem;
    }
    public void setImagem(Icon imagem) {
         this.imagem = imagem;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}