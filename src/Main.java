import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by IddoGal on 29/05/2017.
 */
public class Main
{
    public static void main (String[]args)
    {
        int g=20;
        Object[] b = Block.blockFactory(0,g).toArray();

        BTree BT = new BTree(3);
        for (int i=0;i<g;i++) {
            BT.insert((Block) b[i]);
            if(i==17)
                g=20;
        }
        int i1=0;

        int i=0;
    }
}
