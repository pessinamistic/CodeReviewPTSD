package FileSharing;

import java.util.ArrayList;
import java.util.List;

public class MyHashSet {
  int hash = 10000;
  List<Integer>[] hashSet;

  public MyHashSet() {
    hashSet = new ArrayList[hash];
    for (int i = 0; i < hash; i++) {
      hashSet[i] = new ArrayList<>();
    }
  }

  public void add(int key) {
    int index = key % hash;
    if (!hashSet[index].contains(key)) {
      hashSet[index].add(key);
    }
  }

  public void remove(int key) {
    int index = key % hash;
    hashSet[index].remove(Integer.valueOf(key));
  }

  public boolean contains(int key) {
    int index = key % hash;
    return hashSet[index].contains(key);
  }
}
