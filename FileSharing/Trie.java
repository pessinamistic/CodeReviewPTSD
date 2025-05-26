package FileSharing;

import java.util.HashMap;
import java.util.Map;

/**
 * Your Trie object will be instantiated and called as such: Trie obj = new Trie();
 * obj.insert(word); boolean param_2 = obj.search(word); boolean param_3 = obj.startsWith(prefix); A
 * trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and
 * retrieve keys in a dataset of strings. There are various applications of this data structure,
 * such as autocomplete and spellchecker.
 *
 * <p>Implement the Trie class:
 *
 * <p>Trie() Initializes the trie object. void insert(String word) Inserts the string word into the
 * trie. boolean search(String word) Returns true if the string word is in the trie (i.e., was
 * inserted before), and false otherwise. boolean startsWith(String prefix) Returns true if there is
 * a previously inserted string word that has the prefix prefix, and false otherwise.
 */
public class Trie {

  TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  public void insert(String word) {
    TrieNode currentNode = root;
    for (char c : word.toCharArray()) {
      currentNode.children.putIfAbsent(c, new TrieNode());
      currentNode = currentNode.children.get(c);
    }
    currentNode.isEndOfWord = true;
  }

  public boolean search(String word) {
    TrieNode currentNode = root;
    for (char c : word.toCharArray()) {
      if (!currentNode.children.containsKey(c)) {
        return false;
      }
      currentNode = currentNode.children.get(c);
    }
    return currentNode.isEndOfWord;
  }

  public boolean startsWith(String prefix) {
    TrieNode currentNode = root;
    for (char c : prefix.toCharArray()) {
      if (!currentNode.children.containsKey(c)) {
        return false;
      }
      currentNode = currentNode.children.get(c);
    }
    return true;
  }

  public static void main(String[] args) {
    Trie trie = new Trie();
    trie.insert("apple");
    trie.visualize();
    System.out.println("\n[TEST] Search for 'apple': " + trie.search("apple") + " (should be true)");
    System.out.println("[TEST] Search for 'app': " + trie.search("app") + " (should be false)");
    System.out.println("[TEST] StartsWith 'app': " + trie.startsWith("app") + " (should be true)");
    trie.insert("app");
    System.out.println("[ACTION] Inserted 'app'");
    trie.visualize();
    System.out.println("[TEST] Search for 'app': " + trie.search("app") + " (should be true)");
    trie.insert("anu");
    System.out.println("[ACTION] Inserted 'anu'");
    trie.visualize();
    trie.insert("and");
    System.out.println("[ACTION] Inserted 'and'");
    trie.visualize();
    trie.insert("android");
    System.out.println("[ACTION] Inserted 'android'");
    trie.visualize();
    System.out.println("[TEST] Search for 'and': " + trie.search("and") + " (should be true)");
    System.out.println("[TEST] StartsWith 'an': " + trie.startsWith("an") + " (should be true)");
    trie.insert("dad");
    System.out.println("[ACTION] Inserted 'dad'");
    trie.visualize();
    trie.insert("do");
    System.out.println("[ACTION] Inserted 'do'");
    trie.visualize();
    trie.insert("dodo");
    System.out.println("[ACTION] Inserted 'dodo'");
    trie.visualize();
    System.out.println("[TEST] Search for 'dodo': " + trie.search("dodo") + " (should be true)");
    System.out.println("[TEST] StartsWith 'd': " + trie.startsWith("d") + " (should be true)");
    System.out.println("[TEST] Search for 'do': " + trie.search("do") + " (should be true)");
  }

  // Visualize the Trie structure as a tree
  public void visualize() {
    System.out.println("Trie visualization:");
    visualizeHelper(root, new StringBuilder(), "", true);
    System.out.println();
  }

  private void visualizeHelper(TrieNode node, StringBuilder prefix, String branch, boolean isTail) {
    if (node == null) return;
    if (prefix.length() > 0) {
      System.out.print(prefix.substring(0, prefix.length() - 2));
      System.out.print(isTail ? "└─" : "├─");
      System.out.print(branch);
      if (node.isEndOfWord) System.out.print(" [END]");
      System.out.println();
    }
    int count = 0;
    int total = 0;
    for (char c = 'a'; c <= 'z'; c++) {
      if (node.children.get(c) != null) total++;
    }
    for (char c = 'a'; c <= 'z'; c++) {
      TrieNode child = node.children.get(c);
      if (child != null) {
        count++;
        StringBuilder newPrefix = new StringBuilder(prefix);
        newPrefix.append(isTail ? "  " : "│ ");
        visualizeHelper(child, newPrefix, String.valueOf(c), count == total);
      }
    }
  }
}

class TrieNode {
  Map<Character, TrieNode> children;
  boolean isEndOfWord = false;

  public TrieNode() {
    children = new HashMap<>(26);
  }
}
