package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import model.Ingresso;
import model.Jogo;

public class Apagar {
    private static ObjectContainer manager;

    public static void main(String[] args) {
        manager = Util.conectar();
        System.out.println("Apagar o Ingresso");

        // localizar inrgesso a partir do codigo dele
        Query q = manager.query();
        q.constrain(Ingresso.class);
        q.descend("codigo").constrain("FLAFLU522373"); // Obter codigo a partir da listagem
        List<Ingresso> resultado = q.execute();
        
        if (resultado.isEmpty()) {
            System.out.println("Ingresso não encontrado!");
            Util.desconectar();
            return;
        }
        
        Ingresso ingresso = resultado.getFirst();

        // Localiza o jogo relacionado ao ingresso a partir do id do jogo
        Query qj = manager.query();
        qj.constrain(Jogo.class);
        qj.descend("id").constrain(ingresso.getJogo().getId());
        List<Jogo> jogo = qj.execute();
        
        if (jogo.isEmpty()) {
            System.out.println("Jogo não encontrado!");
            Util.desconectar();
            return;
        }
        
        Jogo jogoResultado = jogo.getFirst();

        List<Ingresso> listaIngresso = jogoResultado.getListaIngressos();
        for (Ingresso i : listaIngresso) {
            if (i.getCodigo().equals(ingresso.getCodigo())) {
                
                jogoResultado.getListaIngressos().remove(i); // remove o ingresso da lista do jogo
                
                manager.delete(i); // deleta o ingresso do banco
                
                manager.store(jogoResultado); //atualiza o jogo no banco
                manager.commit();
                
                System.out.println("Ingresso removido com sucesso!");
                break;
            }
        }

        Util.desconectar();
        System.out.println("fim da aplicação");
    }

}