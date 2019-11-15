
public class Entry {
    private String data;
    private int start;
    private int end;

    Entry() {
    }

    Entry(String data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getData() {
        return this.data;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }
}
