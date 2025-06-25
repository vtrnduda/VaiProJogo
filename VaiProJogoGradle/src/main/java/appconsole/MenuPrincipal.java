package appconsole;

import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    
    public MenuPrincipal() {
        scanner = new Scanner(System.in);
        exibirMenu();
    }
    
    private void exibirMenu() {
        int opcao;
        
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           VAI PRO JOGO - SISTEMA JPA");
            System.out.println("=".repeat(50));
            System.out.println("1. Cadastrar dados iniciais");
            System.out.println("2. Listar todos os registros");
            System.out.println("3. Consultar dados específicos");
            System.out.println("4. Alterar dados");
            System.out.println("5. Apagar dados");
            System.out.println("0. Sair");
            System.out.println("=".repeat(50));
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        System.out.println("\n>>> Executando Cadastro...");
                        new Cadastrar();
                        break;
                        
                    case 2:
                        System.out.println("\n>>> Executando Listagem...");
                        new Listar();
                        break;
                        
                    case 3:
                        System.out.println("\n>>> Executando Consultas...");
                        new Consultar();
                        break;
                        
                    case 4:
                        System.out.println("\n>>> Executando Alterações...");
                        new Alterar();
                        break;
                        
                    case 5:
                        System.out.println("\n>>> Executando Exclusões...");
                        new Apagar();
                        break;
                        
                    case 0:
                        System.out.println("\n>>> Saindo do sistema...");
                        System.out.println("Obrigado por usar o VAI PRO JOGO!");
                        break;
                        
                    default:
                        System.out.println("\n Opção inválida! Tente novamente.");
                        break;
                }
                
                if (opcao != 0) {
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\nPor favor, digite apenas números!");
                opcao = -1; 
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }
    
    public static void main(String[] args) {
        new MenuPrincipal();
    }
} 