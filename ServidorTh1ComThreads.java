package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTh1ComThreads extends Thread{

    private static ServerSocket server;
    private Socket socket;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;

    public ServidorTh1ComThreads(Socket cliente) {
        this.socket = cliente;
        try {
            in = cliente.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {

            server = new ServerSocket(1234);
            System.out.println("Servidor ativo ");

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket cliente = server.accept();

                System.out.println("Cliente conectado...");
                Thread thread = new ServidorTh1ComThreads(cliente);
                thread.start();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {

            OutputStream out = this.socket.getOutputStream();// enviar dados para o cliente (writer)
            Writer ouw = new OutputStreamWriter(out);
            BufferedWriter bfw = new BufferedWriter(ouw);

            String senha = bfr.readLine();
            String resposta;

            if(senha.equalsIgnoreCase("352")) {
                resposta = "sim";
            }else {
                resposta = "nao";
            }

            // Thread.currentThread().sleep(1000);

            //resposta = msg.equalsIgnoreCase("7as75fj345k") ? "sim" : "nao";

            //servidor envia resposta para o cliente
            bfw.write(resposta + System.lineSeparator());
            bfw.flush();


            //espera o cliente fechar a conexao ou pode colocar um timeout
            //while(this.socket.isConnected()) {
            // recomendado esperar o cliente encerrar a conexao para nao fechar o fluxo de dados
            //}

            bfw.close();
            ouw.close();
            out.close();
            this.socket.close();

        } catch (

                Exception e) {
            e.printStackTrace();

        }

    }

}
