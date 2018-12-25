package softwareplant.Model.Swapi;


import softwareplant.Utility.StringUtils;

import java.util.Objects;

public class SwapiObject {

    protected String url;
    protected Long id;

    protected SwapiObject(String url){
        this.url = url;
        this.id = StringUtils.transformId(this.url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwapiObject that = (SwapiObject) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
