package models;

import java.sql.Blob;

public class ProdutoModel {
    private String cod, status, nome, descricao, ncm, data_cadastro;
    private float fator, preco_compra, preco_venda;
    private long bar_code;
    private int qtd_estoque, estoque_maximo, estoque_minimo;
    private Blob imagem;
    
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
    public String getData_cadastro() {
        return data_cadastro;
    }
    public void setData_cadastro(String data_cadastro) {
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
    public Blob getImagem() {
        return imagem;
    }
    public void setImagem(Blob imagem) {
        this.imagem = imagem;
    }
}