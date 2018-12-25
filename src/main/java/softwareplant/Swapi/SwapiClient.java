package softwareplant.Swapi;


import io.joshworks.restclient.http.MediaType;
import io.joshworks.restclient.http.Unirest;
import io.joshworks.restclient.http.mapper.ObjectMappers;
import io.joshworks.restclient.request.GetRequest;
import org.springframework.stereotype.Service;
import softwareplant.Configuration.MappingNames;
import softwareplant.Model.Swapi.Film;
import softwareplant.Model.Swapi.Person;
import softwareplant.Model.Swapi.Planet;
import softwareplant.Model.Swapi.Response.FilmResponse;
import softwareplant.Model.Swapi.Response.PersonResponse;
import softwareplant.Model.Swapi.Response.PlanetResponse;
import softwareplant.Model.Swapi.Response.Response;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class SwapiClient implements SwapiService{

    private static final String SEARCH_PARAM_KEY = "search";

    @PostConstruct
    public void initialize(){
        ObjectMappers.register(MediaType.APPLICATION_JSON_TYPE, new JsonMapper());
    }

    @Override
    public List<Film> searchFilms(final String searchParam){
        return pageableGet(MappingNames.SWAPI_FILMS_URL, searchParam, FilmResponse.class);
    }

    @Override
    public List<Film> getAllFilms(){
        return searchFilms(null);
    }

    @Override
    public List<Person> searchPeople(final String searchParam){
        return pageableGet(MappingNames.SWAPI_PEOPLE_URL, searchParam, PersonResponse.class);
    }


    @Override
    public List<Person> getAllPeople(){
        return searchPeople(null);
    }

    @Override
    public List<Planet> searchPlanets(final String searchParam){
        return pageableGet(MappingNames.SWAPI_PLANETS_URL, searchParam, PlanetResponse.class);
    }


    @Override
    public List<Planet> getAllPlanets(){
        return searchPlanets(null);
    }

    private static <T extends Response<K>, K> List<K> pageableGet(final String url, final String searchParam, Class<T> responseType){
        List<K> output = new ArrayList<>();

        boolean hasNextPage = true;
        String nextUrl = url;
        T response = null;

        while(hasNextPage)
        {
            response = get(nextUrl)
                    .queryString(SEARCH_PARAM_KEY, searchParam)
                    .asObject(responseType)
                    .getBody();

            output.addAll(Arrays.asList(response.getResults()));

            hasNextPage = response.hasNextPage();
            nextUrl = response.getNext();
        }

        return output;
    }

    private static GetRequest get(final String url){
        return Unirest.get(url)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
