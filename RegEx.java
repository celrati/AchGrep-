
import java.util.ArrayList;
import java.util.Iterator;

public class RegEx {
    static final int CONCAT = 12602535;
    static final int ETOILE = 15139102;
    static final int ALTERN = 10583636;
    static final int PROTECTION = 12246445;
    static final int PARENTHESEOUVRANT = 375658084;
    static final int PARENTHESEFERMANT = 1364283729;
    static final int DOT = 3335;

    public RegEx() {
    }

    public static RegExTree parse(String regEx) throws Exception {
        RegExTree example = exampleAhoUllman();
        ArrayList<RegExTree> result = new ArrayList();

        for(int i = 0; i < regEx.length(); ++i) {
            result.add(new RegExTree(charToRoot(regEx.charAt(i)), new ArrayList()));
        }

        return parse(result);
    }

    public static int charToRoot(char c) {
        if (c == '.') {
            return 3335;
        } else if (c == '*') {
            return 15139102;
        } else if (c == '|') {
            return 10583636;
        } else if (c == '(') {
            return 375658084;
        } else {
            return c == ')' ? 1364283729 : c;
        }
    }

    public static RegExTree parse(ArrayList<RegExTree> result) throws Exception {
        while(containParenthese(result)) {
            result = processParenthese(result);
        }

        while(containEtoile(result)) {
            result = processEtoile(result);
        }

        while(containConcat(result)) {
            result = processConcat(result);
        }

        while(containAltern(result)) {
            result = processAltern(result);
        }

        if (result.size() > 1) {
            throw new Exception();
        } else {
            return removeProtection((RegExTree)result.get(0));
        }
    }

    public static boolean containParenthese(ArrayList<RegExTree> trees) {
        Iterator var1 = trees.iterator();

        RegExTree t;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            t = (RegExTree)var1.next();
        } while(t.root != 1364283729 && t.root != 375658084);

