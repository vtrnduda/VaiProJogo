package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Jogo;

public class JogoDAO extends DAO<Jogo> {

    public Jogo read(Object chave) {
        try {
            Long id = (Long) chave;
            TypedQuery<Jogo> q = manager.createQuery("select j from Jogo j where j.id = :id", Jogo.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // -------------------------------------------- 
    // consultas
    // -------------------------------------------- 

    public List<Jogo> jogosComMaisDeXIngressosVendidos(int qtdIngressos) {
        TypedQuery<Jogo> q = manager.createQuery(
                "select j from Jogo j where size(j.listaIngressos) > :qtd", Jogo.class);
        q.setParameter("qtd", qtdIngressos);
        return q.getResultList();
    }
}