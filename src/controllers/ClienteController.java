package controllers;

import DAO.ClienteDAO;
import java.util.List;
import models.ClienteModel;

public class ClienteController {
    ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteController(){}

    public void cadastrar(String nome, String telefone, String endereco, String email){
        ClienteModel cliente = new ClienteModel();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        clienteDAO.cadastrar(cliente);
    }

    public List<ClienteModel> listar(String pesquisa){
        return clienteDAO.listar(pesquisa);
    }

    public void alterar(int codigo, String nome, String telefone, String endereco, String email){
        ClienteModel cliente = new ClienteModel();
        cliente.setCodigo(codigo);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setEmail(email);
        clienteDAO.alterar(cliente);
    }

    public void excluir(int codigo){
        clienteDAO.excluir(codigo);
    }
}
