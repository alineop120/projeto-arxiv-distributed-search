package br.ucb.arxivdistributed.util;

/**
 * Implementação do algoritmo Knuth-Morris-Pratt (KMP) para busca eficiente de padrão em texto.
 */
public class KMPAlgorithm {

    /**
     * Verifica se o padrão `pat` ocorre dentro do texto `txt`.
     * Retorna true se encontrado, false caso contrário.
     */
    public static boolean contains(String txt, String pat) {
        int M = pat.length();
        int N = txt.length();

        int[] lps = computeLPSArray(pat);

        int i = 0; // índice do texto
        int j = 0; // índice do padrão

        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                i++;
                j++;
            }

            if (j == M) {
                return true; // padrão encontrado
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false; // padrão não encontrado
    }

    /**
     * Calcula o array LPS (Longest Prefix Suffix) usado pelo algoritmo KMP.
     */
    private static int[] computeLPSArray(String pat) {
        int M = pat.length();
        int[] lps = new int[M];
        int len = 0; // comprimento do prefixo mais longo que é também sufixo

        lps[0] = 0;
        int i = 1;

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}