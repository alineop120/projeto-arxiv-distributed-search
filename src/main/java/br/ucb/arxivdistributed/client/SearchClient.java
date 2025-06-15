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

}
