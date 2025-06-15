package br.ucb.arxivdistributed.client;

import br.ucb.arxivdistributed.util.Config;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SearchClient {

    public static void main(String[] args) {
        System.out.println("🔍 Sistema de Busca Distribuído - Cliente");

        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Digite uma palavra ou trecho para buscar (ou 'sair' para encerrar): ");
            String query;

            while ((query = userInput.readLine()) != null && !query.equalsIgnoreCase("sair")) {
                if (query.isBlank()) {
                    System.out.println("⚠️ Consulta vazia! Por favor, digite um termo válido.");
                    System.out.print("\nDigite uma palavra ou trecho para buscar (ou 'sair' para encerrar): ");
                    continue;
                }
                
                processQuery(query); 
                
                System.out.print("\nDigite uma nova palavra ou trecho para buscar (ou 'sair' para encerrar): ");
            }

        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao ler a entrada do usuário: " + e.getMessage());
        }

        System.out.println("Cliente encerrado.");
    }

    private static void processQuery(String query) {
        try (Socket socket = new Socket(Config.SERVER_A_HOST, Config.SERVER_A_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("[INFO] Conectando ao servidor e enviando consulta...");
            out.println(query);

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line).append("\n");
            }
            String response = responseBuilder.toString().trim();

            System.out.println("\n✅ Busca finalizada.");
            System.out.println("------ RESULTADOS ENCONTRADOS ------");
            System.out.println(response.isEmpty() ? "⚠️ Nenhuma resposta recebida do servidor." : response);
            System.out.println("------------ FIM DA BUSCA ----------");

        } catch (UnknownHostException e) {
            System.err.println("[ERRO FATAL] Host do servidor não encontrado: " + Config.SERVER_A_HOST);
        } catch (IOException e) {
            System.err.println("[ERRO] Falha na comunicação com o servidor: " + e.getMessage());
            System.err.println("Verifique se o Servidor A está em execução.");
        }
    }
}
