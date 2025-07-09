package flipkart.shashank_shekhar_flipkart.src;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

enum Role {
  LEAD,
  DEVELOPER
}

enum ProjectStatus {
  OPEN,
  REQUESTED,
  ASSIGNED,
  IN_PROGRESS,
  COMPLETED,
  CANCELLED
}

enum Category {
  FRONTEND,
  BACKEND,
  DEVOPS,
  WEB_DEVELOPMENT,
  DESIGN
}

record User(int id, String name, Role role) {}

record Request(int id, int developerId, long requestTime) {}

record Rating(int developerId, int projectId, int score) {}

class Project {
  static AtomicInteger requestIdCounter = new AtomicInteger();
  int projectId;
  String title;
  List<Category> category;
  Integer developerId = null;
  int leadId;
  ProjectStatus projectStatus = ProjectStatus.OPEN;
  long createdAt;
  Map<Integer, Request> requests = new ConcurrentHashMap<>();

  public Project(int projectId, String title, int leadId, List<Category> category) {
    this.projectId = projectId;
    this.title = title;
    this.leadId = leadId;
    this.category = category;
    createdAt = System.currentTimeMillis();
  }

  public int getProjectId() {
    return projectId;
  }

  public String getTitle() {
    return title;
  }

  public List<Category> getCategory() {
    return category;
  }

  public Integer getDeveloperId() {
    return developerId;
  }

  public int getLeadId() {
    return leadId;
  }

