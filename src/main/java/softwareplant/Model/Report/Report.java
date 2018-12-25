package softwareplant.Model.Report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Report {

    public Report(){}

    public Report(Long filmId, String filmName, Long characterId, String characterName, Long planetId, String planetName) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.characterId = characterId;
        this.characterName = characterName;
        this.planetId = planetId;
        this.planetName = planetName;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "film_id")
    private Long filmId;
    @Column(name = "film_name")
    private String filmName;

    @Column(name = "character_id")
    private Long characterId;
    @Column(name = "character_name")
    private String characterName;

    @Column(name = "planet_id")
    private Long planetId;
    @Column(name = "planet_name")
    private String planetName;


    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", characterId=" + characterId +
                ", characterName='" + characterName + '\'' +
                ", planetId=" + planetId +
                ", planetName='" + planetName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }






}
