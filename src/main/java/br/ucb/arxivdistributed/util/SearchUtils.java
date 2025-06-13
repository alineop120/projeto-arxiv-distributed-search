package br.ucb.arxivdistributed.util;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilitário para busca e carregamento de arquivos JSON a partir do sistema de arquivos.
 */
public class SearchUtils {

    // Cache estático em memória para arquivos JSON já carregados.
    private static final Map<String, JSONArray> cache = new ConcurrentHashMap<>();

    /**
     * Carrega e mantém na memória o conteúdo JSON de um arquivo.
     * Na primeira chamada, o conteúdo é lido do disco; nas demais, do cache.
     * Este método lê o arquivo a partir do caminho relativo ao diretório de execução.
     *
     * @param filePath Caminho do arquivo JSON (ex: "data/arxiv_part1.json").
     * @return JSONArray carregado.
     */
    public static JSONArray loadJsonFileCached(String filePath) {
        return cache.computeIfAbsent(filePath, path -> {
            System.out.println("[INFO] Carregando arquivo de dados do sistema de arquivos: " + path);
            try (InputStream is = new FileInputStream(path)) { // <-- AQUI ESTÁ A LINHA CHAVE
                return new JSONArray(new JSONTokener(is));
            } catch (Exception e) {
                System.err.println("[ERRO FATAL] Falha ao carregar o arquivo JSON '" + path + "': " + e.getMessage());
                System.err.println("[VERIFIQUE] A pasta 'data' está na raiz do seu projeto?");
                // Retorna um JSONArray vazio como fallback seguro para evitar NullPointerExceptions.
                return new JSONArray();
            }
        });
    }
}