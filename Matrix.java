

import java.util.Iterator;
import java.util.Stack;

public class Matrix {
    public static final int SIZE = 255;
    public String[][] matrix = new String[255][255];
    private Stack<Entry> entryStack;
    private Stack<Block> blocks;

    public Matrix(Stack<Entry> entryStack) {
        this.entryStack = entryStack;
        this.blocks = new Stack();
        this.insertTerminals();
        this.update();
    }

    public String[][] getMatrix() {
        return this.matrix;
    }

    public void print() {
        for(int i = 0; i < 255; ++i) {
            for(int j = 0; j < 255; ++j) {
                System.out.print(this.matrix[i][j] + " ");
            }

            System.out.println();
        }

    }

    public void insertTerminals() {
        Iterator it = this.entryStack.iterator();

        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            if (e.getData().charAt(0) != '*' && e.getData().charAt(0) != '|' && e.getData().charAt(0) != '.') {
                this.matrix[e.getStart()][e.getStart()] = "BEGIN";
                this.matrix[e.getStart()][e.getEnd()] = e.getData();
                this.matrix[e.getEnd()][e.getEnd()] = "END";
            }
        }

    }

    public void update() {
        Iterator it = this.entryStack.iterator();

        label113:
        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            Block tmp;
            if (!e.getData().equals("|") && !e.getData().equals(".") && !e.getData().equals("*")) {
                if (this.blocks.size() == 0) {
                    break;
                }

                tmp = (Block)this.blocks.pop();
                Entry _e;
                if (tmp.getOperator() == '*') {
                    tmp.setEntry_1(e);
                    if (this.blocks.size() == 0) {
                        this.blocks.push(tmp);
                    } else {
                        do {
                            while(true) {
                                if (this.blocks.size() == 0) {
                                    continue label113;
                                }

                                _e = tmp.getEntry();
                                tmp = (Block)this.blocks.pop();
                                if (tmp.getOperator() == '*' && tmp.getEntry_1() == null) {
                                    tmp.setEntry_1(_e);
                                    break;
                                }

                                if ((tmp.getOperator() == '|' || tmp.getOperator() == '.') && tmp.getEntry_1() == null) {
                                    tmp.setEntry_1(_e);
                                    this.blocks.push(tmp);
                                    continue label113;
                                }

                                if ((tmp.getOperator() == '|' || tmp.getOperator() == '.') && tmp.getEntry_2() == null) {
                                    tmp.setEntry_2(_e);
                                    if (this.blocks.size() == 0) {
                                        this.blocks.push(tmp);
                                        continue label113;
                                    }
                                }
                            }
                        } while(this.blocks.size() != 0);

                        this.blocks.push(tmp);
                    }
                } else if (tmp.getEntry_1() == null) {
                    tmp.setEntry_1(e);
                    this.blocks.push(tmp);
                } else if (tmp.getEntry_2() == null) {
                    tmp.setEntry_2(e);
                    if (this.blocks.size() == 0) {
                        this.blocks.push(tmp);
                    } else {
                        do {
                            while(true) {
                                if (this.blocks.size() == 0) {
                                    continue label113;
                                }

                                _e = tmp.getEntry();
                                tmp = (Block)this.blocks.pop();
                                if (tmp.getOperator() == '*' && tmp.getEntry_1() == null) {
                                    tmp.setEntry_1(_e);
                                    break;
                                }

                                if ((tmp.getOperator() == '|' || tmp.getOperator() == '.') && tmp.getEntry_1() == null) {
                                    tmp.setEntry_1(_e);
                                    this.blocks.push(tmp);
                                    continue label113;
                                }

                                if ((tmp.getOperator() == '|' || tmp.getOperator() == '.') && tmp.getEntry_2() == null) {
                                    tmp.setEntry_2(_e);
                                    if (this.blocks.size() == 0) {
                                        this.blocks.push(tmp);
                                        continue label113;
                                    }
                                }
                            }
                        } while(this.blocks.size() != 0);

                        this.blocks.push(tmp);
                    }
                }
            } else {
                tmp = new Block(e.getData().charAt(0), this);
                this.blocks.push(tmp);
            }
        }

    }
}
