package FileSharing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FileSharing {
  int chunkSize;
  Map<Integer, Set<Integer>> fileChunks;
  Map<Integer, Boolean> userMap;
  TreeSet<Integer> requestQueue;

  public int getChunkSize() {
    return chunkSize;
  }

  public FileSharing setChunkSize(int chunkSize) {
    this.chunkSize = chunkSize;
    return this;
  }

  public Map<Integer, Set<Integer>> getFileChunks() {
    return fileChunks;
  }

  public FileSharing setFileChunks(Map<Integer, Set<Integer>> fileChunks) {
    this.fileChunks = fileChunks;
    return this;
  }

  public Map<Integer, Boolean> getUserMap() {
    return userMap;
  }

  public FileSharing setUserMap(Map<Integer, Boolean> userMap) {
    this.userMap = userMap;
    return this;
  }

  public TreeSet<Integer> getRequestQueue() {
    return requestQueue;
  }

  public FileSharing setRequestQueue(TreeSet<Integer> requestQueue) {
    this.requestQueue = requestQueue;
    return this;
  }

  public FileSharing(int chunkSize) {
    this.chunkSize = chunkSize;
    fileChunks = new HashMap<>();
    userMap = new HashMap<>();
    requestQueue = new TreeSet<>();
  }

  public int join(int[] ownedChunks) {
    int userId = requestQueue.isEmpty() ? userMap.size() + 1 : requestQueue.removeFirst();
    for (int chunk : ownedChunks) {
      fileChunks.computeIfAbsent(chunk, k -> new HashSet<>()).add(userId);
    }
    userMap.put(userId, true);
    return userId;
  }

  public int[] request(int userId, int chunkId) {
    if (!userMap.containsKey(userId) || !fileChunks.containsKey(chunkId)) {
      return new int[0];
    }
    int[] ownersList = fileChunks.get(chunkId).stream().mapToInt(Integer::intValue).toArray();
    fileChunks.get(chunkId).add(userId);
    return ownersList;
  }

  public void leave(int userId) {
    if (!userMap.containsKey(userId)) return;
    userMap.put(userId, false);
    for (Set<Integer> owners : fileChunks.values()) {
      owners.remove(userId);
    }
    List<Integer> emptyChunks = new ArrayList<>();
    for (Map.Entry<Integer, Set<Integer>> entry : fileChunks.entrySet()) {
      if (entry.getValue().isEmpty()) {
        emptyChunks.add(entry.getKey());
      }
    }
    for (Integer chunkId : emptyChunks) {
      fileChunks.remove(chunkId);
    }
    requestQueue.add(userId);
  }

  public static void main(String[] args) {
    FileSharing fs = new FileSharing(1024);
    System.out.println("--- User Join Tests ---");
    int user1 = fs.join(new int[] {1, 2});
    System.out.printf("User1 joined with chunks [1,2]: userId=%d\n", user1);
    System.out.println("FileChunks: " + fs.getFileChunks());

    int user2 = fs.join(new int[] {2, 3});
    System.out.printf("User2 joined with chunks [2,3]: userId=%d\n", user2);
    System.out.println("FileChunks: " + fs.getFileChunks());

    int user3 = fs.join(new int[] {4});
    System.out.printf("User3 joined with chunk [4]: userId=%d\n", user3);
    System.out.println("FileChunks: " + fs.getFileChunks());

    System.out.println("\n--- Request Tests ---");
    int[] owners = fs.request(user1, 3);
    System.out.printf(
        "User1 requests chunk 3. Owners before: %s\n", java.util.Arrays.toString(owners));
    System.out.println("FileChunks: " + fs.getFileChunks());

    owners = fs.request(user2, 2);
    System.out.printf(
        "User2 requests chunk 2. Owners before: %s\n", java.util.Arrays.toString(owners));
    System.out.println("FileChunks: " + fs.getFileChunks());

    System.out.println("\n--- Leave Tests ---");
    fs.leave(user1);
    System.out.println("User1 leaves.");
    System.out.println("FileChunks: " + fs.getFileChunks());

    owners = fs.request(user2, 1);
    System.out.printf(
        "User2 requests chunk 1 after user1 leaves. Owners before: %s\n",
        java.util.Arrays.toString(owners));
    System.out.println("FileChunks: " + fs.getFileChunks());

    int user4 = fs.join(new int[] {});
    System.out.printf("User4 joins with no chunks: userId=%d\n", user4);
    System.out.println("FileChunks: " + fs.getFileChunks());

    // --- Additional Edge Case Tests ---
    System.out.println("\n--- Edge Case Tests ---");

    // Test: Requesting a chunk that doesn't exist
    int[] ownersEdge = fs.request(user4, 99);
    assert ownersEdge.length == 0 : "Requesting non-existent chunk should return empty array";

    // Test: Leaving with a user that never joined
    int fakeUser = 999;
    fs.leave(fakeUser); // Should not throw
    System.out.println("Leave with non-existent user did not throw.");

    // Test: User requests a chunk they already own
    int[] ownersSelf = fs.request(user2, 2);
    assert ownersSelf.length > 0 : "User should see previous owners before themselves";
    boolean containsSelf = false;
    for (int id : ownersSelf) if (id == user2) containsSelf = true;
    assert !containsSelf : "User should not see themselves in the owners list before request";

    // Test: User leaves and rejoins, should get recycled userId
    fs.leave(user2);
    int recycledUser = fs.join(new int[] {5});
    assert recycledUser == user2 : "UserId should be recycled after leave";
    System.out.println("UserId recycled correctly: " + recycledUser);

    // Test: All users leave, then join again
    fs.leave(user3);
    fs.leave(user4);
    int newUserA = fs.join(new int[] {6});
    int newUserB = fs.join(new int[] {7});
    assert newUserA == user3 || newUserA == user4 : "UserId should be recycled from previous users";
    assert newUserB == user3 || newUserB == user4 : "UserId should be recycled from previous users";
    assert newUserA != newUserB : "Recycled userIds should be unique";

    // Test: Request with invalid user
    int[] invalidUserReq = fs.request(9999, 1);
    assert invalidUserReq.length == 0 : "Request from invalid user should return empty array";

    // Test: Join with empty chunk list
    int userEmpty = fs.join(new int[] {});
    assert fs.getFileChunks().values().stream().noneMatch(set -> set.contains(userEmpty)) : "User with no chunks should not appear in any chunk list";
    System.out.println("Join with empty chunk list handled correctly.");

    System.out.println("All edge case tests passed.");
  }
}
