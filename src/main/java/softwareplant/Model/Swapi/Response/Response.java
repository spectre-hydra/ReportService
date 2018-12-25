package softwareplant.Model.Swapi.Response;
import org.springframework.util.StringUtils;

public class Response<T> {

    private int count;
    private String next;
    private String previous;
    private T[] results;

    public Response(){}


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public T[] getResults() {
        return results;
    }

    public void setResults(T[] results) {
        this.results = results;
    }

    public boolean hasNextPage()
    {
        return !StringUtils.isEmpty(next);
    }

}
