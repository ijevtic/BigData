package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiEntry {
    @JsonProperty("$schema")
    private String schema;

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("user")
    private String user;

    @JsonProperty("bot")
    private boolean bot;

    @JsonProperty("minor")
    private boolean minor;

    @JsonProperty("patrolled")
    private boolean patrolled;

    @JsonProperty("length")
    private Length length;

    @JsonProperty("revision")
    private Revision revision;

    @JsonProperty("server_url")
    private String serverUrl;

    @JsonProperty("server_name")
    private String serverName;

    @JsonProperty("wiki")
    private String wiki;

    @JsonProperty("parsed_comment")
    private ParsedComment parsedComment;

    public static WikiEntry fromJson(String json, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(json, WikiEntry.class);
    }

    // Length class
    @Data
    static class Length {
        @JsonProperty("old")
        private int old;

        @JsonProperty("new")
        private int _new;
    }

    // Revision class
    @Data
    static class Revision {
        @JsonProperty("old")
        private long old;

        @JsonProperty("new")
        private long _new;
    }

    // ParsedComment class
    @Data
    static class ParsedComment {
        @JsonProperty("span_dir_auto")
        private String spanDirAuto;

        @JsonProperty("span_class_auto_comment")
        private String spanClassAutoComment;
    }

    @Override
    public String toString() {
        return "WikiEntry{" +
                "schema='" + schema + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", bot=" + bot +
                ", minor=" + minor +
                ", patrolled=" + patrolled +
                ", length=" + length +
                ", revision=" + revision +
                ", serverUrl='" + serverUrl + '\'' +
                ", serverName='" + serverName + '\'' +
                ", wiki='" + wiki + '\'' +
                ", parsedComment=" + parsedComment +
                '}';
    }
}