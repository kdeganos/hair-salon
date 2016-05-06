import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;


public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void stylist_instantiatesCorrectly() {
    Stylist stylist = new Stylist("Name");
    assertEquals(true, stylist instanceof Stylist);
  }

  @Test
  public void getName_returnsName_Name() {
    Stylist stylist = new Stylist("Name");
    assertEquals("Name", stylist.getName());
  }

  @Test
  public void all_initiallyEmpty_0() {
    Stylist stylist = new Stylist("Name");
    assertEquals(0, Stylist.all().size());
  }

}
