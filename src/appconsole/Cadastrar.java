package appconsole;

import com.db4o.ObjectContainer;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

public class Cadastrar {

    private ObjectContainer manager;

    public Cadastrar(){
        manager = Util.conectar();

        System.out.println("cadastrando...");

        // Criar categorias de ingressos
        Categoria cat1 = new Categoria(1, 150.0);
        Categoria cat2 = new Categoria(2, 250.0);
        Categoria cat3 = new Categoria(3, 350.0);

        manager.commit();

        System.out.println("Cadastro concluído com sucesso!");
        Util.desconectar();
        System.out.println("Fim do cadastro");
    }

    //=================================================
    public static void main(String[] args) {
        new Cadastrar();
    }
}
