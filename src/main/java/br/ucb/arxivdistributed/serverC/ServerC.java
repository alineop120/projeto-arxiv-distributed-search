package br.ucb.arxivdistributed.serverC;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerC {

    private static final String DATA_FILE = "data/arxiv_part2.json";
    private static final JSONArray cachedData = SearchUtils.loadJsonFileCached(DATA_FILE);
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        
    }
}
