
import java.util.Stack;

public class Tools {
    public Tools() {
    }

    public static Stack<Character> getCharStack(String regEx) {
        RegExTree ret = null;

        try {
            ret = RegEx.parse(regEx);
        } catch (Exception var5) {
            System.err.println("  >> ERROR: syntax error for regEx \"" + regEx + "\".");
        }

        String _ret = ret.toString().replace("(", " ").replace(")", " ").replace("*", " * ").replace(".", " . ").replace("|", " | ").replace(",", " ").trim();
        Stack<Character> __ret = new Stack();

        for(int i = 0; i < _ret.length(); ++i) {
            if (_ret.charAt(i) != ' ') {
                __ret.push(_ret.charAt(i));
            }
        }

        return __ret;
    }

    public static void print_Stack_char(Stack<Character> stack) {
    }

    public static boolean containsWord(String words, char word) {
        String[] s = words.split(",");

        for(int i = 0; i < s.length; ++i) {
            if (s[i].charAt(0) == word) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsWord(String words, String word) {
        String[] s = words.split(",");

        for(int i = 0; i < s.length; ++i) {
            if (s[i].equals(word)) {
                return true;
            }
        }

        return false;
    }


    public static boolean containsalpha(String words){
        return false;
    }



    public static boolean isWordalpha(String word) {
        for(int i = 0; i < word.length(); ++i) {
            if ((word.charAt(i) < 'a' || word.charAt(i) > 'z') && (word.charAt(i) < 'A' || word.charAt(i) > 'Z')) {
                return false;
            }
        }

        return true;
    }


    public static void addIndex(String[] indexes, int line, int i, int j){
        indexes[line]  +=  i+":"+j+"-";
        //System.out.println(indexes[line]);

    }

    public static boolean verifyIndex(String[] indexes, int line, int c){
        if(indexes[line] == null) return false;
        String[] s = indexes[line].split("-");

        //System.out.println(s[0]+"hello");
        for(int i = 0 ; i < s.length ; i++){

            if(s[i].equals("")) continue;
            s[i].replace("-","");
            String[] _s = s[i].split(":");
            //System.out.println(s[i]+"aloha");

            int start = Integer.valueOf(_s[0].replace("$",""));
            int end = Integer.valueOf(_s[1].replace("$",""));
            ///System.out.println("c : "+c);
            //System.out.println("sta : "+start);
            ///System.out.println("end : "+end);

            if(c >= start && c <= end){
                return true;
            }
        }
        return false;
    }
}
