/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

import utils.Conexao;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Limber
 */
public class FrmProduto extends javax.swing.JFrame {

    /**
     * Creates new form FrmProduto
     */
    public FrmProduto() {
        initComponents();
    }
    
    Connection conexao;
    PreparedStatement pst;
    ResultSet rs;
    
    
    public void limpar(){        
        txtCodigo.setText("");
        txtData.setText("");
        txtNome.setText("");
        txtQtdEstoque.setText("");
        txtDescricao.setText("");
        txtEstqMinimo.setText("");
        txtEstqMaximo.setText("");
        txtPrecoCompra.setText("");
        txtPrecoVenda.setText("");
        txtFatorLucro.setText("");
        txtNCM.setText("");
        txtBarcode.setText("");
        cmbStatus.setSelectedItem("I - Inativo");
        txtImagem.setText("");
        lblImagemProduto.setIcon(new ImageIcon(getClass().getResource("../img/pessoa.png")));
        txtCodigo.grabFocus();
    }
    
    public void cadastrar(){
        conexao = Conexao.obterConexao();
        
        float precoCompra = Float.parseFloat(txtPrecoCompra.getText());
        float precoVenda = Float.parseFloat(txtPrecoVenda.getText());
        float percFator;
        
        percFator = ((precoCompra/precoVenda)-1)*-100;
        System.out.println(String.format("%.2f", percFator));
        
        try{
            String sql = "insert into produto (cod, status, nome, descricao, qtd_estoque, estoque_minimo, estoque_maximo, preco_compra, preco_venda, bar_code, ncm, fator, data_cadastro, imagem) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.setString(2, cmbStatus.getSelectedItem().toString().substring(1,1));
            pst.setString(3, txtNome.getText());
            pst.setString(4, txtDescricao.getText());
            pst.setInt(5, Integer.parseInt(txtQtdEstoque.getText()));
            pst.setInt(6, Integer.parseInt(txtEstqMinimo.getText()));
            pst.setInt(7, Integer.parseInt(txtEstqMaximo.getText()));
            pst.setFloat(8, Float.parseFloat(txtPrecoCompra.getText()));
            pst.setFloat(9, Float.parseFloat(txtPrecoVenda.getText()));
            pst.setInt(10, Integer.parseInt(txtBarcode.getText()));
            pst.setInt(11, Integer.parseInt(txtNCM.getText()));
            pst.setFloat(12, percFator);
            pst.setString(13, txtData.getText());
            pst.setString(14, txtImagem.getText());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar");
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        }
        limpar();
    }
    
