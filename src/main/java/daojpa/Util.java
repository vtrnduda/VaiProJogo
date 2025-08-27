package daojpa;

import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Util {
    private static EntityManager manager;
    private static EntityManagerFactory factory;
    public static String ip;

    public static EntityManager conectarBanco() {
        if (manager == null) {
            try {
                System.out.println("Carregando configuração do banco...");

                Properties dados = new Properties();
                dados.load(Util.class.getResourceAsStream("/daojpa/util.properties"));
                String sgbd = dados.getProperty("sgbd");
                String banco = dados.getProperty("banco");
                String ip = dados.getProperty("ipatual");

                System.out.println("SGBD: " + sgbd);
                System.out.println("Banco: " + banco);
                System.out.println("IP: " + ip);

                Properties propriedades = new Properties();
                if (sgbd.equals("postgresql")) {
                    Dotenv dotenv = Dotenv.load();
                    String user = dotenv.get("DB_USER");
                    String password = dotenv.get("DB_PASSWORD");
                    propriedades.setProperty("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
                    propriedades.setProperty("jakarta.persistence.jdbc.url",
                            "jdbc:postgresql://" + ip + ":5432/" + banco);
                    propriedades.setProperty("jakarta.persistence.jdbc.user", user);
                    propriedades.setProperty("jakarta.persistence.jdbc.password", password);
                }
                if (sgbd.equals("mysql")) {
                    propriedades.setProperty("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                    propriedades.setProperty("jakarta.persistence.jdbc.url",
                            "jdbc:mysql://" + ip + ":3306/" + banco + "?createDatabaseIfNotExist=true");
                    propriedades.setProperty("jakarta.persistence.jdbc.user", "root");
                    propriedades.setProperty("jakarta.persistence.jdbc.password", "SUA_SENHA_AQUI");
                }

                String unit_name = "hibernate" + "-" + sgbd;
                System.out.println("Unit name: " + unit_name);

                factory = Persistence.createEntityManagerFactory(unit_name, propriedades);
                manager = factory.createEntityManager();

                System.out.println("Conexão com banco estabelecida com sucesso!");

            } catch (Exception e) {
                System.err.println("Erro ao conectar: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("nao conectou ao bd: " + e.getMessage());
            }
        }
        return manager;
    }

    public static void desconectarBanco() {
        if (manager != null && manager.isOpen()) {
            manager.close();
            factory.close();
            manager = null;
        }
    }
}