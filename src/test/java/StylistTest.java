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

  @Test
  public void equals_returnsTrueIfBothNamesSame_true() {
    Stylist stylist1 = new Stylist("Name");
    Stylist stylist2 = new Stylist("Name");

    assertTrue(stylist1.equals(stylist2));
  }

  @Test
  public void save_savesInstanceToDBWithId() {
    Stylist stylist = new Stylist("Name");
    stylist.save();
    Stylist savedStylist = Stylist.all().get(0);

    assertEquals(stylist.getId(), savedStylist.getId());
  }

}
