package requisito;

import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Fachada {

    // Listagem
    public static List<Ingresso> listarIngressos(){
        // TODO:
        return null;
    }

    public static List<Jogo> listarJogos(){
        // TODO
        return null;
    }

    public static List<Categoria> listarCategorias(){
        // TODO
        return null;
    }

    // Consultas
    public static List<Categoria> categoriasComPrecoMaiorQue(BigDecimal preco){
        // TODO
        return null;
    }

    public static List<Ingresso> ingressosDaCategoriaXDoJogoY(Long numeroCatgoria, Long idJogo){
        // TODO
        return null;
    }

    public static List<Jogo> jogosComMaisDeXIngressosVendidos(Long qtdIngressos){
        // TODO
        return null;
    }

    // Cadastros
    public static Categoria cadastrarCategoria(Long numero, BigDecimal preco){
        // TODO
        return null;
    }

    public static Ingresso cadastrarIngresso(Jogo jogo, Categoria categoria){
        // TODO
        return null;
    }

    public static Jogo cadastrarJogo(LocalDate data, LocalTime hora, String local, String timaA, String timeB){
        // TODO
        return null;
    }

    // Exclusões
    public static void removerIngresso(Long id){
        // TODO
    }

    public static void removerJogo(Long id){
        // TODO
    }

    public static void removerCategoria(Long id){
        // TODO
    }

    // Alterações
    public static Categoria alterarNumeroDaCategoria(Long numero, Long idCategoria){
        // TODO
        return null;
    }

    public static Ingresso alterarCodigoDoIngresso(String novoCodigoIngresso, Long idIngresso){
        // TODO
        return null;
    }
}
