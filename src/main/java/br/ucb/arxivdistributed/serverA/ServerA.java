package br.ucb.arxivdistributed.serverA;

import br.ucb.arxivdistributed.util.Config;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerA {

    private static final ExecutorService clientExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService searchExecutor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_A_PORT)) {
            System.out.println("Servidor A aguardando conexão na porta " + Config.SERVER_A_PORT + "...");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
            
                    clientExecutor.submit(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    System.err.println("[ERRO] Falha ao aceitar conexão do cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[ERRO FATAL] Falha ao iniciar o Servidor A: " + e.getMessage());
        } finally {
            System.out.println("Encerrando Servidor A...");
            clientExecutor.shutdown();
            searchExecutor.shutdown();
        } 
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("[INFO] Cliente conectado: " + clientSocket.getInetAddress());

            String query = in.readLine();
            if (query == null || query.isBlank()) {
                System.out.println("[WARN] Consulta vazia ou conexão fechada pelo cliente.");
                out.println("[ERRO] Consulta inválida.");
                return;
            }

            System.out.println("[INFO] Recebida consulta: '" + query + "'");

            Future<String> futureB = searchExecutor.submit(
                () -> consultaServidor(Config.SERVER_B_HOST, Config.SERVER_B_PORT, query)
            );
            Future<String> futureC = searchExecutor.submit(
                () -> consultaServidor(Config.SERVER_C_HOST, Config.SERVER_C_PORT, query)
            );

            String resultadoB = getResultFromFuture(futureB, "B");
            String resultadoC = getResultFromFuture(futureC, "C");

            String respostaFinal = (resultadoB.trim() + "\n" + resultadoC.trim()).trim();
            if (respostaFinal.isBlank()) {
                respostaFinal = "⚠️ Nenhum resultado encontrado para a sua busca.";
            }

            out.println(respostaFinal);

        } catch (IOException e) {
            System.err.println("[ERRO] Comunicação com cliente falhou: " + e.getMessage());
        } finally {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("[ERRO] Falha ao fechar o socket do cliente: " + e.getMessage());
            }
        }
    }

    private static String getResultFromFuture(Future<String> future, String serverName) {
        try {
            return future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("[ERRO] Consulta ao servidor " + serverName + " falhou: " + e.getMessage());
            return "[ERRO] Falha ao consultar servidor " + serverName + ".\n";
        }
    }

    
}
