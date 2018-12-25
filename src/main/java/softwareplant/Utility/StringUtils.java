package softwareplant.Utility;

public class StringUtils {

    public static Long transformId(final String url)
    {
        final String[] parts = url.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }
}
