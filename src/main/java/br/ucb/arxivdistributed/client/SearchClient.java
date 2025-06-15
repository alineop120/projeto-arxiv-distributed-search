package br.ucb.arxivdistributed.client;

import br.ucb.arxivdistributed.util.Config; // Adicionado para referência futura
import java.io.*;
import java.net.Socket;
import org.json.JSONObject; // Mantido temporariamente
import org.json.JSONArray;
import org.json.JSONException;

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
            // Tratamento de erro será melhorado
        }

        System.out.println("Cliente encerrado.");
    }

    private static void processQuery(String query) {
        // A lógica de conexão e resposta será inserida aqui.
        // Por enquanto, usamos a lógica de parse antiga como placeholder.
        
        // Simulação da resposta do servidor para manter a lógica de parse
        String response = ""; // responseBuilder.toString().trim();

        System.out.println("\n✅ Busca finalizada.");
        System.out.println("------ RESULTADOS ENCONTRADOS ------");

        if (response.isEmpty()) {
            System.out.println("⚠️ Nenhuma resposta recebida do servidor.");
        } else {
            try {
                JSONObject respostaJson = new JSONObject(response);

                if (respostaJson.has("erro")) {
                    System.out.println("❌ Erro: " + respostaJson.getString("erro"));
                } else if (respostaJson.has("mensagem")) {
                    System.out.println(respostaJson.getString("mensagem"));
                }

                if (respostaJson.has("resultados")) {
                    JSONArray resultados = respostaJson.getJSONArray("resultados");
                    System.out.println("Total de resultados: " + resultados.length());

                    for (int i = 0; i < resultados.length(); i++) {
                        JSONObject resultado = resultados.getJSONObject(i);
                        System.out.println("\n--- Artigo " + (i + 1) + " ---");

                        if (resultado.has("dado")) {
                            System.out.println(resultado.getString("dado"));
                        } else { 
                            String titulo = resultado.optString("title", "Título não disponível");
                            String introducao = resultado.optString("abstract", "Resumo não disponível");
                            System.out.println("Título: " + titulo);
                            System.out.println("Resumo: " + introducao.substring(0, Math.min(introducao.length(), 150)) + "...");
                        }
                    }
                }
            } catch (JSONException e) {
                System.out.println("⚠️ Resposta do servidor não está no formato JSON esperado. Exibindo resposta bruta:");
                System.out.println(response);
            }
        }
        System.out.println("------------ FIM DA BUSCA ----------");
    }
}