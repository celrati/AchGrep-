
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class KMP {
    private String word;
    private String file;

    public KMP(String word, String file) {
        this.word = word;
        this.file = file;
    }

    public void run(String[] indexes) {
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

            this.KMPSearch(this.word, line, i,indexes);
        }
    }

    void KMPSearch(String pat, String txt, int line, String[] indexes) {
        int M = pat.length();
        int N = txt.length();
        int[] lps = new int[M];
        int j = 0;
        this.computeLPSArray(pat, M, lps);
        int i = 0;

        while(i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                ++j;
                ++i;
            }

            if (j == M) {
                Tools.addIndex(indexes,line,(i-j),i-1);
                //System.out.println(line + " " + (i - j) + " " + i);
                j = lps[j - 1];
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    ++i;
                }
            }
        }

    }

    void computeLPSArray(String pat, int M, int[] lps) {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while(i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                ++len;
                lps[i] = len;
                ++i;
            } else if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = len;
                ++i;
            }
        }

    }
}
