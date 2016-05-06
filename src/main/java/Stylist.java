import java.util.List;
import org.sql2o.*;
import java.text.DecimalFormat;

public class Stylist{
  private String name;
  private int id;
  private float rating = 0f;
  private float review_total = 0f;
  private int review_counter = 0;
  private static DecimalFormat df2 = new DecimalFormat(".##");

  public Stylist (String name) {
    this.name = name;
   }

  public String getName() {
     return name;
   }

  public int getId() {
     return id;
  }

  public float getRating() {
      return Float.parseFloat(df2.format(rating));
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  @Override
  public boolean equals(Object stylist) {
    if (!(stylist instanceof Stylist)) {
      return false;
    } else {
      Stylist otherStylist = (Stylist) stylist;
      return this.getName().equals(otherStylist.getName());
    }
  }

  public void save() {
    String sql = "INSERT INTO stylists (name) VALUES (:name)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
                .addParameter("name", this.name)
                .executeUpdate()
                .getKey();
    }
  }

  public static Stylist find(int id) {
    String sql = "SELECT * FROM stylists WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Stylist.class);
    }
  }

  public List<Client> allStylistClients() {
    String sql = "SELECT id, name FROM clients WHERE stylist_id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetch(Client.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
    }
    String sql2 = "DELETE FROM clients WHERE stylist_id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql2).addParameter("id", this.id).executeUpdate();
    }
  }

  public void updateRating(int reviewRating, boolean add) {

    if (add){
      review_counter++;
      review_total += (float) reviewRating;
    } else {
      review_counter--;
      review_total -= (float) reviewRating;
    }

    if (review_counter > 0){
    rating = review_total / (float) review_counter;
  } else {
    rating = 0f;
  }

    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET rating= :rating WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("rating", this.rating)
        .addParameter("id", this.id)
        .executeUpdate();
      String sql2 = "UPDATE stylists SET review_counter= :review_counter WHERE id = :id";
      con.createQuery(sql2, true)
        .addParameter("review_counter", this.review_counter)
        .addParameter("id", this.id)
      .executeUpdate();
      String sql3 = "UPDATE stylists SET review_total = :review_total WHERE id = :id";
      con.createQuery(sql3, true)
        .addParameter("review_total", this.review_total)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
