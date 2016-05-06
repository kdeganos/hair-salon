import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;


public class AppTest extends FluentTest{

  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
   return webDriver;
 }

 @ClassRule
 public static ServerRule server = new ServerRule();

 @Rule
 public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
   goTo("http://localhost:4567/");
   assertThat(pageSource()).contains("Hair Salon");
  }

  @Test
  public void addStylistPageTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A Stylist"));
    assertThat(pageSource()).contains("Stylist Name");
  }

  @Test
  public void addStylistFormTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A Stylist"));
    fill("#stylistName").with("Name 1");
    submit(".btn", withText("Add"));
    assertThat(pageSource()).contains("Name 1");
  }

  @Test
  public void stylistPageTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A Stylist"));
    fill("#stylistName").with("Name 1");
    submit(".btn", withText("Add"));
    click("a", withText("Name 1"));
    assertThat(pageSource()).contains("Name 1");
  }

  @Test
  public void addClientPageTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add A Stylist"));
    fill("#stylistName").with("Name 1");
    submit(".btn", withText("Add"));
    click("a", withText("Name 1"));
    click("a", withText("Add a Client"));
    assertThat(pageSource()).contains("Client Name");
  }

  @Test
  public void addClientFormTest() {
    Stylist stylist = new Stylist("Name 1");
    stylist.save();
    String categoryPath = String.format("http://localhost:4567/stylist/%d", stylist.getId());
    goTo(categoryPath);
    click("a", withText("Add a Client"));
    fill("#clientName").with("Client 1");
    submit(".btn", withText("Add"));
    assertThat(pageSource()).contains("Client 1");
  }

  @Test
  public void listClientsPageTest() {
    Stylist stylist = new Stylist("Name 1");
    stylist.save();
    Client client = new Client("Client 1", stylist.getId());
    client.save();
    goTo("http://localhost:4567/list-clients");
    assertThat(pageSource()).contains("Client 1");
  }

  @Test
  public void clientPageTest() {
    Stylist stylist = new Stylist("Name 1");
    stylist.save();
    Client client = new Client("Client 1", stylist.getId());
    client.save();
    String categoryPath = String.format("http://localhost:4567/client/%d", client.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Client 1");
  }
}
