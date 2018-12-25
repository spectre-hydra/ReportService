package softwareplant.Swapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.joshworks.restclient.http.mapper.ObjectMapper;
import softwareplant.Model.Swapi.Response.Response;

import java.io.IOException;


public class JsonMapper implements ObjectMapper {

    private final com.fasterxml.jackson.databind.ObjectMapper mapper;

    public JsonMapper(){
        mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public <T> T readValue(String value, Class<T> aClass) {
        try
        {
            Response<T> response = mapper.readValue(value, new TypeReference<Response<T>>() {});
            return mapper.convertValue(response, aClass);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String writeValue(Object value) {
        try
        {
            return mapper.writeValueAsString(value);
        }
        catch(JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
