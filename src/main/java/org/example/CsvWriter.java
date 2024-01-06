package org.example;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.util.List;

public class CsvWriter {

    static String[] header = {
            "$schema",
            "type",
            "title",
            "comment",
            "timestamp",
            "user",
            "bot",
            "minor",
            "patrolled",
            "length_old",
            "length_new",
            "revision_old",
            "revision_new",
            "server_url",
            "server_name",
            "wiki",
//            "parsed_comment_span_dir_auto",
//            "parsed_comment_span_class_auto_comment",
    };

//    public static void main(String[] args) {
//
//
//        // Sample WikiEntry
//        WikiEntry wikiEntry = createSampleWikiEntry();
//
//        // Write WikiEntry to CSV
//        writeWikiEntryToCsv(wikiEntry);
//    }

    public static String[] getDataFromWikiEntry(WikiEntry wikiEntry) {
        try {
            String[] data = {
                    wikiEntry.getSchema(),
                    wikiEntry.getType(),
                    wikiEntry.getTitle(),
                    wikiEntry.getComment(),
                    String.valueOf(wikiEntry.getTimestamp()),
                    wikiEntry.getUser(),
                    String.valueOf(wikiEntry.isBot()),
                    String.valueOf(wikiEntry.isMinor()),
                    String.valueOf(wikiEntry.isPatrolled()),
                    String.valueOf(wikiEntry.getLength().getOld()),
                    String.valueOf(wikiEntry.getLength().get_new()),
                    String.valueOf(wikiEntry.getRevision().getOld()),
                    String.valueOf(wikiEntry.getRevision().get_new()),
                    wikiEntry.getServerUrl(),
                    wikiEntry.getServerName(),
                    wikiEntry.getWiki(),
//                    wikiEntry.getParsedComment().getSpanDirAuto(),
//                    wikiEntry.getParsedComment().getSpanClassAutoComment()
            };
            return data;
        } catch (Exception e) {
            System.out.println(wikiEntry);
        }
        return null;

    }
}