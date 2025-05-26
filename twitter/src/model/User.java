package twitter.src.model;

import java.util.Set;

public record User(int id, Set<Integer> followees) {}
