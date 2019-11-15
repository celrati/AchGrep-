
public class Block {
    private char operator;
    private Entry entry_1;
    private Entry entry_2;
    private int start;
    private int end;
    private String state;
    private Matrix matrix;

    public Block(char operator, Matrix matrix) {
        this.matrix = matrix;
        this.operator = operator;
        this.state = "NOT";
        this.entry_1 = null;
        this.entry_2 = null;
    }

    public void update() {
        int start_1;
        int end_1;
        if (this.operator == '*') {
            start_1 = this.entry_1.getStart();
            end_1 = this.entry_1.getEnd();
            this.matrix.matrix[this.entry_1.getStart()][this.entry_1.getStart()] = "IGNORE";
            this.matrix.matrix[this.entry_1.getEnd()][this.entry_1.getEnd()] = "IGNORE";
            this.matrix.matrix[this.entry_1.getEnd()][this.entry_1.getStart()] = "EPSILON";
            this.matrix.matrix[this.entry_1.getEnd()][this.entry_1.getEnd() + 1] = "EPSILON";
            this.matrix.matrix[this.entry_1.getStart() - 1][this.entry_1.getStart()] = "EPSILON";
            this.matrix.matrix[this.entry_1.getStart() - 1][this.entry_1.getEnd() + 1] = "EPSILON";
            this.entry_1.setStart(this.entry_1.getStart() - 1);
            this.entry_1.setEnd(this.entry_1.getEnd() + 1);
            this.matrix.matrix[this.entry_1.getStart()][this.entry_1.getStart()] = "BEGIN";
            this.matrix.matrix[this.entry_1.getEnd()][this.entry_1.getEnd()] = "END";
            this.start = this.entry_1.getStart();
            this.end = this.entry_1.getEnd();
        } else {
            int start_2;
            int end_2;
            if (this.operator == '.') {
                start_1 = this.entry_1.getStart();
                end_1 = this.entry_1.getEnd();
                start_2 = this.entry_2.getStart();
                end_2 = this.entry_2.getEnd();
                this.matrix.matrix[start_1][start_1] = "BEGIN";
                this.matrix.matrix[end_1][start_2] = "EPSILON";
                this.matrix.matrix[start_2][start_2] = "IGNORE";
                this.matrix.matrix[end_1][end_1] = "IGNORE";
                this.start = this.entry_1.getStart();
                this.end = this.entry_2.getEnd();
            } else if (this.operator == '|') {
                start_1 = this.entry_1.getStart();
                end_1 = this.entry_1.getEnd();
                start_2 = this.entry_2.getStart();
                end_2 = this.entry_2.getEnd();
                int a = Math.max(start_1, start_2) - 1;
                int b = Math.min(end_1, end_2) + 1;
                this.matrix.matrix[start_1][start_1] = "IGNORE";
                this.matrix.matrix[start_2][start_2] = "IGNORE";
                this.matrix.matrix[end_1][end_1] = "IGNORE";
                this.matrix.matrix[end_2][end_2] = "IGNORE";
                this.matrix.matrix[a][a] = "BEGIN";
                this.matrix.matrix[b][b] = "END";
                this.matrix.matrix[a][start_1] = "EPSILON";
                this.matrix.matrix[a][start_2] = "EPSILON";
                this.matrix.matrix[end_1][b] = "EPSILON";
                this.matrix.matrix[end_2][b] = "EPSILON";
                this.start = a;
                this.end = b;
            }
        }

    }

    public Entry getEntry() {
        Entry ret = new Entry("HELLO", this.start, this.end);
        return ret;
    }

    public boolean isDone() {
        return this.state.equals("DONE");
    }

    public void check() {
        if (this.operator == '*' && this.entry_1 != null) {
            this.state = "DONE";
            this.update();
        }

        if (this.operator == '|' && this.entry_2 != null) {
            this.state = "DONE";
            this.update();
        }

        if (this.operator == '.' && this.entry_2 != null) {
            this.state = "DONE";
            this.update();
        }

    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setEntry_1(Entry entry_1) {
        this.entry_1 = entry_1;
        this.check();
    }

    public void setEntry_2(Entry entry_2) {
        this.entry_2 = entry_2;
        this.check();
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Block(char operator, Entry entry_1, Entry entry_2) {
        this.operator = operator;
        this.entry_1 = entry_1;
        this.entry_2 = entry_2;
    }

    public char getOperator() {
        return this.operator;
    }

    public Entry getEntry_1() {
        return this.entry_1;
    }

    public Entry getEntry_2() {
        return this.entry_2;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }
}
