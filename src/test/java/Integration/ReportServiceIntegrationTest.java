package Integration;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.JsonNode;
import io.joshworks.restclient.http.MediaType;
import io.joshworks.restclient.http.Unirest;
import io.joshworks.restclient.http.mapper.ObjectMappers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import softwareplant.Application;
import softwareplant.Repository.ReportRepository;
import softwareplant.Swapi.JsonMapper;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ReportServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReportRepository reportRepository;


    @Before
    public void init() throws Exception {
        ObjectMappers.register(MediaType.APPLICATION_JSON_TYPE, new JsonMapper());
    }

    @Test

    public void getReport1(){

        String expected = "{\"criteria\":{\"characterPhrase\":\"Luke\",\"planetName\":\"Tatooine\"},\"results\":[]}";

        JSONObject response = get(createUrl("/report?characterPhrase=Luke&planetName=Tatooine"));
        JSONAssert.assertEquals(expected, response.toString(), false);
    }

    @Test
    public void getMissingPlanetName(){

        HttpResponse<JsonNode> response = getResponse(createUrl("/report?characterPhrase=Luke"));
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void getMissingCharacterPhrase(){

        HttpResponse<JsonNode> response = getResponse(createUrl("/report"));
        assertEquals(response.getStatus(), 400);
    }

    @Test
    @DirtiesContext
    public void putReport1(){

        put(createUrl("/report"), createBodyPayload("Luke", "Tatooine"));
        assertEquals(reportRepository.count(), 5);
    }

    @Test
    @DirtiesContext
    public void putReport2(){

        put(createUrl("/report"), createBodyPayload("R2-D2", "Naboo"));
        assertEquals(reportRepository.count(), 7);
    }

    @Test
    @DirtiesContext
    public void validateReturnedCriteria(){

        put(createUrl("/report"), createBodyPayload("C-3PO", "Tatooine"));

        JSONObject object = get(createUrl("/report?characterPhrase=C-3PO&planetName=Tatooine"));

        JSONObject criteria = object.getJSONObject("criteria");
        assertEquals(criteria.getString("characterPhrase"), "C-3PO");
        assertEquals(criteria.getString("planetName"), "Tatooine");
    }

    @Test
    @DirtiesContext
    public void putAndGetReport1(){

        put(createUrl("/report"), createBodyPayload("C-3PO", "Tatooine"));

        JSONObject object = get(createUrl("/report?characterPhrase=C-3PO&planetName=Tatooine"));

        JSONArray results = object.getJSONArray("results");
        assertEquals(results.length(), 6);
    }

    @Test
    @DirtiesContext
    public void putAndGetReport2(){

        put(createUrl("/report"), createBodyPayload("Darth Vader", "Tatooine"));

        JSONObject object = get(createUrl("/report?characterPhrase=Darth%20Vader&planetName=Tatooine"));

        JSONArray results = object.getJSONArray("results");
        assertEquals(results.length(), 4);
    }

    @Test
    @DirtiesContext
    public void putAndGetAndDeleteReport1(){

        final String name = "Leia Organa";
        final String planetName = "Alderaan";

        final String query = createQuery(name, planetName);
        final String body = createBodyPayload(name, planetName);

        put(createUrl("/report"), body);

        JSONObject object = get(createUrl(query));

        JSONArray results = object.getJSONArray("results");
        assertEquals(results.length(), 5);

        delete(createUrl(query), body);

        assertEquals(reportRepository.count(), 0);


        object = get(createUrl(query));
        results = object.getJSONArray("results");

        assertEquals(results.length(), 0);
    }


    @Test
    @DirtiesContext
    public void putAndGetAndDeleteReport2(){

        final String name = "Owen Lars";
        final String planetName = "Tatooine";

        final String query = createQuery(name, planetName);
        final String body = createBodyPayload(name, planetName);

        put(createUrl("/report"), body);

        JSONObject object = get(createUrl(query));

        JSONArray results = object.getJSONArray("results");
        assertEquals(results.length(), 3);

        delete(createUrl(query), body);

        assertEquals(reportRepository.count(), 0);


        object = get(createUrl(query));
        results = object.getJSONArray("results");

        assertEquals(results.length(), 0);
    }



    private String createBodyPayload(final String characterPhrase, final String planetName)
    {
        return String.format("{\"characterPhrase\":\"%s\",\"planetName\": \"%s\"}", characterPhrase, planetName);
    }


    private String createQuery(final String characterPhrase, final String planetName)
    {
        return ("/report?characterPhrase=" + characterPhrase + "&planetName=" + planetName).replace(" ", "%20");
    }

    private String createUrl(final String endpointLocation)
    {
        return "http://localhost:" + port + endpointLocation;
    }

    private JSONObject get(final String url)
    {
        JsonNode node = Unirest.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .asJson().getBody();

        return node.getObject();
    }

    private JSONObject post(final String url, final String body)
    {
        JsonNode node = Unirest.post(url).contentType(MediaType.APPLICATION_JSON).body(body)
                .asJson().getBody();

        return node.getObject();
    }

    private JSONObject put(final String url, final String body)
    {
        JsonNode node = Unirest.put(url).contentType(MediaType.APPLICATION_JSON).body(body)
                .asJson().getBody();

        return node.getObject();
    }

    private JSONObject delete(final String url, final String body)
    {
        JsonNode node = Unirest.delete(url).contentType(MediaType.APPLICATION_JSON).body(body)
                .asJson().getBody();

        return node.getObject();
    }

    private HttpResponse<JsonNode> getResponse(final String url)
    {
        return Unirest.get(url).contentType(MediaType.APPLICATION_JSON).asJson();
    }
}