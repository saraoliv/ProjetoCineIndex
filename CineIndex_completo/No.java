// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

public class No {
    Filme filme;
    No esquerda;
    No direita;

    public No(Filme filme) {
        this.filme = filme;
        this.esquerda = null;
        this.direita = null;
    }
}
