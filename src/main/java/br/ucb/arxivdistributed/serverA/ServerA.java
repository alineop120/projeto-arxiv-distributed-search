import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

private static void handleClient(Socket clientSocket) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

        System.out.println("[INFO] Cliente conectado: " + clientSocket.getInetAddress());

        String query = in.readLine();
        if (query == null || query.isBlank()) {
            System.out.println("[WARN] Consulta vazia ou conexão fechada pelo cliente.");
            out.println(new JSONObject().put("erro", "Consulta inválida.").toString());
            return;
        }

        System.out.println("[INFO] Recebida consulta: '" + query + "'");

        Future<String> futureB = searchExecutor.submit(() -> consultaServidor(Config.SERVER_B_HOST, Config.SERVER_B_PORT, query));
        Future<String> futureC = searchExecutor.submit(() -> consultaServidor(Config.SERVER_C_HOST, Config.SERVER_C_PORT, query));

        String resultadoB = getResultFromFuture(futureB, "B");
        String resultadoC = getResultFromFuture(futureC, "C");

        JSONObject respostaJson = new JSONObject();
        JSONArray resultadosArray = new JSONArray();

        // Processa o resultado do Servidor B
        if (resultadoB != null && !resultadoB.trim().isEmpty() && !resultadoB.startsWith("[ERRO]")) {
            try {
                // Tenta interpretar como um objeto JSON
                resultadosArray.put(new JSONObject(resultadoB));
            } catch (JSONException e) {
                // Se falhar, trata como texto simples e o encapsula em um JSON
                resultadosArray.put(new JSONObject().put("dado", resultadoB.trim()));
            }
        }

        // Processa o resultado do Servidor C
        if (resultadoC != null && !resultadoC.trim().isEmpty() && !resultadoC.startsWith("[ERRO]")) {
            try {
                // Tenta interpretar como um objeto JSON
                resultadosArray.put(new JSONObject(resultadoC));
            } catch (JSONException e) {
                // Se falhar, trata como texto simples e o encapsula em um JSON
                resultadosArray.put(new JSONObject().put("dado", resultadoC.trim()));
            }
        }

        respostaJson.put("resultados", resultadosArray);
        respostaJson.put("total", resultadosArray.length());

        if (resultadosArray.isEmpty()) {
            respostaJson.put("mensagem", "⚠️ Nenhum resultado encontrado para a sua busca.");
        }

        out.println(respostaJson.toString());

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
