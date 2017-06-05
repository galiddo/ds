import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//SUBMIT
public class BNode implements BNodeInterface {

    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    // ///////////////////BEGIN DO NOT CHANGE ///////////////////
    private final int t;
    private int numOfBlocks;
    private boolean isLeaf;
    private ArrayList<Block> blocksList;
    private ArrayList<BNode> childrenList;

    /**
     * Constructor for creating a node with a single child.<br>
     * Useful for creating a new root.
     */
    public BNode(int t, BNode firstChild) {
        this(t, false, 0);
        this.childrenList.add(firstChild);
    }

    /**
     * Constructor for creating a <b>leaf</b> node with a single block.
     */
    public BNode(int t, Block firstBlock) {
        this(t, true, 1);
        this.blocksList.add(firstBlock);
    }

    public BNode(int t, boolean isLeaf, int numOfBlocks) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.numOfBlocks = numOfBlocks;
        this.blocksList = new ArrayList<Block>();
        this.childrenList = new ArrayList<BNode>();
    }

    // For testing purposes.
    public BNode(int t, int numOfBlocks, boolean isLeaf,
                 ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
        this.t = t;
        this.numOfBlocks = numOfBlocks;
        this.isLeaf = isLeaf;
        this.blocksList = blocksList;
        this.childrenList = childrenList;
    }

    @Override
    public int getT() {
        return t;
    }

    @Override
    public int getNumOfBlocks() {
        return numOfBlocks;
    }

    @Override
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public ArrayList<Block> getBlocksList() {
        return blocksList;
    }

    @Override
    public ArrayList<BNode> getChildrenList() {
        return childrenList;
    }

    @Override
    public boolean isFull() {
        return numOfBlocks == 2 * t - 1;
    }

    @Override
    public boolean isMinSize() {
        return numOfBlocks == t - 1;
    }

    @Override
    public boolean isEmpty() {
        return numOfBlocks == 0;
    }

    @Override
    public int getBlockKeyAt(int indx) {
        return blocksList.get(indx).getKey();
    }

    @Override
    public Block getBlockAt(int indx) {
        return blocksList.get(indx);
    }

    @Override
    public BNode getChildAt(int indx) {
        return childrenList.get(indx);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((blocksList == null) ? 0 : blocksList.hashCode());
        result = prime * result
                + ((childrenList == null) ? 0 : childrenList.hashCode());
        result = prime * result + (isLeaf ? 1231 : 1237);
        result = prime * result + numOfBlocks;
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
        BNode other = (BNode) obj;
        if (blocksList == null) {
            if (other.blocksList != null)
                return false;
        } else if (!blocksList.equals(other.blocksList))
            return false;
        if (childrenList == null) {
            if (other.childrenList != null)
                return false;
        } else if (!childrenList.equals(other.childrenList))
            return false;
        if (isLeaf != other.isLeaf)
            return false;
        if (numOfBlocks != other.numOfBlocks)
            return false;
        if (t != other.t)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[ blocksList=" + blocksList + ", childrenList="
                + childrenList + "]";
    }

    // ///////////////////DO NOT CHANGE END///////////////////
    // ///////////////////DO NOT CHANGE END///////////////////
    // ///////////////////DO NOT CHANGE END///////////////////


    public void splitChild(int childIndex) {
        BNode y = this.getChildAt(childIndex);
        BNode z = new BNode(t, y.isLeaf(), t - 1);
        for (int j = 0; j < t - 1; j++) {
            z.blocksList.add(j, y.getBlockAt(j + t));
        }

        if (!y.isLeaf()) {
            for (int j = 0; j < t; j++)
                z.childrenList.add(j, y.getChildAt(j + t));
        }


        this.childrenList.add(childIndex + 1, z);

        blocksList.add(childIndex, y.getBlockAt(t - 1));
        this.numOfBlocks += 1;
        y.numOfBlocks = t - 1;
        y.blocksList = new ArrayList<Block>(y.blocksList.subList(0, t - 1));

    }

    @Override
    public Block search(int key) {
        int i = 0;
        while (i < this.numOfBlocks && blocksList.get(i).getKey() < key) i++;
        if (i < numOfBlocks && blocksList.get(i).getKey() == key) return blocksList.get(i);
        else {
            if (this.isLeaf) return null;
            else return childrenList.get(i).search(key);
        }
    }

    @Override
    public void insertNonFull(Block b) {
        int i = this.numOfBlocks - 1;
        if (isLeaf) {
            while (i >= 0 && b.getKey() < getBlockKeyAt(i)) {
                i--;
            }
            blocksList.add(i + 1, b);
            numOfBlocks++;
        } else {
            while (i >= 0 && b.getKey() < getBlockKeyAt(i))
                i--;
            i++;
            if (childrenList.get(i).getNumOfBlocks() == 2 * t - 1) {
                splitChild(i);
                if (b.getKey() > getBlockKeyAt(i))
                    i++;
            }
            getChildAt(i).insertNonFull(b);
        }
    }

    @Override
    public void delete(int key) {
        //TODO
    }


    @Override
    public MerkleBNode createHashNode() {

        return new MerkleBNode(calcHash(),this.isLeaf,createMerkleChildren());


    }

    private byte[] calcHash()
    {
        ArrayList<byte[]> toHash = new ArrayList<>();
        for (int i=0;i<childrenList.size();i++)
        {
            if(!isLeaf())toHash.add(childrenList.get(i).calcHash());
            toHash.add(blocksList.get(i).getData());
        }
        return HashUtils.sha1Hash(toHash);

    }
    private ArrayList<MerkleBNode> createMerkleChildren()
    {
        ArrayList<MerkleBNode> merkleChildren = new ArrayList<>();
        for (BNode child:childrenList)
            merkleChildren.add(child.createHashNode());

        return merkleChildren;

    }





}
