// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

public class ABB {
    private No raiz;
    private int totalFilmes;

    public ABB() {
        this.raiz = null;
        this.totalFilmes = 0;
    }

    // Insere um filme na arvore
    public void inserir(Filme filme) {
        raiz = inserirRecursivo(raiz, filme);
        totalFilmes++;
    }

    private No inserirRecursivo(No no, Filme filme) {
        // Se chegou em um lugar vazio, cria o novo no
        if (no == null) {
            return new No(filme);
        }

        int comparacao = filme.getTitulo().compareToIgnoreCase(no.filme.getTitulo());

        if (comparacao < 0) {
            // Titulo menor -> vai para a esquerda
            no.esquerda = inserirRecursivo(no.esquerda, filme);
        } else if (comparacao > 0) {
            // Titulo maior -> vai para a direita
            no.direita = inserirRecursivo(no.direita, filme);
        } else {
            // Titulo igual -> nao insere duplicado
            System.out.println("Aviso: Filme '" + filme.getTitulo() + "' ja existe no catalogo.");
            totalFilmes--; // cancela o incremento que sera feito depois
        }

        return no;
    }

    // Busca um filme pelo titulo
    public Filme buscar(String titulo) {
        return buscarRecursivo(raiz, titulo);
    }

    private Filme buscarRecursivo(No no, String titulo) {
        if (no == null) {
            return null; // nao encontrou
        }

        int comparacao = titulo.compareToIgnoreCase(no.filme.getTitulo());

        if (comparacao == 0) {
            return no.filme; // encontrou!
        } else if (comparacao < 0) {
            return buscarRecursivo(no.esquerda, titulo);
        } else {
            return buscarRecursivo(no.direita, titulo);
        }
    }

    // Exibe todos os filmes em ordem alfabetica (percurso in-ordem)
    public void exibirEmOrdem() {
        if (raiz == null) {
            System.out.println("O catalogo esta vazio.");
            return;
        }
        System.out.println("\n===== CATALOGO EM ORDEM ALFABETICA =====");
        inOrdem(raiz);
        System.out.println("========================================");
    }

    private void inOrdem(No no) {
        if (no == null) return;
        inOrdem(no.esquerda);
        System.out.println("  " + no.filme.toString());
        inOrdem(no.direita);
    }

    // Retorna total de filmes
    public int getTotalFilmes() {
        return totalFilmes;
    }

    // Calcula a altura da arvore (extensao)
    public int calcularAltura() {
        return alturaRecursiva(raiz);
    }

    private int alturaRecursiva(No no) {
        if (no == null) return 0;
        int alturaEsq = alturaRecursiva(no.esquerda);
        int alturaDir = alturaRecursiva(no.direita);
        return 1 + Math.max(alturaEsq, alturaDir);
    }

    // Remove um filme pelo titulo, mantendo propriedade da ABB (extensao)
    public boolean remover(String titulo) {
        int antes = totalFilmes;
        raiz = removerRecursivo(raiz, titulo);
        return totalFilmes < antes;
    }

    private No removerRecursivo(No no, String titulo) {
        if (no == null) return null;

        int comparacao = titulo.compareToIgnoreCase(no.filme.getTitulo());

        if (comparacao < 0) {
            no.esquerda = removerRecursivo(no.esquerda, titulo);
        } else if (comparacao > 0) {
            no.direita = removerRecursivo(no.direita, titulo);
        } else {
            // Encontrou o no a remover
            totalFilmes--;

            // Caso 1: sem filhos
            if (no.esquerda == null && no.direita == null) {
                return null;
            }
            // Caso 2: so tem filho direito
            if (no.esquerda == null) {
                return no.direita;
            }
            // Caso 2: so tem filho esquerdo
            if (no.direita == null) {
                return no.esquerda;
            }
            // Caso 3: tem dois filhos - pega o menor da subarvore direita (sucessor)
            No sucessor = menorNo(no.direita);
            no.filme = sucessor.filme;
            no.direita = removerRecursivo(no.direita, sucessor.filme.getTitulo());
            totalFilmes++; // compensar o decremento duplo
        }

        return no;
    }

    private No menorNo(No no) {
        while (no.esquerda != null) {
            no = no.esquerda;
        }
        return no;
    }
}
