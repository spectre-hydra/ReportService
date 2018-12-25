package Integration;

import io.joshworks.restclient.http.JsonNode;
import io.joshworks.restclient.http.Unirest;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import softwareplant.Application;
import softwareplant.Configuration.MappingNames;
import softwareplant.Swapi.SwapiService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {Application.class})
public class SwapiIntegrationTest {


    @Autowired
    private SwapiService swapiService;

    @Test
    public void testSwapiPeopleIntegrity1(){

        JSONObject object = getObject(MappingNames.SWAPI_PEOPLE_URL + "/1/");

        assertEquals(object.getString("name"), "Luke Skywalker");
        assertTrue(object.getJSONArray("films").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_PEOPLE_URL + "/1/");
        assertEquals(object.getString("homeworld"), MappingNames.SWAPI_PLANETS_URL + "/1/");
    }

    @Test
    public void testSwapiPeopleIntegrity2(){

        JSONObject object = getObject(MappingNames.SWAPI_PEOPLE_URL + "/3/");

        assertEquals(object.getString("name"), "R2-D2");
        assertTrue(object.getJSONArray("films").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_PEOPLE_URL + "/3/");
        assertEquals(object.getString("homeworld"), MappingNames.SWAPI_PLANETS_URL + "/8/");
    }


    @Test
    public void testSwapiFilmsIntegrity1(){

        JSONObject object = getObject(MappingNames.SWAPI_FILMS_URL + "/1/");

        assertEquals(object.getString("title"), "A New Hope");
        assertTrue(object.getJSONArray("characters").length() > 0);
        assertTrue(object.getJSONArray("planets").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_FILMS_URL + "/1/");
    }

    @Test
    public void testSwapiFilmsIntegrity2(){

        JSONObject object = getObject(MappingNames.SWAPI_FILMS_URL + "/3/");

        assertEquals(object.getString("title"), "Return of the Jedi");
        assertTrue(object.getJSONArray("characters").length() > 0);
        assertTrue(object.getJSONArray("planets").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_FILMS_URL + "/3/");
    }

    @Test
    public void testSwapiPlanetsIntegrity1(){

        JSONObject object = getObject(MappingNames.SWAPI_PLANETS_URL + "/1/");

        assertEquals(object.getString("name"), "Tatooine");
        assertTrue(object.getJSONArray("residents").length() > 0);
        assertTrue(object.getJSONArray("films").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_PLANETS_URL + "/1/");
    }

    @Test
    public void testSwapiPlanetsIntegrity2(){

        JSONObject object = getObject(MappingNames.SWAPI_PLANETS_URL + "/3/");

        assertEquals(object.getString("name"), "Yavin IV");
        assertTrue(object.getJSONArray("residents").length() == 0);
        assertTrue(object.getJSONArray("films").length() > 0);
        assertEquals(object.getString("url"), MappingNames.SWAPI_PLANETS_URL + "/3/");
    }

    private JSONObject getObject(final String url)
    {
        JsonNode node = Unirest.get(url)
                .asJson().getBody();

        return node.getObject();
    }

    @Test
    public void testFilmsSearch(){
        assertEquals(swapiService.searchFilms("pe").size(), 1);
    }

    @Test
    public void testPlanetsSearch(){
        assertEquals(swapiService.searchPlanets("Ta").size(), 5);
    }

    @Test
    public void testPeopleSearch(){
        assertEquals(swapiService.searchPeople("Lu").size(), 2);
    }

    @Test
    public void testFilmsAvailability(){
        assertTrue(swapiService.getAllFilms().size() > 0);
    }

    @Test
    public void testPeopleAvailability(){
        assertTrue(swapiService.getAllPeople().size() > 0);
    }

    @Test
    public void testPlanetsAvailability(){
        assertTrue(swapiService.getAllPlanets().size() > 0);
    }

}
