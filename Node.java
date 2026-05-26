// Integrantes: [SEU NOME AQUI]
// Projeto 2 - CineIndex - Estrutura de Dados - SI Turma 03S

public class Node<E> {
    private E elemento;
    private Node<E> left;
    private Node<E> right;
    private Node<E> parent;

    public Node(E elemento) {
        this.elemento = elemento;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public E getElemento() { return elemento; }
    public void setElemento(E elemento) { this.elemento = elemento; }

    public Node<E> getLeft() { return left; }
    public void setLeft(Node<E> left) { this.left = left; }

    public Node<E> getRight() { return right; }
    public void setRight(Node<E> right) { this.right = right; }

    public Node<E> getParent() { return parent; }
    public void setParent(Node<E> parent) { this.parent = parent; }
}
