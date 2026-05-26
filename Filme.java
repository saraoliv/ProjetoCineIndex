// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

// Filme implementa Comparable para funcionar com a ABB generica do professor
public class Filme implements Comparable<Filme> {
    private String titulo;
    private String genero;
    private int ano;

    public Filme(String titulo, String genero, int ano) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getAno() { return ano; }

    // Comparacao case-insensitive pelo titulo (exigido pelo projeto)
    @Override
    public int compareTo(Filme outro) {
        return this.titulo.compareToIgnoreCase(outro.titulo);
    }

    @Override
    public String toString() {
        return titulo + " | " + genero + " | " + ano;
    }
}