  public ProjectStatus getProjectStatus() {
    return projectStatus;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public Map<Integer, Request> getRequests() {
    return requests;
  }

  public Project setDeveloperId(Integer developerId) {
    this.developerId = developerId;
    return this;
  }

  public void setProjectStatus(ProjectStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

  @Override
  public String toString() {
    return "Project{"
        + "projectId="
        + projectId
        + ", title='"
        + title
        + '\''
        + ", category="
        + category
        + ", developerId="
        + developerId
        + ", leadId="
        + leadId
        + ", projectStatus="
        + projectStatus
        + ", createdAt="
        + createdAt
        + ", requests="
        + requests
        + '}';
  }
}

class InMemoryDataBase {
  public static Map<Integer, User> userMap = new ConcurrentHashMap<>();
  public static Map<Integer, Project> projectMap = new ConcurrentHashMap<>();
  public static Map<Integer, List<Rating>> ratings = new ConcurrentHashMap<>();
  public static Map<Integer, Project> developerProjectMap = new ConcurrentHashMap<>();
  public static Map<Integer, Deque<Integer>> favorites = new ConcurrentHashMap<>();
}

public class Flipkart {

  public static void main(String[] args) {
    try {

      UserService userService = UserService.getInstance();
      ProjectService projectService = ProjectService.getInstance();

      User lead1 = null, lead2 = null, developer1 = null, developer2 = null;

      try {
        lead1 = userService.addUser(new User(1, "Shashank", Role.LEAD));
        lead2 = userService.addUser(new User(2, "Shashwat", Role.LEAD));
        developer1 = userService.addUser(new User(3, "Priya", Role.DEVELOPER));
        developer2 = userService.addUser(new User(4, "Vignesh", Role.DEVELOPER));
      } catch (Exception e) {
        System.err.println("Error adding user: " + e.getMessage());
      }

      Project project1 = null, project2 = null, project3 = null;
      try {
        project1 =
            projectService.postProject(
                new Project(
                    101,
                    "App Development",
                    lead1.id(),
                    List.of(Category.WEB_DEVELOPMENT, Category.DESIGN)));
        project2 =
            projectService.postProject(
                new Project(102, "K8s Cluster Setup", lead2.id(), List.of(Category.DEVOPS)));
        project3 =
            projectService.postProject(
                new Project(103, "Setup CI / CD", lead2.id(), List.of(Category.DEVOPS)));
      } catch (Exception e) {
        System.err.println("Error posting project: " + e.getMessage());
      }

      List<Project> projects = null;
      try {
        projects = projectService.viewAvailableProjects();
        System.out.println("List of available Projects");
        for (Project project : projects) {
          System.out.println(project);
        }
      } catch (Exception e) {
        System.err.println("Error viewing available projects: " + e.getMessage());
      }

      Thread.sleep(1000);

      try {
        Request request1 = projectService.requestProject(developer1.id(), project1.projectId);
        Request request2 = projectService.requestProject(developer2.id(), project1.projectId);
        Request request3 = projectService.requestProject(developer2.id(), project2.projectId);

        Thread.sleep(1000);

        projectService.approveRequest(lead1.id(), project1.projectId, request1.id());
        projectService.markProjectCompleted(developer1.id(), project1.projectId);
        projectService.rateDeveloper(lead1.id(), project1.projectId, 5);

        // These calls were in your original code but may cause exceptions; wrapping in try-catch
        try {
          projectService.approveRequest(1, 101, 2);
          projectService.markProjectCompleted(2, 101);
          projectService.rateDeveloper(1, 101, 5);
        } catch (Exception e) {
          System.err.println("Error approving or completing project: " + e.getMessage());
        }

        List<Integer> top = projectService.getTopDevelopersByRating();
        System.out.println("Top developers: " + top);

        projectService.starProject(developer2.id(), project1.projectId);
        projectService.starProject(developer2.id(), project2.projectId);
        projectService.starProject(developer2.id(), project3.projectId);
        projectService.unstarProject(developer2.id(), project2.projectId);
        List<Integer> favs = projectService.getLastNStarredProjects(developer2.id(), 2);
        System.out.println("Last 2 starred projects by Developer 2: " + favs);

        Thread.sleep(11000); // this is for auto Cancelling mechanism

        System.out.println(
            "Available projects after potential auto-cancel:"
                + projectService.viewAvailableProjects());
      } catch (Exception e) {
        System.err.println("Error processing project requests or updates: " + e.getMessage());
      }

    } catch (Exception e) {
      System.err.println("Exception caught in main: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

class UserService {

  private static UserService INSTANCE;

  private UserService() {}

  public static UserService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UserService();
    }
    return INSTANCE;
  }

  public User addUser(User user) {
    InMemoryDataBase.userMap.put(user.id(), user);
    String message = user.role() == Role.LEAD ? "%s Lead registered" : "%s Developer registered";
    message = String.format(message, user.name());
    System.out.println(message);
    return InMemoryDataBase.userMap.get(user.id());
  }
}

class ProjectService {

  private static ProjectService INSTANCE;

  private ProjectService() {}

  public static ProjectService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ProjectService();
    }
    return INSTANCE;
  }

  private static final ScheduledExecutorService scheduledExecutorService =
      Executors.newScheduledThreadPool(1);

  public Project postProject(Project project) {
    InMemoryDataBase.projectMap.put(project.getProjectId(), project);
    System.out.println(
        project.getTitle()
            + " with ID : "
            + project.getProjectId()
            + " is registered by Lead : "
            + InMemoryDataBase.userMap.get(project.getLeadId()).name());
    scheduledExecutorService.schedule(
        () -> autoCancelProject(project.projectId), 10, TimeUnit.SECONDS);
    return InMemoryDataBase.projectMap.get(project.getProjectId());
  }

  public List<Project> viewAvailableProjects() {
    List<Project> availableProjects = new ArrayList<>();
    for (Map.Entry<Integer, Project> project : InMemoryDataBase.projectMap.entrySet()) {
      Project value = project.getValue();
      if (value.getProjectStatus() == ProjectStatus.OPEN
          || value.getProjectStatus() == ProjectStatus.REQUESTED) {
        availableProjects.add(value);
      }
    }
    return availableProjects;
  }

  public synchronized Request requestProject(int developerId, int projectId) {
    if (InMemoryDataBase.developerProjectMap.containsKey(developerId)) {
      throw new DeveloperBusyException("Developer already working on a project.");
    }

    User user = InMemoryDataBase.userMap.get(developerId);
    if (user == null) {
      throw new UserNotFoundException("Developer not found.");
    }

    Project project = InMemoryDataBase.projectMap.get(projectId);
    if (project == null) {
      throw new ProjectNotFoundException("Project not found.");
    }

    if (project.getProjectStatus() == ProjectStatus.CANCELLED) {
      throw new InvalidProjectStateException("Project is cancelled.");
    }

    project.setProjectStatus(ProjectStatus.REQUESTED);
    int requestId = Project.requestIdCounter.getAndIncrement();
    Request request = new Request(requestId, developerId, System.currentTimeMillis());
    project.getRequests().put(requestId, request);
    System.out.println("Request with ID " + requestId + " for project " + project.getTitle());
    return request;
  }

  public synchronized void approveRequest(int leadId, int projectID, int requestId) {
    User user = InMemoryDataBase.userMap.get(leadId);
    if (user == null) {
      throw new UserNotFoundException("Lead not found.");
    }

    Project project = InMemoryDataBase.projectMap.get(projectID);
    if (project == null) {
      throw new ProjectNotFoundException("Project not found.");
    }

    if (project.getProjectStatus() == ProjectStatus.CANCELLED) {
      throw new InvalidProjectStateException("Project is cancelled.");
    }

    Request request = project.requests.get(requestId);
    if (request == null) {
      throw new RequestNotFoundException("Request not found.");
    }

    if (project.getDeveloperId() != null) {
      throw new AlreadyAssignedException("Project already has a developer assigned.");
    }

    project.setProjectStatus(ProjectStatus.ASSIGNED);
    project.setDeveloperId(request.developerId());
    InMemoryDataBase.developerProjectMap.put(request.developerId(), project);

    System.out.println("Developer " + request.developerId() + " assigned to project " + projectID);
  }

  public synchronized void startProject(int developerId, int projectID) {
    Project project = InMemoryDataBase.projectMap.getOrDefault(projectID, null);
    if (project == null || project.getProjectStatus() == ProjectStatus.CANCELLED) {
      String message =
          project == null ? "Project is not Present in System" : "Project is Cancelled";
      System.out.println(message);
      return;
    }
    User developer = InMemoryDataBase.userMap.getOrDefault(developerId, null);
    if (developer == null) {
      System.out.println("Developer is not Present in System");
      return;
    }
    if (project.getDeveloperId() == developerId
        && project.getProjectStatus() == ProjectStatus.ASSIGNED) {
      project.setProjectStatus(ProjectStatus.IN_PROGRESS);
      System.out.println(
          "Project : " + project.getTitle() + " stated for developer : " + developer.name());
    }
  }

  public synchronized void cancelProject(int leadId, int projectId) {
    Project project = InMemoryDataBase.projectMap.getOrDefault(projectId, null);
    if (project != null
        && project.leadId == leadId
        && project.getProjectStatus() != ProjectStatus.IN_PROGRESS) {
      project.setProjectStatus(ProjectStatus.CANCELLED);
      project.requests.clear();
      System.out.println("Project " + projectId + " cancelled.");
    }
  }

  public synchronized void markProjectCompleted(int developerId, int projectId) {
    Project project = InMemoryDataBase.projectMap.getOrDefault(projectId, null);
    if (project != null && project.developerId == developerId) {
      project.setProjectStatus(ProjectStatus.COMPLETED);
      InMemoryDataBase.developerProjectMap.remove(developerId);
      System.out.println("Project " + projectId + " completed by Developer " + developerId);
    }
  }

  public void rateDeveloper(int leadId, int projectId, int rating) {
    Project project = InMemoryDataBase.projectMap.getOrDefault(projectId, null);
    if (project != null
        && project.getProjectStatus() == ProjectStatus.COMPLETED
        && project.leadId == leadId) {
      InMemoryDataBase.ratings
          .computeIfAbsent(project.developerId, k -> new ArrayList<>())
          .add(new Rating(project.developerId, projectId, rating));
      System.out.println("Rated developer " + project.developerId + " with " + rating);
    }
  }

  public List<Integer> getTopDevelopersByRating() {
    return InMemoryDataBase.ratings.entrySet().stream()
        .sorted(
            (a, b) ->
                Double.compare(
                    b.getValue().stream().mapToInt(r -> r.score()).average().orElse(0),
                    a.getValue().stream().mapToInt(r -> r.score()).average().orElse(0)))
        .map(Map.Entry::getKey)
        .toList();
  }

  public void starProject(int developerId, int projectId) {
    InMemoryDataBase.favorites.computeIfAbsent(developerId, k -> new LinkedList<>());
    Deque<Integer> favs = InMemoryDataBase.favorites.get(developerId);
    if (favs.contains(projectId)) {
      favs.remove(projectId);
    }
    favs.addLast(projectId);
    System.out.println("Developer " + developerId + " starred project " + projectId);
  }

  public void unstarProject(int developerId, int projectId) {
    Deque<Integer> favs = InMemoryDataBase.favorites.get(developerId);
    if (favs != null && favs.remove(projectId)) {
      System.out.println("Developer " + developerId + " unstarred project " + projectId);
    }
  }

  public List<Integer> getLastNStarredProjects(int developerId, int n) {
    Deque<Integer> favs = InMemoryDataBase.favorites.getOrDefault(developerId, new LinkedList<>());
    List<Integer> result = new ArrayList<>();
    Iterator<Integer> descIt = favs.descendingIterator();
    while (descIt.hasNext() && result.size() < n) {
      result.add(descIt.next());
    }
    return result;
  }

  private synchronized void autoCancelProject(int projectId) {
    Project project = InMemoryDataBase.projectMap.getOrDefault(projectId, null);
    if (project != null && project.getProjectStatus() == ProjectStatus.OPEN) {
      project.setProjectStatus(ProjectStatus.CANCELLED);
      System.out.println("Auto-cancelled project " + projectId);
    }
  }
}

class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}

class ProjectNotFoundException extends RuntimeException {
  public ProjectNotFoundException(String message) {
    super(message);
  }
}

class InvalidProjectStateException extends RuntimeException {
  public InvalidProjectStateException(String message) {
    super(message);
  }
}

class RequestNotFoundException extends RuntimeException {
  public RequestNotFoundException(String message) {
    super(message);
  }
}

class AlreadyAssignedException extends RuntimeException {
  public AlreadyAssignedException(String message) {
    super(message);
  }
}

class DeveloperBusyException extends RuntimeException {
  public DeveloperBusyException(String message) {
    super(message);
  }
}
