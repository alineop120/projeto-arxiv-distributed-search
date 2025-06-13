package br.ucb.arxivdistributed.util;

public class KMPAlgorithm {

    public static boolean contains(String txt, String pat) {
        // TODO: Implementar a lógica de busca usando o array LPS
        return false;
    }

    private static int[] computeLPSArray(String pat) {
        int M = pat.length();
        int[] lps = new int[M];
        int len = 0; 
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