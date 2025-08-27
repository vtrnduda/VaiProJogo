/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Projeto: Vai Pro Jogo
 **********************************/
package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Ingresso;

public class IngressoDAO extends DAO<Ingresso> {

    public Ingresso read(Object chave) {
        try {
            Long id = (Long) chave;
            TypedQuery<Ingresso> q = manager.createQuery("select i from Ingresso i where i.id = :id", Ingresso.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Ingresso readByCodigo(String codigo) {
        try {
            TypedQuery<Ingresso> q = manager.createQuery("select i from Ingresso i where i.codigo = :codigo", Ingresso.class);
            q.setParameter("codigo", codigo);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // --------------------------------------------
    // consultas
    // --------------------------------------------

    public List<Ingresso> ingressosDaCategoriaXDoJogoY(int numeroCategoria, Long idJogo) {
        TypedQuery<Ingresso> q = manager.createQuery(
                "select i from Ingresso i where i.categoria.numero = :numeroCat and i.jogo.id = :jogoId",
                Ingresso.class);
        q.setParameter("numeroCat", numeroCategoria);
        q.setParameter("jogoId", idJogo);
        return q.getResultList();
    }
}