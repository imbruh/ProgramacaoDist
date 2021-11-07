package com.gugawag.rpc.banco;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoServiceIF extends Remote {

    double saldo(String conta) throws RemoteException;
    String quantidadeContas() throws RemoteException;
    String criarConta(String conta, double saldo) throws RemoteException;
    String criarContaClass(String conta, double saldo) throws RemoteException;
}
