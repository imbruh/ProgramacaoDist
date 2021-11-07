package com.gugawag.rpc.banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private Map<String, Double> saldoContas;
    public List<Conta> contas = new ArrayList<>();

    public BancoServiceServer() throws RemoteException {
        saldoContas = new HashMap<>();
        
        saldoContas.put("1", 100.0);
        saldoContas.put("2", 156.0);
        saldoContas.put("3", 950.0);
    }
    

    @Override
    public double saldo(String conta) throws RemoteException {
        return saldoContas.get(conta);
    }

    @Override
    public String quantidadeContas() throws RemoteException {
        return "qnt hash: " + saldoContas.size()
        		+ "\nqnt class: " + contas.size();
    }
    
    @Override
    public String criarConta(String conta, double saldo) throws RemoteException {
    	saldoContas.put(conta, saldo);
        return "Conta criada com sucesso.";
    }
    
    @Override
    public String criarContaClass(String conta, double saldo) throws RemoteException {
    	contas.add(new Conta(conta, saldo));
        return "Conta criada com sucesso.";
    }

}
