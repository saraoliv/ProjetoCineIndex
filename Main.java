// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

import java.io.*;
import java.util.Scanner;

public class Main {

    // Usa a ABB do professor com Filme como tipo generico
    static ABB<Filme> arvore = new ABB<>();
    static int totalFilmes = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== CineIndex - Sistema de Catalogo de Filmes ===");

        carregarArquivo("filmes.txt");

        // Extensao: exibe altura da arvore apos a carga
        System.out.println("Altura da arvore apos carga: " + calcularAltura(arvore.getRoot()));

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1: menuBuscar(); break;
                case 2: menuInserir(); break;
                case 3: exibirRelatorio(); break;
                case 4:
                    System.out.println("Total de filmes: " + totalFilmes);
                    System.out.println("Altura da arvore: " + calcularAltura(arvore.getRoot()));
                    break;
                case 5: menuRemover(); break;
                case 0: System.out.println("Encerrando o CineIndex. Ate mais!"); break;
                default: System.out.println("Opcao invalida. Tente novamente.");
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

                try {
                    Filme f = new Filme(partes[0].trim(), partes[1].trim(), Integer.parseInt(partes[2].trim()));
                    arvore.insere(new Node<>(f));
                    totalFilmes++;
                    carregados++;
                } catch (NumberFormatException e) {
                    System.out.println("Aviso: Linha " + linhaNumero + " ignorada (ano invalido): " + linha);
                }
            }
            System.out.println(carregados + " filme(s) carregado(s) de '" + nomeArquivo + "'.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    static void menuBuscar() {
        System.out.print("Digite o titulo do filme: ");
        String titulo = scanner.nextLine().trim();

        // Cria um Filme auxiliar so para usar como chave de busca
        Filme chave = new Filme(titulo, "", 0);
        Node<Filme> resultado = arvore.busca(chave);

        if (resultado != null) {
            Filme f = resultado.getElemento();
            System.out.println("Filme encontrado!");
            System.out.println("  Titulo: " + f.getTitulo());
            System.out.println("  Genero: " + f.getGenero());
            System.out.println("  Ano:    " + f.getAno());
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
            Filme f = new Filme(titulo, genero, Integer.parseInt(anoStr));
            arvore.insere(new Node<>(f));
            totalFilmes++;
            System.out.println("Filme '" + titulo + "' inserido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Ano invalido. Insercao cancelada.");
        }
    }

    static void menuRemover() {
        System.out.print("Digite o titulo do filme a remover: ");
        String titulo = scanner.nextLine().trim();

        Filme chave = new Filme(titulo, "", 0);
        Node<Filme> resultado = arvore.busca(chave);

        if (resultado != null) {
            arvore.delete(arvore.getRoot(), chave);
            totalFilmes--;
            System.out.println("Filme '" + titulo + "' removido com sucesso!");
        } else {
            System.out.println("Filme '" + titulo + "' nao encontrado no catalogo.");
        }
    }

    // Percurso in-ordem para exibir em ordem alfabetica
    static void exibirRelatorio() {
        if (arvore.isEmpty()) {
            System.out.println("O catalogo esta vazio.");
            return;
        }
        System.out.println("\n===== CATALOGO EM ORDEM ALFABETICA =====");
        inOrdem(arvore.getRoot());
        System.out.println("========================================");
    }

    static void inOrdem(Node<Filme> no) {
        if (no == null) return;
        inOrdem(no.getLeft());
        System.out.println("  " + no.getElemento().toString());
        inOrdem(no.getRight());
    }

    // Calcula altura da arvore (extensao)
    static int calcularAltura(Node<Filme> no) {
        if (no == null) return 0;
        return 1 + Math.max(calcularAltura(no.getLeft()), calcularAltura(no.getRight()));
    }
}