        return true;
    }

    public static ArrayList<RegExTree> processParenthese(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList();
        boolean found = false;
        Iterator var3 = trees.iterator();

        while(true) {
            while(var3.hasNext()) {
                RegExTree t = (RegExTree)var3.next();
                if (!found && t.root == 1364283729) {
                    boolean done = false;
                    ArrayList content = new ArrayList();

                    while(!done && !result.isEmpty()) {
                        if (((RegExTree)result.get(result.size() - 1)).root == 375658084) {
                            done = true;
                            result.remove(result.size() - 1);
                        } else {
                            content.add(0, (RegExTree)result.remove(result.size() - 1));
                        }
                    }

                    if (!done) {
                        throw new Exception();
                    }

                    found = true;
                    ArrayList<RegExTree> subTrees = new ArrayList();
                    subTrees.add(parse(content));
                    result.add(new RegExTree(12246445, subTrees));
                } else {
                    result.add(t);
                }
            }

            if (!found) {
                throw new Exception();
            }

            return result;
        }
    }

    public static boolean containEtoile(ArrayList<RegExTree> trees) {
        Iterator var1 = trees.iterator();

        RegExTree t;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            t = (RegExTree)var1.next();
        } while(t.root != 15139102 || !t.subTrees.isEmpty());

        return true;
    }

    public static ArrayList<RegExTree> processEtoile(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList();
        boolean found = false;
        Iterator var3 = trees.iterator();

        while(true) {
            while(var3.hasNext()) {
                RegExTree t = (RegExTree)var3.next();
                if (!found && t.root == 15139102 && t.subTrees.isEmpty()) {
                    if (result.isEmpty()) {
                        throw new Exception();
                    }

                    found = true;
                    RegExTree last = (RegExTree)result.remove(result.size() - 1);
                    ArrayList<RegExTree> subTrees = new ArrayList();
                    subTrees.add(last);
                    result.add(new RegExTree(15139102, subTrees));
                } else {
                    result.add(t);
                }
            }

            return result;
        }
    }

    public static boolean containConcat(ArrayList<RegExTree> trees) {
        boolean firstFound = false;
        Iterator var2 = trees.iterator();

        while(true) {
            while(var2.hasNext()) {
                RegExTree t = (RegExTree)var2.next();
                if (!firstFound && t.root != 10583636) {
                    firstFound = true;
                } else if (firstFound) {
                    if (t.root != 10583636) {
                        return true;
                    }

                    firstFound = false;
                }
            }

            return false;
        }
    }

    public static ArrayList<RegExTree> processConcat(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList();
        boolean found = false;
        boolean firstFound = false;
        Iterator var4 = trees.iterator();

        while(true) {
            while(var4.hasNext()) {
                RegExTree t = (RegExTree)var4.next();
                if (!found && !firstFound && t.root != 10583636) {
                    firstFound = true;
                    result.add(t);
                } else if (!found && firstFound && t.root == 10583636) {
                    firstFound = false;
                    result.add(t);
                } else if (!found && firstFound && t.root != 10583636) {
                    found = true;
                    RegExTree last = (RegExTree)result.remove(result.size() - 1);
                    ArrayList<RegExTree> subTrees = new ArrayList();
                    subTrees.add(last);
                    subTrees.add(t);
                    result.add(new RegExTree(12602535, subTrees));
                } else {
                    result.add(t);
                }
            }

            return result;
        }
    }

    public static boolean containAltern(ArrayList<RegExTree> trees) {
        Iterator var1 = trees.iterator();

        RegExTree t;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            t = (RegExTree)var1.next();
        } while(t.root != 10583636 || !t.subTrees.isEmpty());

        return true;
    }

    public static ArrayList<RegExTree> processAltern(ArrayList<RegExTree> trees) throws Exception {
        ArrayList<RegExTree> result = new ArrayList();
        boolean found = false;
        RegExTree gauche = null;
        boolean done = false;
        Iterator var5 = trees.iterator();

        while(true) {
            while(var5.hasNext()) {
                RegExTree t = (RegExTree)var5.next();
                if (!found && t.root == 10583636 && t.subTrees.isEmpty()) {
                    if (result.isEmpty()) {
                        throw new Exception();
                    }

                    found = true;
                    gauche = (RegExTree)result.remove(result.size() - 1);
                } else if (found && !done) {
                    if (gauche == null) {
                        throw new Exception();
                    }

                    done = true;
                    ArrayList<RegExTree> subTrees = new ArrayList();
                    subTrees.add(gauche);
                    subTrees.add(t);
                    result.add(new RegExTree(10583636, subTrees));
                } else {
                    result.add(t);
                }
            }

            return result;
        }
    }

    public static RegExTree removeProtection(RegExTree tree) throws Exception {
        if (tree.root == 12246445 && tree.subTrees.size() != 1) {
            throw new Exception();
        } else if (tree.subTrees.isEmpty()) {
            return tree;
        } else if (tree.root == 12246445) {
            return removeProtection((RegExTree)tree.subTrees.get(0));
        } else {
            ArrayList<RegExTree> subTrees = new ArrayList();
            Iterator var2 = tree.subTrees.iterator();

            while(var2.hasNext()) {
                RegExTree t = (RegExTree)var2.next();
                subTrees.add(removeProtection(t));
            }

            return new RegExTree(tree.root, subTrees);
        }
    }

    public static RegExTree exampleAhoUllman() {
        RegExTree a = new RegExTree(97, new ArrayList());
        RegExTree b = new RegExTree(98, new ArrayList());
        RegExTree c = new RegExTree(99, new ArrayList());
        ArrayList<RegExTree> subTrees = new ArrayList();
        subTrees.add(c);
        RegExTree cEtoile = new RegExTree(15139102, subTrees);
        subTrees = new ArrayList();
        subTrees.add(b);
        subTrees.add(cEtoile);
        RegExTree dotBCEtoile = new RegExTree(12602535, subTrees);
        subTrees = new ArrayList();
        subTrees.add(a);
        subTrees.add(dotBCEtoile);
        return new RegExTree(10583636, subTrees);
    }
}
