
import java.util.ArrayList;

class RegExTree {
    public int root;
    public ArrayList<RegExTree> subTrees;

    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
        this.root = root;
        this.subTrees = subTrees;
    }

    public String toString() {
        if (this.subTrees.isEmpty()) {
            return this.rootToString();
        } else {
            String var10000 = this.rootToString();
            String result = var10000 + "(" + ((RegExTree)this.subTrees.get(0)).toString();

            for(int i = 1; i < this.subTrees.size(); ++i) {
                result = result + "," + ((RegExTree)this.subTrees.get(i)).toString();
            }

            return result + ")";
        }
    }

    public String rootToString() {
        if (this.root == 12602535) {
            return ".";
        } else if (this.root == 15139102) {
            return "*";
        } else if (this.root == 10583636) {
            return "|";
        } else {
            return this.root == 3335 ? "." : Character.toString((char)this.root);
        }
    }
}
