// SUBMIT
public class BTree implements BTreeInterface {

    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    private BNode root;
    private final int t;

    /**
     * Construct an empty tree.
     */
    public BTree(int t) { //
        this.t = t;
        this.root = null;
    }

    // For testing purposes.
    public BTree(int t, BNode root) {
        this.t = t;
        this.root = root;
    }

    @Override
    public BNode getRoot() {
        return root;
    }

    @Override
    public int getT() {
        return t;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((root == null) ? 0 : root.hashCode());
        result = prime * result + t;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BTree other = (BTree) obj;
        if (root == null) {
            if (other.root != null)
                return false;
        } else if (!root.equals(other.root))
            return false;
        if (t != other.t)
            return false;
        return true;
    }

    // ///////////////////DO NOT CHANGE END///////////////////
    // ///////////////////DO NOT CHANGE END///////////////////
    // ///////////////////DO NOT CHANGE END///////////////////


    @Override
    public Block search(int key) {

        return root.search(key);
    }

    @Override
    public void insert(Block b) {
        // TODO Auto-generated method stub
        if (root == null) root = new BNode(t, b);
        else {
            BNode root = this.root;
            if (root.isFull()) {
                BNode s = new BNode(t, false, 0);
                s.getChildrenList().add(0, root);
                this.root = s;
                if(b.getKey()==18)
                    b.getKey();
                s.splitChild(0);
                s.insertNonFull(b);

            } else
                root.insertNonFull(b);

        }
    }


    @Override
    public void delete(int key) {


    }

    @Override
    public MerkleBNode createMBT() {

        return root.createHashNode();
    }


}
