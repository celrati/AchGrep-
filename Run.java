
public class Run {
    @SuppressWarnings("unchecked")

    public Run() {
    }

    public static void main(String[] args) {
        Processor processor = new Processor(args[0], args[1]);
        processor.run();
    }
}
