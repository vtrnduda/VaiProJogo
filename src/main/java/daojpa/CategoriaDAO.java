package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Categoria;

public class CategoriaDAO extends DAO<Categoria> {

    public Categoria read(Object chave) {
        try {
            Long id = (Long) chave;
            TypedQuery<Categoria> q = manager.createQuery("select c from Categoria c where c.id = :id", Categoria.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Categoria readByNumero(int numero) {
        try {
            TypedQuery<Categoria> q = manager.createQuery("select c from Categoria c where c.numero = :numero", Categoria.class);
            q.setParameter("numero", numero);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // -------------------------------------------- 
    // consultas
    // -------------------------------------------- 

    public List<Categoria> categoriasComPrecoMaiorQue(double preco) {
        TypedQuery<Categoria> q = manager.createQuery(
                "select c from Categoria c where c.preco > :preco", Categoria.class);
        q.setParameter("preco", preco);
        return q.getResultList();
    }
}