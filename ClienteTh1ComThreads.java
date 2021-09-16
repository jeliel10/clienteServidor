package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ClienteTh1ComThreads extends Thread{

    private Socket socket;
    private OutputStream ou;
    private Writer ouw;
    private BufferedWriter bfw;

    public ClienteTh1ComThreads(Socket soc) {
        this.socket = soc;
    }

    public static void main(String[] args) {

        ClienteTh1ComThreads clienteTh = null;
        for(int x=0; x < 1000;x++) {
            try {
                clienteTh = new ClienteTh1ComThreads(new Socket("localhost", 1234));
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            clienteTh.run();
        }



    }

    @Override
    public void run() {
        this.conectar();

        String num = String.valueOf(new Random().nextInt(999));
        this.enviarMensagem(num);

        this.receberResposta();

        this.sair();
    }

    public void conectar()  {

        try {

            ou = socket.getOutputStream();
            ouw = new OutputStreamWriter(ou);
            bfw = new BufferedWriter(ouw);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void enviarMensagem(String msg)  {

        try {
            //escrever dados no servidor
            bfw.write(msg + System.lineSeparator());
            bfw.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println("\n Mensagem enviada");

    }

    public void receberResposta()  {

        InputStream in;
        try {
            in = socket.getInputStream();

            InputStreamReader inr = new InputStreamReader(in);
            //objeto para leitura e escreve os dados no cliente
            BufferedReader bfr = new BufferedReader(inr);
            String msg = "";

            while (!"Sair".equalsIgnoreCase(msg)) {

                if (bfr.ready()) {

                    msg = bfr.readLine();

                    if(msg.equalsIgnoreCase("sim")){
                        System.out.println(msg);
                    }
                    msg = "Sair";
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sair()  {

        try {
            bfw.close();
            ouw.close();
            ou.close();
            socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
