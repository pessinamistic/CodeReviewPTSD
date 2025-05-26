package twitter.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

record Tweet(int id, long timeStamp) {}

public class Twitter {

  public static void main(String[] args) {
    System.out.println("This is Twitter lol !!");

    Twitter twitter = new Twitter();

    twitter.postTweet(1, 5);
    System.out.println(twitter.getNewsFeed(1)); // [5]

    twitter.follow(1, 2);
    twitter.postTweet(2, 6);
    System.out.println(twitter.getNewsFeed(1)); // [6, 5]

    twitter.unfollow(1, 2);
    System.out.println(twitter.getNewsFeed(1));
  }

  AtomicLong timeStamp = new AtomicLong();

  private final Map<Integer, List<Tweet>> userTweetMap;
  private final Map<Integer, Set<Integer>> followersMap;

  public Twitter() {
    userTweetMap = new HashMap<>();
    followersMap = new HashMap<>();
  }

  public void postTweet(int userId, int tweetId) {
    Tweet tweet = new Tweet(tweetId, timeStamp.getAndIncrement());
    userTweetMap.computeIfAbsent(userId, user -> new ArrayList<>()).add(tweet);
  }

  public List<Integer> getNewsFeed(int userId) {
    PriorityQueue<Tweet> tweetPriorityQueue =
            new PriorityQueue<>((a, b) -> Long.compare(b.timeStamp(), a.timeStamp()));

    List<Tweet> selfTweets = userTweetMap.get(userId);
    tweetPriorityQueue.addAll(selfTweets);

    Set<Integer> followees = followersMap.getOrDefault(userId, new HashSet<>());

    for (int followeeId : followees) {
      if (followeeId == userId) continue;
      List<Tweet> followeeTweets = userTweetMap.getOrDefault(followeeId, new ArrayList<>());
      tweetPriorityQueue.addAll(followeeTweets);
    }

    List<Integer> result = new ArrayList<>();
    int count = 0;
    while (!tweetPriorityQueue.isEmpty() && count < 10) {
      result.add(tweetPriorityQueue.poll().id());
      count++;
    }

    return result;
  }

  public void follow(int followerId, int followeeId) {
    if (followerId == followeeId) return;
    followersMap.computeIfAbsent(followerId, user -> new HashSet<>()).add(followeeId);
  }

  public void unfollow(int followerId, int followeeId) {
    if (followerId == followeeId) return;
    followersMap.get(followerId).remove(followeeId);
  }
}
