package softwareplant.Model.Swapi;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import softwareplant.Utility.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Person extends SwapiObject{

    private String name;
    private List<Long> films;
    private Long planetId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getFilms() {
        return films;
    }

    public void setFilms(List<Long> films) {
        this.films = films;
    }

    @JsonCreator
    public Person(@JsonProperty("name") String name,
                  @JsonProperty("films") List<String> films,
                  @JsonProperty("homeworld") String homeworld,
                  @JsonProperty("url") String url){
        super(url);

        this.name = name;
        this.planetId = StringUtils.transformId(homeworld);
        this.films = films.stream().map(StringUtils::transformId).collect(Collectors.toList());
    }


    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }
}
