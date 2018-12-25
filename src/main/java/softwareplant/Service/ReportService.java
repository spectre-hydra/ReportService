package softwareplant.Service;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import softwareplant.Model.Report.Report;
import softwareplant.Model.Report.ReportRequest;
import softwareplant.Model.Swapi.Film;
import softwareplant.Model.Swapi.Person;
import softwareplant.Model.Swapi.Planet;
import softwareplant.Repository.ReportRepository;
import softwareplant.Swapi.SwapiService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportService {


    @Autowired
    private SwapiService swapiService;

    @Autowired
    private ReportRepository reportRepository;

    private static final Logger log = LogManager.getLogger(ReportService.class);

    public void generateReport(ReportRequest reportRequest){

        List<Report> reports = null;

        try {

            // request all planets and filter them locally with the provided planet name
            Map<Long, Planet> planets = swapiService.getAllPlanets()
                    .stream()
                    .filter((p) -> StringUtils.equalsIgnoreCase(p.getName(), reportRequest.getPlanetName()))
                    .collect(Collectors.toMap(Planet::getId, Function.identity()));


            log.info(String.format("Found Planets: %d", planets.size()));


            // find people and filter them by planets
            Map<Long, Person> people = swapiService.searchPeople(reportRequest.getCharacterPhrase())
                    .stream()
                    .filter(person -> planets
                            .entrySet()
                            .stream()
                            .anyMatch(planet -> planet
                                    .getValue()
                                    .getResidents()
                                    .contains(person.getId())))
                    .collect(Collectors.toMap(Person::getId, Function.identity()));

            log.info(String.format("Found People: %d", people.size()));

            // get all films and filter them by the already filtered people
            Map<Long, Film> films = swapiService.getAllFilms()
                    .stream()
                    .filter(f -> people
                            .entrySet()
                            .stream()
                            .anyMatch(person -> f
                                    .getCharacters()
                                    .contains(person.getValue().getId())))
                    .collect(Collectors.toMap(Film::getId, Function.identity()));

            log.info(String.format("Found Films: %d", films.size()));

            // combine retrieved data into a pack of reports
            reports = films.entrySet().stream().map(Map.Entry::getValue).flatMap((film) -> film.getCharacters()
                    .stream()
                    .map(people::get)
                    .filter(Objects::nonNull)
                    .map(person -> {

                        Planet planet = planets.get(person.getPlanetId());

                        return new Report(film.getId(),
                                film.getTitle(),
                                person.getId(),
                                person.getName(),
                                planet.getId(),
                                planet.getName());

                    })).collect(Collectors.toList());


            log.info(String.format("Created reports: %d", reports.size()));
        }
        catch (Exception ex)
        {
            log.error(ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "There was a problem with fetching the external API", ex);
        }


        try {
            saveReport(reports);
        }
        catch (Exception ex)
        {
            log.error(ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "There was a problem with contacting the Database", ex);
        }

    }

    private void saveReport(Collection<Report> reports)
    {
        reports.forEach(r -> reportRepository.save(r));
    }


    @Transactional
    public void deleteReport(ReportRequest reportRequest)
    {
        reportRepository.deleteByPlanetNameAndCharacterNameLike(reportRequest.getPlanetName(), reportRequest.getCharacterPhrase());
    }

    public List<Report> getReports(ReportRequest reportRequest)
    {
        return reportRepository.findByPlanetNameAndCharacterNameLike(reportRequest.getPlanetName(), reportRequest.getCharacterPhrase());
    }
}
