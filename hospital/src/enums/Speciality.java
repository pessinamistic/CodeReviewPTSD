package enums;

public enum Speciality {
  CARDIOLOGIST("Cardiologist"),
  DERMATOLOGIST("Dermatologist"),
  ORTHOPEDIST("Orthopedist"),
  GENERAL_PHYSICIAN("General Physician");

  private final String name;

  Speciality(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
