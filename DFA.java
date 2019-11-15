

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DFA {
    public static final int SIZE = 255;
    private String[][] matrix_NFA;
    private String[][] matrix_DFA = new String[64][64];
    ArrayList<Character> sigma;

    public String[][] getMatrix_DFA() {
        return this.matrix_DFA;
    }

    public DFA(String[][] nfa) {
        this.init();
        this.matrix_NFA = nfa;
        this.sigma = this.getLanguageSigma();
        this.convert();
    }

    public void convert() {
        int begin_state = this.getBEGIN();
        Set<Integer> e_closure_begin = this.get_e_closure(begin_state);
        ArrayList<DFA.SubSet> states = new ArrayList();
        DFA.SubSet begin = new DFA.SubSet(e_closure_begin);
        states.add(begin);
        Set<Set<Integer>> stateSet = new HashSet();
        stateSet.add(e_closure_begin);

        for(int i = 0; i < states.size(); ++i) {
            DFA.SubSet tmp = (DFA.SubSet)states.get(i);
            Iterator it_1 = this.sigma.iterator();

            while(it_1.hasNext()) {
                char word = (Character)it_1.next();
                Set<Integer> state_1 = this.getTransition(tmp.getState(), word);
                tmp.setStateOf(state_1);
                if (!stateSet.contains(state_1)) {
                    stateSet.add(state_1);
                    DFA.SubSet _tmp = new DFA.SubSet(state_1);
                    states.add(_tmp);
                }
            }
        }

        this.setDFA(states);
    }

    public void printStackDebug(ArrayList<DFA.SubSet> states) {
        Iterator it = states.iterator();

        while(it.hasNext()) {
            DFA.SubSet tmp = (DFA.SubSet)it.next();
            System.out.print(tmp.getState() + "  : ");

            for(int i = 0; i < this.sigma.size(); ++i) {
                Set<Integer> state_tmp = tmp.getStateOf(i);
                System.out.print(state_tmp + "  ,   ");
            }

            System.out.println();
        }

    }

    public void setDFA(ArrayList<DFA.SubSet> states) {
        String[] var10000 = this.matrix_DFA[0];
        String[] var10002 = this.matrix_DFA[0];
        var10000[0] = var10002[0] + ",BEGIN";
        Iterator it = states.iterator();

        while(it.hasNext()) {
            DFA.SubSet tmp = (DFA.SubSet)it.next();

            for(int i = 0; i < this.sigma.size(); ++i) {
                int index_state_1 = this.getIndexState(states, tmp.getState());
                Set<Integer> state_tmp = tmp.getStateOf(i);
                if (tmp.getState().contains(this.getEndState())) {
                    this.matrix_DFA[index_state_1][index_state_1] = this.matrix_DFA[index_state_1][index_state_1] + ",END";
                }

                if (state_tmp.size() != 0) {
                    int index_state_2 = this.getIndexState(states, state_tmp);
                    var10000 = this.matrix_DFA[index_state_1];
                    String var8 = this.matrix_DFA[index_state_1][index_state_2];
                    var10000[index_state_2] = var8 + "," + String.valueOf(this.sigma.get(i));
                }
            }
        }

    }

    public int getIndexState(ArrayList<DFA.SubSet> states, Set<Integer> state) {
        Iterator it = states.iterator();
        int i = -1;

        DFA.SubSet tmp;
        do {
            if (!it.hasNext()) {
                return i;
            }

            tmp = (DFA.SubSet)it.next();
            ++i;
        } while(!tmp.getState().equals(state));

        return i;
    }

    public String[][] getDFA() {
        return this.matrix_DFA;
    }

    public void init() {
        for(int i = 0; i < 64; ++i) {
            for(int j = 0; j < 64; ++j) {
                this.matrix_DFA[i][j] = "-";
            }
        }

    }

    public int getBEGIN() {
        int ret = -1;

        for(int i = 0; i < 255; ++i) {
            if (this.matrix_NFA[i][i] != null && this.matrix_NFA[i][i].equals("BEGIN")) {
                return i;
            }
        }

        return ret;
    }

    public ArrayList<Character> getLanguageSigma() {
        Set<Character> ret = new HashSet();

        for(int i = 0; i < 255; ++i) {
            for(int j = 0; j < 255; ++j) {
                if (this.matrix_NFA[i][j] != null && !this.matrix_NFA[i][j].equals("EPSILON") && !this.matrix_NFA[i][j].equals("IGNORE") && !this.matrix_NFA[i][j].equals("BEGIN") && !this.matrix_NFA[i][j].equals("END")) {
                    ret.add(this.matrix_NFA[i][j].charAt(0));
                }
            }
        }

        return new ArrayList(ret);
    }

    public Set<Integer> get_e_closure(int state) {
        Set<Integer> ret = new HashSet();
        ArrayList<Integer> list = new ArrayList();
        list.add(state);

        for(int j = 0; j < list.size(); ++j) {
            int victim = (Integer)list.get(j);

            for(int i = 0; i < 255; ++i) {
                if (this.matrix_NFA[victim][i] != null && this.matrix_NFA[victim][i].equals("EPSILON") && !list.contains(i)) {
                    list.add(i);
                }
            }
        }

        ret.addAll(list);
        return ret;
    }

    public Set<Integer> getTransition(Set<Integer> state_1, char word) {
        Iterator it = state_1.iterator();
        HashSet setTransition = new HashSet();

        while(it.hasNext()) {
            int s = (Integer)it.next();

            for(int i = 0; i < 255; ++i) {
                if (this.matrix_NFA[s][i] != null && this.matrix_NFA[s][i].equals(String.valueOf(word))) {
                    setTransition.add(i);
                }
            }
        }

        Set<Integer> ret = new HashSet();
        Iterator it_1 = setTransition.iterator();

        while(it_1.hasNext()) {
            int s = (Integer)it_1.next();
            ret.addAll(this.get_e_closure(s));
        }

        return ret;
    }

    public void print() {
        System.out.println("printing dfa ");

        for(int i = 0; i < 64; ++i) {
            for(int j = 0; j < 64; ++j) {
                System.out.print(this.matrix_DFA[i][j] + "       ");
            }

            System.out.println();
        }

    }

    public int getEndState() {
        for(int i = 0; i < 255; ++i) {
            if (this.matrix_NFA[i][i] != null && this.matrix_NFA[i][i].equals("END")) {
                return i;
            }
        }

        return -1;
    }

    public int getBeginDfa() {
        for(int i = 0; i < 64; ++i) {
            if (this.matrix_DFA[i][i] != null && Tools.containsWord(this.matrix_DFA[i][i], "BEGIN")) {
                return i;
            }
        }

        return -1;
    }

    class SubSet {
        private Set<Integer> state;
        private ArrayList<Set<Integer>> state_transition;

        public SubSet(Set<Integer> state) {
            this.state = state;
            this.state_transition = new ArrayList();
        }

        private Set<Integer> getState() {
            return this.state;
        }

        private Set<Integer> getStateOf(int index) {
            return (Set)this.state_transition.get(index);
        }

        private void setStateOf(Set<Integer> state) {
            this.state_transition.add(state);
        }
    }
}