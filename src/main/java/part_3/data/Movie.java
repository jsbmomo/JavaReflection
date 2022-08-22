package part_3.data;

public class Movie {
  private final String name;
  private final float rating;
  private final String[] categories;
  private final Actor[] actors;

  public Movie(String name, float rating, String[] categories, Actor[] actor) {
    this.name = name;
    this.rating = rating;
    this.categories = categories;
    this.actors = actor;
  }
}
