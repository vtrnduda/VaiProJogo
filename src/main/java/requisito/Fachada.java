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

    // ============================================
    // EXCLUSÕES
    // ============================================

    public static void removerIngresso(Long id) {
        DAO.begin();
        try {
            Ingresso ingresso = ingressoDao.read(id);
            if (ingresso == null) {
                throw new RuntimeException("Ingresso não encontrado com id: " + id);
            }

            // Remover da lista bidirecional
            Jogo jogo = ingresso.getJogo();
            if (jogo != null) {
                jogo.getListaIngressos().remove(ingresso);
            }

            ingressoDao.delete(ingresso);
            DAO.commit();

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao remover ingresso: " + e.getMessage());
        }
    }

    public static void removerJogo(Long id) {
        DAO.begin();
        try {
            Jogo jogo = jogoDao.read(id);
            if (jogo == null) {
                throw new RuntimeException("Jogo não encontrado com id: " + id);
            }

            // Verificar se tem ingressos
            if (!jogo.getListaIngressos().isEmpty()) {
                throw new RuntimeException("Não é possível remover jogo com ingressos cadastrados");
            }

            jogoDao.delete(jogo);
            DAO.commit();

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao remover jogo: " + e.getMessage());
        }
    }

    public static void removerCategoria(Long id) {
        DAO.begin();
        try {
            Categoria categoria = categoriaDao.read(id);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com id: " + id);
            }

            // Verificar se tem ingressos
            if (categoria.getIngressos() != null && !categoria.getIngressos().isEmpty()) {
                throw new RuntimeException("Não é possível remover categoria com ingressos cadastrados");
            }

            categoriaDao.delete(categoria);
            DAO.commit();

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao remover categoria: " + e.getMessage());
        }
    }

    // ============================================
    // ALTERAÇÕES
    // ============================================

    public static Categoria alterarNumeroDaCategoria(int novoNumero, Long idCategoria) {
        DAO.begin();
        try {
            Categoria categoria = categoriaDao.read(idCategoria);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com id: " + idCategoria);
            }

            // Verificar se já existe outra categoria com esse número
            Categoria existente = categoriaDao.readByNumero(novoNumero);
            if (existente != null && !existente.getId().equals(idCategoria)) {
                throw new RuntimeException("Já existe categoria com número: " + novoNumero);
            }

            categoria.setNumero(novoNumero);
            categoriaDao.update(categoria);
            DAO.commit();
            return categoria;

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao alterar número da categoria: " + e.getMessage());
        }
    }

    public static Ingresso alterarCodigoDoIngresso(String novoCodigo, Long idIngresso) {
        DAO.begin();
        try {
            Ingresso ingresso = ingressoDao.read(idIngresso);
            if (ingresso == null) {
                throw new RuntimeException("Ingresso não encontrado com id: " + idIngresso);
            }

            // Verificar se já existe ingresso com esse código
            Ingresso existente = ingressoDao.readByCodigo(novoCodigo);
            if (existente != null && !existente.getId().equals(idIngresso)) {
                throw new RuntimeException("Já existe ingresso com código: " + novoCodigo);
            }

            ingresso.setCodigo(novoCodigo);
            ingressoDao.update(ingresso);
            DAO.commit();
            return ingresso;

        } catch (Exception e) {
            DAO.rollback();
            throw new RuntimeException("Erro ao alterar código do ingresso: " + e.getMessage());
        }
    }
}