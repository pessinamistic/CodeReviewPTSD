package FileSharing;

import java.util.ArrayList;
import java.util.List;

public class MyHashMap {
  int hash = 10000;
  List<Integer>[] hashMap;

  public MyHashMap() {
    hashMap = (ArrayList<Integer>[]) new ArrayList[hash];
    for (int i = 0; i < hash; i++) {
      hashMap[i] = new ArrayList<>();
    }
  }

  public void put(int key, int value) {
    int index = key % hash;
    if (hashMap[index].size() <= key) {
      for (int i = hashMap[index].size(); i <= key; i++) {
        hashMap[index].add(-1);
      }
    }
    hashMap[index].set(key, value);
  }

  public int get(int key) {
    int index = key % hash;
    if (key < hashMap[index].size()) {
      return hashMap[index].get(key);
    }
    return -1;
  }

  public void remove(int key) {
    int index = key % hash;
    if (key < hashMap[index].size()) {
      hashMap[index].set(key, -1);
    }
  }
}
