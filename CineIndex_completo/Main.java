// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

import java.io.*;
import java.util.Scanner;

public class Main {

    static ABB arvore = new ABB();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== CineIndex - Sistema de Catalogo de Filmes ===");

        // Carrega o arquivo na inicializacao
        carregarArquivo("filmes.txt");

        // Exibe altura da arvore apos a carga (extensao)
        System.out.println("Altura da arvore apos carga: " + arvore.calcularAltura());

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    menuBuscar();
                    break;
                case 2:
                    menuInserir();
                    break;
                case 3:
                    arvore.exibirEmOrdem();
                    break;
                case 4:
                    System.out.println("Total de filmes no catalogo: " + arvore.getTotalFilmes());
                    System.out.println("Altura atual da arvore: " + arvore.calcularAltura());
                    break;
                case 5:
                    menuRemover();
                    break;
                case 0:
                    System.out.println("Encerrando o CineIndex. Ate mais!");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        }

        scanner.close();
    }

    static void exibirMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1 - Buscar filme pelo titulo");
        System.out.println("2 - Inserir novo filme");
        System.out.println("3 - Exibir catalogo em ordem alfabetica");
        System.out.println("4 - Exibir total de filmes e altura da arvore");
        System.out.println("5 - Remover filme pelo titulo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    static void carregarArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            System.out.println("Aviso: Arquivo '" + nomeArquivo + "' nao encontrado. Iniciando com catalogo vazio.");
            return;
        }

        int linhaNumero = 0;
        int carregados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhaNumero++;
                linha = linha.trim();

                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length != 3) {
                    System.out.println("Aviso: Linha " + linhaNumero + " ignorada (formato invalido): " + linha);
                    continue;
                }

                String titulo = partes[0].trim();
                String genero = partes[1].trim();
                String anoStr = partes[2].trim();

                try {
                    int ano = Integer.parseInt(anoStr);
                    Filme filme = new Filme(titulo, genero, ano);
                    arvore.inserir(filme);
                    carregados++;
                } catch (NumberFormatException e) {
                    System.out.println("Aviso: Linha " + linhaNumero + " ignorada (ano invalido): " + linha);
                }
            }
            System.out.println(carregados + " filme(s) carregado(s) do arquivo '" + nomeArquivo + "'.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    static void menuBuscar() {
        System.out.print("Digite o titulo do filme: ");
        String titulo = scanner.nextLine().trim();

        Filme encontrado = arvore.buscar(titulo);
        if (encontrado != null) {
            System.out.println("Filme encontrado!");
            System.out.println("  Titulo: " + encontrado.getTitulo());
            System.out.println("  Genero: " + encontrado.getGenero());
            System.out.println("  Ano:    " + encontrado.getAno());
        } else {
            System.out.println("Filme '" + titulo + "' nao encontrado no catalogo.");
        }
    }

    static void menuInserir() {
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Genero: ");
        String genero = scanner.nextLine().trim();

        System.out.print("Ano: ");
        String anoStr = scanner.nextLine().trim();

        try {
            int ano = Integer.parseInt(anoStr);
            Filme filme = new Filme(titulo, genero, ano);
            arvore.inserir(filme);
            System.out.println("Filme '" + titulo + "' inserido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Ano invalido. Insercao cancelada.");
        }
    }

    static void menuRemover() {
        System.out.print("Digite o titulo do filme a remover: ");
        String titulo = scanner.nextLine().trim();

        boolean removido = arvore.remover(titulo);
        if (removido) {
            System.out.println("Filme '" + titulo + "' removido com sucesso!");
        } else {
            System.out.println("Filme '" + titulo + "' nao encontrado no catalogo.");
        }
    }
}
