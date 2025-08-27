package daojpa;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public abstract class DAO<T> implements InterfaceDAO<T> {
    protected static EntityManager manager;

    public static void open() {
        manager = Util.conectarBanco();
    }

    public static void close() {
        Util.desconectarBanco();
    }

    public void create(T obj) {
        manager.persist(obj);
    }

    public abstract T read(Object chave);

    public T update(T obj) {
        return manager.merge(obj);
    }

    public void delete(T obj) {
        manager.remove(obj);
    }

    @SuppressWarnings("unchecked")
    public List<T> readAll() {
        Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        TypedQuery<T> query = manager.createQuery("select x from " + type.getSimpleName() + " x", type);
        return query.getResultList();
    }

    // ----------------------- TRANSAÇÃO ----------------------
    public static void begin() {
        if (!manager.getTransaction().isActive())
            manager.getTransaction().begin();
    }

    public static void commit() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
            manager.clear();
        }
    }

    public static void rollback() {
        if (manager.getTransaction().isActive())
            manager.getTransaction().rollback();
    }

    /**
     * EXTRA
     */
    public void resetID() {
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        String classe = type.getSimpleName().toLowerCase();
        try {
            String nomesgbd = "";
            Connection con = getConnection();
            if (con == null)
                throw new SQLException("DAO: reset id - falha ao obter conexao");

            nomesgbd = con.getMetaData().getDatabaseProductName();
            if (nomesgbd.equalsIgnoreCase("postgresql"))
                manager.createNativeQuery("ALTER SEQUENCE " + classe + "_id_seq RESTART WITH 1").executeUpdate();
            else if (nomesgbd.equalsIgnoreCase("mysql"))
                manager.createNativeQuery("ALTER TABLE " + classe + " AUTO_INCREMENT = 1").executeUpdate();
            else
                throw new SQLException("DAO: reset id - sgbd desconhecido " + nomesgbd);

        } catch (SQLException ex) {
            throw new RuntimeException("DAO: reset id -  " + ex.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            Session session = manager.unwrap(Session.class);
            SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
            Connection conn = sfi.getJdbcServices().getBootstrapJdbcConnectionAccess().obtainConnection();
            return conn;
        } catch (Exception ex) {
            return null;
        }
    }
}