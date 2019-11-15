
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RadixTree {
    private String word;
    private String file;
    private RadixTree.Trie tree;
    Set<String> words;

    public RadixTree(String word, String file) {
        this.word = word;
        this.file = file;
        this.words = new HashSet();
        this.init();
    }

    public void run() {
        this.tree = new RadixTree.Trie();
        Iterator it = this.words.iterator();

        while(it.hasNext()) {
            String tmp = (String)it.next();
            if (Tools.isWordalpha(tmp)) {
                this.tree.insert(tmp);
            }
        }

    }

    public boolean isExist() {
        return this.tree.search(this.word);
    }

    public void init() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        }

        int var3 = 0;

        while(true) {
            String line = "";

            try {
                if ((line = br.readLine()) == null) {
                    break;
                }

                ++var3;
            } catch (IOException var7) {
                var7.printStackTrace();
            }

            String[] tmp = line.split("[^a-zA-Z0-9']+");
            this.words.addAll(Arrays.asList(tmp));
        }

        this.run();
    }

    public void print() {
        System.out.print(this.words);
    }

    public class Trie {
        private RadixTree.TrieNode root = RadixTree.this.new TrieNode();

        public Trie() {
        }

        public void insert(String word) {
            RadixTree.TrieNode p = this.root;

            for(int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                int index = c - 65;
                if (p.arr[index] == null) {
                    RadixTree.TrieNode temp = RadixTree.this.new TrieNode();
                    p.arr[index] = temp;
                    p = temp;
                } else {
                    p = p.arr[index];
                }
            }

            p.isEnd = true;
        }

        public boolean search(String word) {
            RadixTree.TrieNode p = this.searchNode(word);
            if (p == null) {
                return false;
            } else {
                return p.isEnd;
            }
        }

        public RadixTree.TrieNode searchNode(String s) {
            RadixTree.TrieNode p = this.root;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                int index = c - 65;
                if (p.arr[index] == null) {
                    return null;
                }

                p = p.arr[index];
            }

            if (p == this.root) {
                return null;
            } else {
                return p;
            }
        }
    }

    class TrieNode {
        RadixTree.TrieNode[] arr = new RadixTree.TrieNode[512];
        boolean isEnd;

        public TrieNode() {
        }
    }
}
