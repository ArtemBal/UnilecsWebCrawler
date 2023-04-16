import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    public static class WebParser {
        private static final HashMap<String, ArrayList<String>> SampleTreeUrl = new HashMap<>(Map.of(
                "http://blog.unilecs.ru/tasks/arrays",
                new ArrayList<>(Arrays.asList("http://blog.unilecs.ru", "http://blog.unilecs.ru/tasks")),
                "http://blog.unilecs.ru",
                new ArrayList<>(Arrays.asList("http://blog.unilecs.ru/puzzles", "http://blog.supercode.ru/tasks")),
                "http://blog.supercode.ru/tasks/arrays",
                new ArrayList<>(Arrays.asList("http://blog.supercode.ru", "http://blog.supercode.ru/tasks")),
                "http://blog.supercode.ru",
                new ArrayList<>(Arrays.asList("http://blog.supercode.ru/puzzles", "http://blog.unilecs.ru/tasks/arrays"))
        ));

        public ArrayList<String> GetUrls(String startUrl) {
            if (!SampleTreeUrl.containsKey(startUrl))
                return new ArrayList<>();

            return SampleTreeUrl.get(startUrl);
        }
    }

    public static class WebCrawler
    {
        private final WebParser webParser;
        public String StartHost = "";

        public WebCrawler()
        {
            this.webParser = new WebParser();
        }

        public HashSet<String> Crawl(String startUrl) throws URISyntaxException {
            StartHost = ParseHost(startUrl);
            HashSet<String> result = new HashSet<>();

            DFS(startUrl, result);

            return result;
        }

        public void DFS(String url, HashSet<String> result) throws URISyntaxException {
            if (!Objects.equals(StartHost, ParseHost(url)) || result.contains(url)) return;

            result.add(url);
            for (String cUrl: webParser.GetUrls(url)) {
                DFS(cUrl, result);
            }
        }

        public static String ParseHost(String url) throws URISyntaxException {
            return new URI(url).getHost();
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        System.out.println("UniLecs");

        WebCrawler webCrawler = new WebCrawler();
        System.out.println(webCrawler.Crawl("http://blog.unilecs.ru/tasks/arrays").toString());
        System.out.println(webCrawler.Crawl("http://blog.supercode.ru/tasks/arrays").toString());
    }
}