package main;

import jersey.repackaged.com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.client.ClientBuilder;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Uploader {

    private void run() {
        JSONParser jsonParser = new JSONParser();

        try {
            Object object = jsonParser.parse(new FileReader(getClass().getClassLoader().getResource("json/spaces" +
                    ".json").getPath()));
            JSONArray postList = (JSONArray) object;
            List<Post> posts = new ArrayList<>();
            for (JSONObject current : (Iterable<JSONObject>) postList) {
                posts.add(new Post(
                        (String) current.get("title"),
                        (String) current.get("link"),
                        (String) current.get("comment")));
            }

            List<Post> reversedList = Lists.reverse(posts);
            reversedList.forEach(this::postOnSlack);

        } catch (Exception e) {
            log.error("Failed to process posts", e);
        }
    }

    private void postOnSlack(Post thePost) {
        String responseEntity = ClientBuilder.newClient()
                .target("https://slack.com").path("api/chat.postMessage")
                .queryParam("token", loadSlackToken())
                .queryParam("channel", "old-posts")
                .queryParam("text", thePost.toString())
                .queryParam("unfurl_links", "true")
                .queryParam("unfurl_media", "true")
                .queryParam("username", "SpacesImporter")
                .queryParam("pretty", "1")
                .request()
                .get(String.class);

        log.info(responseEntity);

    }

    private String loadSlackToken() {
        return System.getProperty("slack-token", "");
    }

    @RequiredArgsConstructor
    private class Post {
        private final String title;
        private final String link;
        private final String comment;

        @Override
        public String toString() {
            return "Original title: " + title + "\n"
                    + "Comment: " + comment + "\n"
                    + link;
        }
    }

//    public static void main(String[] args) {
//        new Uploader().run();
//    }

}
