import java.io.*;
import java.util.*;
import java.net.URL;

public class WordAnagram {
    class TrieNode{
        TrieNode[] children = new TrieNode[size];
        // leaf is true if the node is the end of a word
        boolean leaf;

        public TrieNode(){
            leaf = false;
            for(int i = 0; i < size; i++){
                children[i] = null;
            }
        }
    }

    static final int size = 26;
    TrieNode root;

    public static List<String> createDict(String dictUrl) {
        List<String> dict = new ArrayList<>();
        // import all the words
        try {
            URL url = new URL(dictUrl);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null){
                dict.add(inputLine.toLowerCase());
            }
            in.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return dict;
    }

    public WordAnagram(List<String> dict){
        root = new TrieNode();

        // insert all the words in the dict
        for(int i = 0; i < dict.size(); i++){
            insert(root, dict.get(i));
        }

    }

    public List<String> check(HashSet<Character> set){
        List<String> res = new ArrayList<>();
        HashSet<Character> lowCaseSet = new HashSet<>();
        for(char ch : set){
            char c = Character.toLowerCase(ch);
            lowCaseSet.add(c);
        }
        search(root, "", lowCaseSet, res);
        return res;
    }

    // if not present, insert the word into the trie
    private void insert(TrieNode root, String word) {
        // like word "jean-christophe", not insert
        for(int i = 0; i < word.length(); i++){
            char ch = word.charAt(i);
            if(!Character.isLetter(ch)) return;
        }
        int n = word.length();
        TrieNode cur = root;

        for(int i = 0; i < n; i++){
            int index = word.charAt(i) - 'a';
            if (index >= 26 || index < 0) return;
            if(cur.children[index] == null){
                cur.children[index] = new TrieNode();
            }
            cur = cur.children[index];
        }

        // make the last node as leaf node
        cur.leaf = true;
    }

    private List<String> search(TrieNode root, String str, HashSet<Character> set, List<String> res){

        // if word found in trie
        if(root.leaf) res.add(str);

        // traverse all children of current root
        for(int i = 0; i < size; i++){
            char c = (char) (i + 'a');
            if(set.contains(c) && root.children[i] != null){
                set.remove(c);
                // add current character, and recursively search remaining character of word in trie
                search(root.children[i], str + c, set, res);
                set.add(c);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        HashSet<Character> set = new HashSet<>();
        set.add('e');
        set.add('o');
        set.add('b');
        set.add('a');
        set.add('m');
        set.add('g');
        set.add('l');

        List<String> dict = createDict("https://raw.githubusercontent.com/lad/words/master/words");
        WordAnagram wa = new WordAnagram(dict);
        List<String> result = wa.check(set);
        for (String word : result) {
            System.out.println(word);
        }
    }
}
