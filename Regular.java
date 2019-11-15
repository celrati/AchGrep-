
import java.util.Stack;

public class Regular {
    private String regEx;
    private DFA dfa;

    public Regular(String regEx) {
        this.regEx = regEx;
        this.init();
    }

    public void init() {
        Stack<Character> stack_char = Tools.getCharStack(this.regEx);
        Tools.print_Stack_char(stack_char);
        Automaton auto = new Automaton(stack_char);
        this.dfa = new DFA(auto.getMatrix().getMatrix());
    }

    public void print() {
        this.dfa.print();
    }

    public boolean match(String str, int line, String[] indexes) {
       // dfa.print();

        int state = this.dfa.getBeginDfa();
        int startIndex = 0;
        boolean change = false;

        for(int i = 0; i < str.length(); ++i) {
            if (change) {
                startIndex = i;
                change = false;
            }

            char tmp = str.charAt(i);
            boolean ok = false;

            for(int j = 0; j < 64; ++j) {
                if (Tools.containsWord(this.dfa.getMatrix_DFA()[state][j], tmp)) {
                    state = j;
                    ok = true;
                    break;
                }
            }

            if (!ok) {
                state = this.dfa.getBeginDfa();
                change = true;
            } else if (Tools.containsWord(this.dfa.getMatrix_DFA()[state][state], "END")) {
                boolean ok2 = true;
                for(int x = 0 ; x < 64 ; x++ ){
                    if(this.dfa.getMatrix_DFA()[state][state].length() > 1){
                        ok2 = false;
                    }
                }

                if(ok2){
                    state = this.dfa.getBeginDfa();

                }
                Tools.addIndex(indexes,line,startIndex,i);
                //System.out.println(line + " " + startIndex + " " + i);
                i = startIndex;
                change = true;
            }
        }

        if (!Tools.containsWord(this.dfa.getMatrix_DFA()[state][state], "END")) {
            return false;
        } else {
            return true;
        }
    }
}
