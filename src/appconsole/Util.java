package appconsole;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

public class Util {

	private static ObjectContainer manager;

	public static ObjectContainer conectar(){
		if (manager != null)
			return manager;

		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0); 

		config.common().objectClass(Categoria.class).cascadeOnDelete(true);;
		config.common().objectClass(Categoria.class).cascadeOnUpdate(true);;
		config.common().objectClass(Categoria.class).cascadeOnActivate(true);
		config.common().objectClass(Ingresso.class).cascadeOnDelete(true);;
		config.common().objectClass(Ingresso.class).cascadeOnUpdate(true);;
		config.common().objectClass(Ingresso.class).cascadeOnActivate(true);
		config.common().objectClass(Jogo.class).cascadeOnDelete(true);;
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

}
