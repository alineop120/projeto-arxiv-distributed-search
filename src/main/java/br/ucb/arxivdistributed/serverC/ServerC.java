package br.ucb.arxivdistributed.serverC;

import br.ucb.arxivdistributed.util.Config;
import br.ucb.arxivdistributed.util.KMPAlgorithm;
import br.ucb.arxivdistributed.util.SearchUtils;
import org.json.JSONArray;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
    
    private static void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("[INFO] Conectado ao Servidor A.");

            String query = in.readLine();
            if (query == null || query.isBlank()) {
                out.println("[ERRO] Consulta inválida recebida.");
                return;
            }

            System.out.println("[INFO] Realizando busca por: '" + query + "'");
            String result = performSearch(query);
            out.println(result);

        } catch (IOException e) {
            System.err.println("[ERRO] Comunicação com Servidor A falhou: " + e.getMessage());
        } finally {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("[ERRO] Falha ao fechar o socket: " + e.getMessage());
            }
        }
    }

    private static String performSearch(String query) {
        StringBuilder results = new StringBuilder();
        String lowerCaseQuery = query.toLowerCase();

        try {
            for (int i = 0; i < cachedData.length(); i++) {
                var article = cachedData.getJSONObject(i);
                String title = article.optString("title", "");
                String summary = article.optString("abstract", "");

                if (KMPAlgorithm.contains(title.toLowerCase(), lowerCaseQuery) ||
                        KMPAlgorithm.contains(summary.toLowerCase(), lowerCaseQuery)) {
                    results.append("Título: ").append(title).append("\nResumo: ").append(summary).append("\n\n");
                }
            }
        } catch (Exception e) {
            System.err.println("[ERRO] Erro durante busca: " + e.getMessage());
            return "[ERRO] Falha ao acessar os dados no servidor.\n";
        }

        return results.toString();
        
    }
}
