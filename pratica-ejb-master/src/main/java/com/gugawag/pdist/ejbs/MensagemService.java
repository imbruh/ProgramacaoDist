package com.gugawag.pdist.ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.gugawag.pdist.model.Mensagem;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {

	@EJB
    private MensagemDAO msgDao;

    public List<Mensagem> listar() {
        return msgDao.listar();
    }
    
    public Mensagem pesquisarPorId(Long id) {
        return msgDao.pesquisarPorId(id);
    }

    public void inserir(long id, String texto) {
        Mensagem novaMsg = new Mensagem(id, texto);
        msgDao.inserir(novaMsg);      
    }
}
