import java.util.List;
import org.sql2o.*;

public class Stylist{
  private String name;
  private int id;

  public Stylist (String name) {
    this.name = name;
   }

  public String getName() {
     return name;
   }

  public int getId() {
     return id;
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
}
