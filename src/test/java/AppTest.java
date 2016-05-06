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
}
