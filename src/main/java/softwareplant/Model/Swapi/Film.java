package softwareplant.Model.Swapi;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import softwareplant.Utility.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Film extends SwapiObject{

    private String title;
    private List<Long> characters;
    private List<Long> planets;

    @JsonCreator
    public Film(@JsonProperty("title") String title, @JsonProperty("characters") List<String> characters, @JsonProperty("planets") List<String> planets, @JsonProperty("url") String url){
        super(url);
        this.title = title;
        this.characters = characters.stream().map(StringUtils::transformId).collect(Collectors.toList());
        this.planets = planets.stream().map(StringUtils::transformId).collect(Collectors.toList());
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<Long> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Long> characters) {
        this.characters = characters;
    }


    public List<Long> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Long> planets) {
        this.planets = planets;
    }
}
