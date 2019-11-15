
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Processor {
    private String regex;
    private String file;
    Regular regular;
    private boolean type;
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_RESET  = "\u001B[0m";
    private String[] indexes = new String[100000];

    Processor(String regex, String file) {
        this.regex = regex;
        this.file = file;
        this.regular = new Regular(this.regex);
        this.type = this.getType(regex);

        for(int i = 0 ; i < 100000 ; i++){
            indexes[i] = "";
        }

    }

    public void run() {
        if (this.type) {
            this.runOther();
        } else {
            this.runRegx();
        }
        //System.out.println("lol");

        achGrep();
    }


    public void achGrep(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        }

        int i = 0;

        while(true) {
            String line = "";

            try {
                if ((line = br.readLine()) == null) {
                    return;
                }
                // line
                i++;
                if(!indexes[i].equals("")){
                    for(int j = 0 ; j < line.length() ; j++){
                        if(Tools.verifyIndex(indexes,i,j)){

                            System.out.print(ANSI_RED+line.charAt(j)+ANSI_RESET);



                        }else{
                            System.out.print(ANSI_RESET+line.charAt(j));
                        }

                    }
                    System.out.println();

                }

            } catch (IOException var6) {
                var6.printStackTrace();
            }

        }
    }



    public boolean runRadix() {
        RadixTree radix = new RadixTree(this.regex, this.file);
        return radix.isExist();
    }

    public void runKmp() {
        KMP kmp = new KMP(this.regex, this.file);
        kmp.run(indexes);
    }

    public void runOther() {
        if (Tools.isWordalpha(this.regex)) {
            if (this.runRadix()) {
                this.runKmp();
            } else {
                this.runKmp();
            }
        } else {
            this.runKmp();
        }

    }

    public void runRegx() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        }

        int i = 0;

        while(true) {
            String line = "";

            try {
                if ((line = br.readLine()) == null) {
                    return;
                }

                ++i;
            } catch (IOException var6) {
                var6.printStackTrace();
            }

            this.regular.match(line, i,indexes);
        }
    }

    public boolean getType(String regex) {
        boolean ok = true;

        for(int i = 0; i < regex.length(); ++i) {
            if (regex.charAt(i) == '|' || regex.charAt(i) == '*' || regex.charAt(i) == '.') {
                ok = false;
            }
        }
        /*
        String universal = "(";
        for(int i = 1 ; i < 255 ; i++){
            if(i != 254){
                universal += (char)i + "|";
            }else{
                universal += (char)i;

            }
        }
        universal += ")";
        regex.replace(".", universal);
        */

        return ok;
    }



}
