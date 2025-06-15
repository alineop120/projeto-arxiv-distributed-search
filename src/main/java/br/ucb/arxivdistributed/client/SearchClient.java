import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


String response = responseBuilder.toString().trim();

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

        // Se o resultado tiver a chave "dado", é texto simples
        if (resultado.has("dado")) {
        System.out.println(resultado.getString("dado"));
        } else { // Senão, formata os campos esperados (título, autor, etc.)
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
