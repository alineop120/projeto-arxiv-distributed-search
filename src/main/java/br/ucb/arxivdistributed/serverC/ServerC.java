package br.ucb.arxivdistributed.serverC;

import br.ucb.arxivdistributed.util.Config;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerC {

    private static final String DATA_FILE = "data/arxiv_part2.json";
    private static final JSONArray cachedData = SearchUtils.loadJsonFileCached(DATA_FILE);
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_C_PORT)) {
            System.out.println("Servidor C aguardando na porta " + Config.SERVER_C_PORT + "...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(() -> handleRequest(socket));
                } catch (IOException e) {
                    if (!executor.isShutdown()) {
                        System.err.println("[ERRO] Falha ao aceitar conexão: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[ERRO FATAL] Falha ao iniciar Servidor C: " + e.getMessage());
        } finally {
            System.out.println("Encerrando Servidor C...");
            executor.shutdown();
        }
    }
        
    }
}
