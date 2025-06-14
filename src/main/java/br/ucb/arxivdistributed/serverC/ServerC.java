package br.ucb.arxivdistributed.serverC;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerC {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.out.println("Servidor C iniciado (esqueleto) na porta " + 6002);
    }
}
