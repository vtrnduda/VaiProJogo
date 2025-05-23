package appconsole;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import java.util.List;

public class Util {

	private static ObjectContainer manager;

	public static ObjectContainer conectar(){
		if (manager != null)
			return manager;

		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0); 

		config.common().objectClass(Categoria.class).cascadeOnDelete(false);;
		config.common().objectClass(Categoria.class).cascadeOnUpdate(true);;
		config.common().objectClass(Categoria.class).cascadeOnActivate(true);
		config.common().objectClass(Ingresso.class).cascadeOnDelete(false);;
		config.common().objectClass(Ingresso.class).cascadeOnUpdate(true);;
		config.common().objectClass(Ingresso.class).cascadeOnActivate(true);
		config.common().objectClass(Jogo.class).cascadeOnDelete(false);;
		config.common().objectClass(Jogo.class).cascadeOnUpdate(true);;
		config.common().objectClass(Jogo.class).cascadeOnActivate(true);

		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}

	public static void desconectar() {
		if (manager != null) {
			manager.close();
			manager = null;
		}
	}

	public static int getProximoIdJogo() {
		ObjectContainer manager = conectar();
		Query query = manager.query();
		query.constrain(Jogo.class);
		List<Jogo> jogos = query.execute();
		
		int maiorId = 0;
		for (Jogo jogo : jogos) {
			int id = jogo.getId();
			if (id > maiorId) {
				maiorId = id;
			}
		}
		
		return maiorId + 1;
	}

	public static void validate() {

	}

}
