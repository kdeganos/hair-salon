import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/add-stylist", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/add-stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/list-stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("stylists", Stylist.all());
      model.put("template", "templates/list-stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/list-stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("stylistName");
      Stylist newStylist = new Stylist(name);

      newStylist.save();

      boolean addingNewStylist = true;

      model.put("stylists", Stylist.all());
      model.put("addingNewStylist", addingNewStylist);
      model.put("template", "templates/list-stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));

      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/add-client/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));

      model.put("stylist", stylist);
      model.put("template", "templates/add-client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylist/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int stylist_id = Integer.parseInt(request.params(":id"));
      Stylist stylist = Stylist.find(stylist_id);

      String clientName = request.queryParams("clientName");
      int rating = Integer.parseInt(request.queryParams("rating"));

      Client newClient = new Client(clientName, rating, stylist_id);
      newClient.save();
      stylist.updateRating(rating, true); //boolean for reviewCounter

      boolean addingNewClient = true;

      model.put("addingNewClient", addingNewClient);
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/list-clients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      model.put("clients", Client.all());
      model.put("template", "templates/list-clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Client client = Client.find(Integer.parseInt(request.params(":id")));

      Stylist stylist = Stylist.find(client.getStylistId());

      model.put("client", client);
      model.put("stylist", stylist);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/delete-client/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Client client = Client.find(Integer.parseInt(request.params(":id")));
      Stylist stylist = Stylist.find(client.getStylistId());

      stylist.updateRating(client.getRating(), false); //boolean for reviewCounter
      client.delete();

      boolean deletingClient = true;

      model.put("deletingClient", deletingClient);
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/delete-stylist/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));

      stylist.delete();

      boolean deletingStylist = true;

      model.put("deletingStylist", deletingStylist);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/list-stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/client/update/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Client client = Client.find(Integer.parseInt(request.params(":id")));

      Stylist stylist = Stylist.find(client.getStylistId());

      boolean update = true;

      model.put("update", update);
      model.put("client", client);
      model.put("stylist", stylist);
      model.put("stylists", Stylist.all());
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/client/update/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      Client client = Client.find(Integer.parseInt(request.params(":id")));

      String clientName = request.queryParams("clientName");
      int rating = Integer.parseInt(request.queryParams("rating"));
      int stylist_id = Integer.parseInt(request.queryParams("stylist_id"));

      Stylist stylist = Stylist.find(client.getStylistId());

      stylist.updateRating(client.getRating(), false); //remove old rating
      client.update(clientName, rating, stylist_id);

      stylist = Stylist.find(stylist_id);
      stylist.updateRating(rating, true); //add new rating

      boolean updatingClient = true;

      model.put("updatingClient", updatingClient);
      model.put("client", client);
      model.put("stylist", stylist);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
