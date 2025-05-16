package appconsole;

import model.Categoria;

public class Util {

	private static ObjectContainer manager;

	public static ObjectContainer conectar(){
		if (manager != null)
			return manager;

		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0); 
	}

	public static void desconectar() {
		if (manager != null) {
			manager.close();
			manager = null;
		}
	}

}
