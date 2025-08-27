/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Projeto: Vai Pro Jogo
 **********************************/
package requisito;

import daojpa.*;
import model.Categoria;
import model.Ingresso;
import model.Jogo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Fachada {

    private static CategoriaDAO categoriaDao = new CategoriaDAO();
    private static JogoDAO jogoDao = new JogoDAO();
    private static IngressoDAO ingressoDao = new IngressoDAO();

    // ============================================
    // LISTAGEM
    // ============================================

    public static List<Ingresso> listarIngressos() {
        return ingressoDao.readAll();
    }

    public static List<Jogo> listarJogos() {
        return jogoDao.readAll();
    }

    public static List<Categoria> listarCategorias() {
        return categoriaDao.readAll();
    }

    // ============================================
    // CONSULTAS
    // ============================================

    public static List<Categoria> categoriasComPrecoMaiorQue(double preco) {
        return categoriaDao.categoriasComPrecoMaiorQue(preco);
    }

    public static List<Ingresso> ingressosDaCategoriaXDoJogoY(int numeroCategoria, Long idJogo) {
        return ingressoDao.ingressosDaCategoriaXDoJogoY(numeroCategoria, idJogo);
    }

    public static List<Jogo> jogosComMaisDeXIngressosVendidos(int qtdIngressos) {
        return jogoDao.jogosComMaisDeXIngressosVendidos(qtdIngressos);
    }

    // ============================================
    // CADASTROS
    // ============================================

    public static Categoria cadastrarCategoria(int numero, double preco) {
        DAO.begin();
        try {
            // Verificar se já existe categoria com esse número
            Categoria existente = categoriaDao.readByNumero(numero);
            if (existente != null) {
                throw new RuntimeException("Já existe categoria com número: " + numero);
            }

            Categoria categoria = new Categoria(numero, preco);
            categoriaDao.create(categoria);
            DAO.commit();
            return categoria;

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao cadastrar categoria: " + e.getMessage());
        }
    }

    public static Ingresso cadastrarIngresso(Long jogoId, int numeroCategoria) {
        DAO.begin();
        try {
            Jogo jogo = jogoDao.read(jogoId);
            if (jogo == null) {
                throw new RuntimeException("Jogo não encontrado com id: " + jogoId);
            }

            Categoria categoria = categoriaDao.readByNumero(numeroCategoria);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com número: " + numeroCategoria);
            }

            Ingresso ingresso = new Ingresso(jogo, categoria);
            ingressoDao.create(ingresso);

            // Atualizar a lista bidirecional
            jogo.adicionarIngresso(ingresso);

            DAO.commit();
            return ingresso;

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao cadastrar ingresso: " + e.getMessage());
        }
    }

    public static Jogo cadastrarJogo(LocalDate data, LocalTime hora, String local, String timeA, String timeB) {
        DAO.begin();
        try {
            // Combinar data e hora em string (adaptando ao modelo existente)
            String dataHora = data.toString() + " " + hora.toString();

            Jogo jogo = new Jogo(dataHora, local, timeA, timeB);
            jogoDao.create(jogo);
            DAO.commit();
            return jogo;

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao cadastrar jogo: " + e.getMessage());
        }
    }

}