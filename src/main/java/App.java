import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    // staticfilelocation("/public");
    // String layout = "templates/layout.vtl";
    //
    // get("/", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/index.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/output", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/output.vtl");
    //
    //   Blank blankList = new Blank(); /
    //
    //   String userInputString = request.queryParams("???");
    //   Integer userInputNumber = Integer.parseInt(userInput);
    //
    //   ArrayList myResults = blankList.runBlank(userInputNumber);
    //
    //   model.put("result", myResults);
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
  }
}
