import java.io.PrintStream;
import java.util.Iterator;
import java.util.Stack;

public class Automaton {
    private int start;
    private int end;
    private Stack<Character> starterStack;
    private Stack<Entry> finalStack;
    private Matrix matrix;

    public Matrix getMatrix() {
        return this.matrix;
    }

    public Automaton(Stack<Character> starterStack) {
        this.starterStack = starterStack;
        this.init();
        this.matrix = new Matrix(this.finalStack);
    }

    public void print() {
        this.matrix.print();
    }

    private void update() {
        this.matrix.update();
    }

    private void init() {
        this.finalStack = new Stack();
        new Stack();
        int index = 10;

        Entry e;
        for(Iterator it = this.starterStack.iterator(); it.hasNext(); this.finalStack.push(e)) {
            char sym = (Character)it.next();
            e = new Entry();
            e.setData(String.valueOf(sym));
            if (sym != '|' && sym != '.' && sym != '*') {
                e.setStart(index);
                ++index;
                e.setEnd(index);
                index += 6;
            }
        }

    }

    public void printStack() {
        Iterator it = this.finalStack.iterator();

        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            PrintStream var10000 = System.out;
            String var10001 = e.getData();
            var10000.println("data : " + var10001 + " start  :" + e.getStart() + " end :" + e.getEnd());
        }

    }
}
