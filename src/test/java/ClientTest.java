import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;


public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void client_instantiatesCorrectly() {
    Client client = new Client("Name", 1);

    assertEquals(true, client instanceof Client);
  }

  @Test
  public void getName_returnsName_Name() {
    Client client = new Client("Name", 1);

    assertEquals("Name", client.getName());
  }

  @Test
  public void all_initiallyEmpty_0() {
    Client client = new Client("Name", 1);

    assertEquals(0, Client.all().size());
  }

  @Test
  public void equals_returnsTrueIfBothNamesSame_true() {
    Client client1 = new Client("Name", 1);
    Client client2 = new Client("Name", 1);

    assertTrue(client1.equals(client2));
  }

  @Test
  public void save_savesInstanceToDBWithId() {
    Client client = new Client("Name", 1);
    client.save();
    Client savedClient = Client.all().get(0);

    assertEquals(client.getId(), savedClient.getId());
  }

  @Test
  public void find_returnsCorrectInstanceOfClient() {
    Client client = new Client("Name", 1);
    client.save();
    Client foundClient = Client.find(client.getId());

    assertTrue(client.equals(foundClient));
  }

  @Test
  public void delete_removesStylistFromDB() {
    Stylist stylist = new Stylist("Name");
    stylist.save();
    stylist.delete();

    assertFalse(Stylist.all().contains(stylist));
  }
}
