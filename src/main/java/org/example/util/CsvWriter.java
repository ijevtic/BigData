package org.example.util;

import org.example.types.Earnings;
import org.example.types.StockData;
import org.example.types.WikiEntry;

public class CsvWriter {

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

    public static String[] getDataFromEarnings(Earnings earnings) {
        try {
            String[] data = {
                    earnings.getDate(),
                    earnings.getEps().toString(),
            };
            return data;
        } catch (Exception e) {
            System.out.println(earnings);
        }
        return null;

    }

    public static String[] getDataFromStockData(StockData stockData) {
        try {
            String[] data = {
                    stockData.getOpen(),
                    stockData.getHigh(),
                    stockData.getLow(),
                    stockData.getClose(),
                    stockData.getVolume(),
                    stockData.getDate(),
            };
            return data;
        } catch (Exception e) {
            System.out.println(stockData);
        }
        return null;

    }
}