public class ABB<E extends Comparable<E>> {

    private Node<E> root;

    public ABB() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Node<E> getRoot() {
        return root;
    }

    public boolean isRoot(Node<E> n) {
        return n.getParent() == null;
    }

    public boolean isInternal(Node<E> n) {
        return n.getLeft() != null || n.getRight() != null;
    }

    public boolean isLeaf(Node<E> n) {
        return n.getLeft() == null && n.getRight() == null;
    }

    public boolean hasLeft(Node<E> e) {
        return e.getLeft() != null;
    }

    public boolean hasRight(Node<E> e) {
        return e.getRight() != null;
    }

    public Node<E> left(Node<E> e) {
        return e.getLeft();
    }

    public Node<E> right(Node<E> e) {
        return e.getRight();
    }

    public void insere(Node<E> z) {
        Node<E> y = null;
        Node<E> x = root;

        while (x != null) {
            y = x;
            if (z.getElemento().compareTo(x.getElemento()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);

        if (y == null) {
            root = z;
        } else if (z.getElemento().compareTo(y.getElemento()) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }
    }

    public Node<E> busca(E chave) {
        Node<E> y = root;

        while (y != null) {
            int cmp = chave.compareTo(y.getElemento());
            if (cmp == 0) {
                return y;
            } else if (cmp > 0) {
                y = y.getRight();
            } else {
                y = y.getLeft();
            }
        }
        return null;
    }

    public Node<E> minimo(Node<E> x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    public Node<E> maximo(Node<E> x) {
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x;
    }

    public void executaPreOrdem(Node<E> no) {
        if (no == null) return;
        System.out.println(no.getElemento());
        executaPreOrdem(no.getLeft());
        executaPreOrdem(no.getRight());
    }

    public void executaInOrdem(Node<E> no) {
        if (no == null) return;
        executaInOrdem(no.getLeft());
        System.out.println(no.getElemento());
        executaInOrdem(no.getRight());
    }

    public void executaPosOrdem(Node<E> no) {
        if (no == null) return;
        executaPosOrdem(no.getLeft());
        executaPosOrdem(no.getRight());
        System.out.println(no.getElemento());
    }

    public void delete(Node<E> tree, E chave) {
        if (tree == null) return;

        int cmp = chave.compareTo(tree.getElemento());

        if (cmp < 0) {
            delete(tree.getLeft(), chave);
        } else if (cmp > 0) {
            delete(tree.getRight(), chave);
        } else {
            // Encontrou o nó
            if (tree.getLeft() != null && tree.getRight() != null) {
                // Caso c: dois filhos: substitui pelo sucessor (mínimo da direita)
                Node<E> min = minimo(tree.getRight());
                tree.setElemento(min.getElemento());
                delete(tree.getRight(), min.getElemento());
            } else {
                // Casos a e b: zero ou um filho
                Node<E> filho = (tree.getLeft() != null) ? tree.getLeft() : tree.getRight();

                if (tree.getParent() == null) {
                    // Removendo a raiz
                    root = filho;
                    if (filho != null) filho.setParent(null);
                } else {
                    if (filho != null) filho.setParent(tree.getParent());
                    if (tree == tree.getParent().getLeft()) {
                        tree.getParent().setLeft(filho);
                    } else {
                        tree.getParent().setRight(filho);
                    }
                }
            }
        }
    }
}