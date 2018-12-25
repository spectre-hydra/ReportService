package softwareplant.Swapi;


import softwareplant.Model.Swapi.Film;
import softwareplant.Model.Swapi.Person;
import softwareplant.Model.Swapi.Planet;

import java.util.List;
import java.util.function.Predicate;

public interface SwapiService {

    List<Film> searchFilms(final String searchParam);

    List<Film> getAllFilms();

    List<Person> searchPeople(final String searchParam);

    List<Person> getAllPeople();

    List<Planet> searchPlanets(final String searchParam);

    List<Planet> getAllPlanets();
}
