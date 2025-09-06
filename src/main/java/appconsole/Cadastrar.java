package appconsole;

import daojpa.DAO;
import requisito.Fachada;
import model.Categoria;
import model.Jogo;
import model.Ingresso;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cadastrar {

    public Cadastrar() {
        try {
            // Abrir conexão uma vez
            DAO.open();
            System.out.println("Cadastrando...");

            // Criar categorias de ingressos
            System.out.println("Criando categorias...");
            Categoria cat1 = Fachada.cadastrarCategoria(1, 150.0);
            Categoria cat2 = Fachada.cadastrarCategoria(2, 250.0);
            Categoria cat3 = Fachada.cadastrarCategoria(3, 350.0);
            System.out.println("Categorias criadas: " + cat1 + ", " + cat2 + ", " + cat3);

            // Criar jogos
            System.out.println("Criando jogos...");
            Jogo jogo1 = Fachada.cadastrarJogo(
                    LocalDate.of(2025, 10, 15),
                    LocalTime.of(16, 0),
                    "Estádio Maracanã",
                    "Flamengo",
                    "Fluminense",
                    null,
                    null
            );

            Jogo jogo2 = Fachada.cadastrarJogo(
                    LocalDate.of(2025, 10, 22),
                    LocalTime.of(16, 0),
                    "Arena Itaquera",
                    "Corinthians",
                    "Palmeiras",
                    null,
                    null
            );

            Jogo jogo3 = Fachada.cadastrarJogo(
                    LocalDate.of(2025, 11, 5),
                    LocalTime.of(18, 30),
                    "Mineirão",
                    "Cruzeiro",
                    "Atlético-MG",
                    null,
                    null
            );
            System.out.println("Jogos criados com IDs: " + jogo1.getId() + ", " + jogo2.getId() + ", " + jogo3.getId());

            // Criar ingressos para jogo1
            System.out.println("Criando ingressos para o jogo 1...");
            Ingresso ingresso1 = Fachada.cadastrarIngresso(jogo1.getId(), 1);
            Ingresso ingresso2 = Fachada.cadastrarIngresso(jogo1.getId(), 2);
            Ingresso ingresso3 = Fachada.cadastrarIngresso(jogo1.getId(), 3);
            System.out.println("Ingressos do jogo 1: " + ingresso1.getCodigo() + ", " + ingresso2.getCodigo() + ", " + ingresso3.getCodigo());

            // Criar ingressos para jogo2
            System.out.println("Criando ingressos para o jogo 2...");
            Ingresso ingresso4 = Fachada.cadastrarIngresso(jogo2.getId(), 1);
            Ingresso ingresso5 = Fachada.cadastrarIngresso(jogo2.getId(), 2);
            System.out.println("Ingressos do jogo 2: " + ingresso4.getCodigo() + ", " + ingresso5.getCodigo());

            // Criar ingressos para jogo3
            System.out.println("Criando ingressos para o jogo 3...");
            Ingresso ingresso6 = Fachada.cadastrarIngresso(jogo3.getId(), 1);
            Ingresso ingresso7 = Fachada.cadastrarIngresso(jogo3.getId(), 3);
            System.out.println("Ingressos do jogo 3: " + ingresso6.getCodigo() + ", " + ingresso7.getCodigo());

            System.out.println("Cadastro concluído com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro durante o cadastro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar conexão no final
            DAO.close();
        }

        System.out.println("Fim do cadastro");
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}