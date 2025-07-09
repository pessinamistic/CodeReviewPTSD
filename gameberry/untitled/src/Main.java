package gameberry.untitled.src;/*
Given a list of projects and a list of project dependencies. Dependencies are set of project pairs, where 2nd project depends on the 1st project. All the dependencies of a project should be build before the project. Find the build order of the projects.

E.g.,
Projects = {1, 2, 3, 'D', 4, ‘F’}
Dependencies = {{1, 'D'}, {'F', 2}, {2, 'D'}, {'F', 1}, {'D', 3}}
One of the Build order = {4, 4, 2, 1, 'D', 3}


List<char> FindBuildOrder(List<char> projects, List<List<char>> dependencies)
*/

/*
 * Click `Run` to execute the snippet below!
 */

import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution2 {
  public static void main(String[] args) {
    int[] projects = new int[] {1, 2, 3, 4, 5, 6};
    int[][] graph = new int[][] {{1, 4}, {6, 2}, {2, 4}, {6, 1}, {4, 3}};
    System.out.println(FindBuildOrder(projects, graph));

    graph = new int[][] {};
    System.out.println(FindBuildOrder(projects, graph));
  }

  static List<Integer> FindBuildOrder(int[] projects, int[][] graph) {
    int no = projects.length;
    int[] inDegree = new int[no];
    List<List<Integer>> adj = createGraph(projects, graph, inDegree);

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < no; i++){
      if (inDegree[i] == 0){
        queue.add(i);
      }
    }

    List<Integer> topo = new ArrayList<>();
    while (!queue.isEmpty()){
      int node = queue.poll();
      no--;
      topo.add(node);
      for (int neighbour : adj.get(node)){
        inDegree[neighbour]--;
        if (inDegree[neighbour] == 0){
          queue.add(neighbour);
        }
      }
    }

    if (no != 0){
      System.out.println("There is a Cycle");
      topo.clear();
    }
    return topo;
  }

  static List<List<Integer>> createGraph(int[] projects, int[][] graph, int[] inDegree) {
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < projects.length; i++) {
      result.add(new ArrayList<>());
    }
    for (int i = 0; i < graph.length; i++) {
      int source = graph[i][0] - 1;
      int destination = graph[i][1] - 1;
      result.get(source).add(destination);
      inDegree[destination]++;
    }
    return result;
  }
}
