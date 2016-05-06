import java.util.List;
import org.sql2o.*;

public class Client{
  private String name;
  private int id;
  private int stylist_id;

  public Client (String name, int stylist_id) {
    this.name = name;
    this.stylist_id = stylist_id;
   }

  public String getName() {
     return name;
   }

  public int getId() {
     return id;
  }

  public int getStylistId() {
    return stylist_id;
  }

  public static List<Client> all() {
    String sql = "SELECT * FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object client) {
    if (!(client instanceof Client)) {
      return false;
    } else {
      Client otherClient = (Client) client;
      return this.getName().equals(otherClient.getName());
    }
  }

  public void save() {
    String sql = "INSERT INTO clients (name, stylist_id) VALUES (:name, :stylist_id)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
                .addParameter("name", this.name)
                .addParameter("stylist_id", this.stylist_id)
                .executeUpdate()
                .getKey();
    }
  }

  public static Client find(int id) {
    String sql = "SELECT * FROM clients WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Client.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
    }
  }
}
