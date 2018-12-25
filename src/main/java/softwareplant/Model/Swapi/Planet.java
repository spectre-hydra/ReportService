package softwareplant.Model.Swapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import softwareplant.Utility.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


public class Planet extends SwapiObject{


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getResidents() {
        return residents;
    }

    public void setResidents(List<Long> residents) {
        this.residents = residents;
    }

    public List<Long> getFilms() {
        return films;
    }

    public void setFilms(List<Long> films) {
        this.films = films;
    }

    private String name;
    private List<Long> residents;
    private List<Long> films;

    @JsonCreator
    public Planet(@JsonProperty("name") String name,
                @JsonProperty("residents") List<String> residents,
                @JsonProperty("films") List<String> films,
                @JsonProperty("url") String url){
        super(url);

        this.name = name;
        this.residents = residents.stream().map(StringUtils::transformId).collect(Collectors.toList());
        this.films = films.stream().map(StringUtils::transformId).collect(Collectors.toList());
    }
}