    public void listar(){
        conexao = Conexao.obterConexao();
        DefaultTableModel model = (DefaultTableModel) tblProduto.getModel();
        model.setNumRows(0);
        try{
            String sql = "select * from produto";
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                    rs.getString("cod"),
                    rs.getString("status"),
                    rs.getString("nome"),
                    rs.getInt("qtd_estoque"),
                    rs.getInt("estoque_minimo"),
                    rs.getInt("estoque_maximo"),
                    rs.getFloat("preco_compra"),
                    rs.getFloat("preco_venda"),
                    rs.getFloat("fator")
                });
                pst.close();
            }
        } catch(Exception e){
            System.out.println("Não foi posível realizar a listagem");
            System.out.println("Erro: " + e);
        }
    }
    
    public void alterar(){
        conexao = Conexao.obterConexao();
        try{
            String sql = "update produto set status=?, nome=?, descricao=?, qtd_estoque=?, estoque_minimo=?, estoque_maximo=?, preco_compra=?, preco_venda=?, bar_code=?, ncm=?, fator=?, data_cadastro=?, imagem=? where cod=?";
            pst = conexao.prepareStatement(sql);            
            pst.setString(1, cmbStatus.getSelectedItem().toString().substring(1,1));
            pst.setString(2, txtNome.getText());
            pst.setString(3, txtDescricao.getText());
            pst.setInt(4, Integer.parseInt(txtQtdEstoque.getText()));
            pst.setInt(5, Integer.parseInt(txtEstqMinimo.getText()));
            pst.setInt(6, Integer.parseInt(txtEstqMaximo.getText()));
            pst.setFloat(7, Float.parseFloat(txtPrecoCompra.getText()));
            pst.setFloat(8, Float.parseFloat(txtPrecoVenda.getText()));
            pst.setInt(9, Integer.parseInt(txtBarcode.getText()));
            pst.setInt(10, Integer.parseInt(txtNCM.getText()));
            pst.setFloat(11, Float.parseFloat(txtFatorLucro.getText()));
            pst.setString(12, txtData.getText());
            pst.setString(13, txtImagem.getText());
            pst.setString(14, txtCodigo.getText());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar");
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        limpar();
        listar();
    }
    
    public void excluir(){
        conexao = Conexao.obterConexao();
        try{
            String sql = "delete from produto where cod=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso");
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir");
        }
        limpar();
        listar();
    }
    
    public void selecionar() {
        conexao = Conexao.obterConexao();
    
        // Verifique se a tabela tem uma linha selecionada
        int selectedRow = tblProduto.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado.");
            return; // Saia do método se nenhuma linha estiver selecionada
        }

        // Preencher o campo de texto com o código do produto selecionado
        txtCodigo.setText(tblProduto.getValueAt(selectedRow, 0).toString());
        String codigo = tblProduto.getValueAt(selectedRow, 0).toString();
        System.out.println(codigo);
        String img;
        String sql = "SELECT * FROM produto WHERE cod=?";
        try {         
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigo);
            rs = pst.executeQuery();
                        
            try{
                // Verifique se o ResultSet contém dados
                if (rs.next()) {
                    img = rs.getString("imagem");
                    try{
                        if (img != null) {
                            try{
                                // Verifique se o caminho da imagem é válido
                                lblImagemProduto.setIcon(new ImageIcon(getClass().getResource("../img/erixku.jpg")));
                                txtImagem.setText(img);
                            } catch(Exception e){
                                JOptionPane.showMessageDialog(null, "Erro na atribuição da Imagem\n" + img);
                                
                            }                            
                        } else {
                            JOptionPane.showMessageDialog(null, "A imagem do produto é null.");
                        }
                    } catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Erro no 2º If... Else");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada para o produto.");
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro no 1º If... Else");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a imagem do produto");
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        } finally {
            // Fechar o ResultSet e PreparedStatement no bloco finally
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar recursos: " + e);
            }
        }

        // Preencher outros campos de texto com base na linha selecionada
        cmbStatus.setSelectedIndex(tblProduto.getValueAt(selectedRow, 1).toString().equals("I") ? 0 : 1);
        txtNome.setText(tblProduto.getValueAt(selectedRow, 2).toString());
        txtQtdEstoque.setText(tblProduto.getValueAt(selectedRow, 3).toString());
        txtEstqMinimo.setText(tblProduto.getValueAt(selectedRow, 4).toString());
        txtEstqMaximo.setText(tblProduto.getValueAt(selectedRow, 5).toString());
        txtPrecoCompra.setText(tblProduto.getValueAt(selectedRow, 6).toString());
        txtPrecoVenda.setText(tblProduto.getValueAt(selectedRow, 7).toString());
        txtFatorLucro.setText(tblProduto.getValueAt(selectedRow, 8).toString());
    }
    
    public void calcularFator(){
        float precoCompra = Float.parseFloat(txtPrecoCompra.getText());
        float precoVenda = Float.parseFloat(txtPrecoVenda.getText());
        float percFator;
        
        percFator = (precoCompra/precoVenda)-1;
        
        txtFatorLucro.setText(String.format("%.2f", (percFator*100*-1))+"%");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlInformacoes = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        lblData = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtQtdEstoque = new javax.swing.JTextField();
        lblQtdEstoque = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        pnlEstoquePreco = new javax.swing.JPanel();
        txtEstqMinimo = new javax.swing.JTextField();
        lblEstqMinimo = new javax.swing.JLabel();
        txtEstqMaximo = new javax.swing.JTextField();
        lblEstqMaximo = new javax.swing.JLabel();
        txtPrecoCompra = new javax.swing.JTextField();
        lblPrecoCompra = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JTextField();
        lblPrecoVenda = new javax.swing.JLabel();
        txtNCM = new javax.swing.JTextField();
        lblNCM = new javax.swing.JLabel();
        txtFatorLucro = new javax.swing.JTextField();
        lblFatorLucro = new javax.swing.JLabel();
        txtBarcode = new javax.swing.JTextField();
        lblBarcode = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        pnlImagem = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblImagemProduto = new javax.swing.JLabel();
        btnImagem = new javax.swing.JButton();
        pnlBotoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("SISTEMA | CADASTRO DE PRODUTOS");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(205, 205, 205))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTituloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addContainerGap())
        );

        pnlInformacoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCodigo.setText("Código");

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStatus.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I - Inativo", "A - Ativo" }));

        lblData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblData.setText("Data de Cadastro");

        txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        lblNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNome.setText("Nome");

        lblQtdEstoque.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblQtdEstoque.setText("Quantidade em Estoque");

        lblDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDescricao.setText("Descrição");

        javax.swing.GroupLayout pnlInformacoesLayout = new javax.swing.GroupLayout(pnlInformacoes);
        pnlInformacoes.setLayout(pnlInformacoesLayout);
        pnlInformacoesLayout.setHorizontalGroup(
            pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addComponent(lblDescricao)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                                .addComponent(lblQtdEstoque)
                                .addGap(0, 133, Short.MAX_VALUE))
                            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                                .addComponent(txtQtdEstoque)
                                .addContainerGap())))
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescricao)
                            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                                        .addComponent(lblCodigo)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtCodigo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblStatus))
                                .addGap(8, 8, 8)
                                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblData)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        pnlInformacoesLayout.setVerticalGroup(
            pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblData)
                            .addComponent(lblStatus))
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInformacoesLayout.createSequentialGroup()
                        .addComponent(lblQtdEstoque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlEstoquePreco.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblEstqMinimo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEstqMinimo.setText("Estoque mínimo");

        lblEstqMaximo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEstqMaximo.setText("Estoque máximo");

        txtPrecoCompra.setToolTipText("");

        lblPrecoCompra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPrecoCompra.setText("Preço de compra");

        lblPrecoVenda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPrecoVenda.setText("Preço de venda");

        lblNCM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNCM.setText("NCM");

        txtFatorLucro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFatorLucroMouseClicked(evt);
            }
        });

        lblFatorLucro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFatorLucro.setText("Fator lucro");

        lblBarcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblBarcode.setText("Código de Barras GTIN / EAN");

        javax.swing.GroupLayout pnlEstoquePrecoLayout = new javax.swing.GroupLayout(pnlEstoquePreco);
        pnlEstoquePreco.setLayout(pnlEstoquePrecoLayout);
        pnlEstoquePrecoLayout.setHorizontalGroup(
            pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstqMinimo)
                            .addComponent(txtEstqMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecoCompra)
                            .addComponent(txtFatorLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFatorLucro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstqMaximo)
                            .addComponent(txtEstqMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecoVenda)
                            .addComponent(txtNCM, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNCM)))
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblBarcode)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtBarcode))
                .addContainerGap())
        );
        pnlEstoquePrecoLayout.setVerticalGroup(
            pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblEstqMaximo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstqMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblEstqMinimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstqMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblPrecoVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblPrecoCompra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlEstoquePrecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblNCM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNCM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEstoquePrecoLayout.createSequentialGroup()
                        .addComponent(lblFatorLucro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFatorLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtPrecoCompra.getAccessibleContext().setAccessibleName("");

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Status", "Nome", "Qtd Est", "Qtd Min", "Qtd Max", "Preço Com.", "Preço Ven.", "Fator %"
            }
        ));
        tblProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduto);
        if (tblProduto.getColumnModel().getColumnCount() > 0) {
            tblProduto.getColumnModel().getColumn(0).setResizable(false);
            tblProduto.getColumnModel().getColumn(1).setResizable(false);
            tblProduto.getColumnModel().getColumn(2).setResizable(false);
            tblProduto.getColumnModel().getColumn(3).setResizable(false);
            tblProduto.getColumnModel().getColumn(4).setResizable(false);
            tblProduto.getColumnModel().getColumn(5).setResizable(false);
            tblProduto.getColumnModel().getColumn(6).setResizable(false);
            tblProduto.getColumnModel().getColumn(7).setResizable(false);
            tblProduto.getColumnModel().getColumn(8).setResizable(false);
        }

        pnlImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Imagem do Produto");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblImagemProduto.setBackground(new java.awt.Color(170, 170, 170));
        lblImagemProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pessoa.png"))); // NOI18N

        btnImagem.setText("Nova Imagem");
        btnImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlImagemLayout = new javax.swing.GroupLayout(pnlImagem);
        pnlImagem.setLayout(pnlImagemLayout);
        pnlImagemLayout.setHorizontalGroup(
            pnlImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlImagemLayout.createSequentialGroup()
                        .addComponent(btnImagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnlImagemLayout.createSequentialGroup()
                        .addGroup(pnlImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblImagemProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        pnlImagemLayout.setVerticalGroup(
            pnlImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagemProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlBotoes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setBackground(new java.awt.Color(38, 34, 97));
        btnNovo.setForeground(new java.awt.Color(204, 204, 255));
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnAlterar.setBackground(new java.awt.Color(38, 34, 97));
        btnAlterar.setForeground(new java.awt.Color(204, 204, 255));
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnApagar.setBackground(new java.awt.Color(38, 34, 97));
        btnApagar.setForeground(new java.awt.Color(204, 204, 255));
        btnApagar.setText("Apagar");

        btnLimpar.setBackground(new java.awt.Color(38, 34, 97));
        btnLimpar.setForeground(new java.awt.Color(204, 204, 255));
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(38, 34, 97));
        btnImprimir.setForeground(new java.awt.Color(204, 204, 255));
        btnImprimir.setText("Imprimir");

        btnSair.setBackground(new java.awt.Color(38, 34, 97));
        btnSair.setForeground(new java.awt.Color(204, 204, 255));
        btnSair.setText("Sair");

        btnAtualizar.setBackground(new java.awt.Color(38, 34, 97));
        btnAtualizar.setForeground(new java.awt.Color(204, 204, 255));
        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotoesLayout = new javax.swing.GroupLayout(pnlBotoes);
        pnlBotoes.setLayout(pnlBotoesLayout);
        pnlBotoesLayout.setHorizontalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBotoesLayout.setVerticalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlEstoquePreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlImagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlEstoquePreco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        cadastrar();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtFatorLucroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFatorLucroMouseClicked
        // TODO add your handling code here:
        calcularFator();
    }//GEN-LAST:event_txtFatorLucroMouseClicked

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // TODO add your handling code here:
        listar();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void tblProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoMouseClicked
        // TODO add your handling code here:
        selecionar();
    }//GEN-LAST:event_tblProdutoMouseClicked

    private void btnImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagemActionPerformed
        // TODO add your handling code here:
       JFileChooser chooser = new JFileChooser();
       chooser.showOpenDialog(null);
       File f = chooser.getSelectedFile();
       String path = f.getAbsolutePath();
       try{
            BufferedImage bi = ImageIO.read(new File(path));
            Image img = bi.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(img);
            lblImagemProduto.setIcon(icon);
       }catch (IOException ex){
           System.out.println("ERRO: " + ex.toString());
           
       }
    }//GEN-LAST:event_btnImagemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnImagem;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblEstqMaximo;
    private javax.swing.JLabel lblEstqMinimo;
    private javax.swing.JLabel lblFatorLucro;
    private javax.swing.JLabel lblImagemProduto;
    private javax.swing.JLabel lblNCM;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPrecoCompra;
    private javax.swing.JLabel lblPrecoVenda;
    private javax.swing.JLabel lblQtdEstoque;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlEstoquePreco;
    private javax.swing.JPanel pnlImagem;
    private javax.swing.JPanel pnlInformacoes;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtEstqMaximo;
    private javax.swing.JTextField txtEstqMinimo;
    private javax.swing.JTextField txtFatorLucro;
    private javax.swing.JTextField txtNCM;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoCompra;
    private javax.swing.JTextField txtPrecoVenda;
    private javax.swing.JTextField txtQtdEstoque;
    // End of variables declaration//GEN-END:variables
}
