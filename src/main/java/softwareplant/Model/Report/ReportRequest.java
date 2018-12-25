package softwareplant.Model.Report;


import javax.validation.constraints.NotBlank;

public class ReportRequest {

    @NotBlank(message = "characterPhrase must not be empty")
    private String characterPhrase;

    @NotBlank(message = "planetName must not be empty")
    private String planetName;

    public ReportRequest(@NotBlank(message = "characterPhrase must not be empty") String characterPhrase, @NotBlank(message = "planetName must not be empty") String planetName) {
        this.characterPhrase = characterPhrase;
        this.planetName = planetName;
    }

    public ReportRequest(){}

    @Override
    public String toString() {
        return "ReportRequest{" +
                "characterPhrase='" + characterPhrase + '\'' +
                ", planetName='" + planetName + '\'' +
                '}';
    }

    public String getCharacterPhrase() {
        return characterPhrase;
    }

    public void setCharacterPhrase(String characterPhrase) {
        this.characterPhrase = characterPhrase;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }




}
