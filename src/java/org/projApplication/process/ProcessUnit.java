package org.projApplication.process;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.*;
import java.util.*;

public class ProcessUnit {
    static protected DatagramSocket socket;
    static protected byte ID;
    static protected Vector<Pair<Byte, InetAddress>> addresses;
    static protected ObservableList<PacketInfo> log;

    static PipedInputStream input;
    static PipedOutputStream output;


    static protected ObjectInputStream getUserInput;
    static protected ObjectOutputStream userInput;


    public ProcessUnit(String addr, String... neighbours) {
        try {

            // Criação do socket
            InetAddress myAddr = InetAddress.getByName(addr);
            socket = new DatagramSocket(new InetSocketAddress(myAddr, 5000));
            socket.setReceiveBufferSize(4096);

            ID = (byte)( 1 << ( myAddr.getAddress()[3] - 1 ));

            // Criação da tabela de endereços e popula com os endereços
            addresses = new Vector<>();
            for (String s : neighbours) {
                InetAddress n = InetAddress.getByName(s);
                byte _id = (byte)( 1 << ( n.getAddress()[3] - 1 ));
                addresses.add(new Pair<>(_id, n));
            }
            // Qualquer outro endereço será enviado para o primeiro endereço assinalado
            addresses.add(new Pair<>((byte)255, addresses.get(0).r()));



            log = FXCollections.observableArrayList();



            input = new PipedInputStream();
            output = new PipedOutputStream(input);



            userInput = new ObjectOutputStream(output);


            // Executa a rotina principal
            rodar();

        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    public static void end() {
        socket.close();
    }

    public static byte getID() { return ID; }
    public static Vector<Pair<Byte, InetAddress>> getAddresses() { return addresses; }
    public static InetAddress getAddress() { return socket.getLocalAddress();}

    protected static void log(PacketInfo msg) {
        Platform.runLater(() -> log.add(msg) );
    }

    public static ObservableList<PacketInfo> log() { return log; }

    public static void sendMessage(PacketInfo pInfo){
        try {
            userInput.writeObject(pInfo);
            userInput.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.add(pInfo);
    }

    private void rodar() throws SocketException {
        System.out.println("org " + ID + socket.getLocalAddress() + ":"+ socket.getLocalPort() + " conectado.");

        // Criação das Threads
        ProcessReceiveMessage receiveThread = new ProcessReceiveMessage();
        Thread tRec = new Thread(receiveThread); tRec.setDaemon(true);
        tRec.start();

        ProcessSendMessage sendThread = new ProcessSendMessage(){
            // Tipos de entrada e respectivas ações
            @Override public void run()
            {

                try {

                    getUserInput = new ObjectInputStream(input);

                    while (!socket.isClosed()) {

                        PacketInfo userMessage = (PacketInfo) getUserInput.readObject();

                        System.out.println(userMessage.message);

                        // Caso envie BroadCast
                        if(userMessage.isBroadCast())
                            for(int i = 0; i < addresses.size()-1; i++)
                                try {
                                    sendMessage(userMessage, addresses.get(i).r());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                        // Caso for mensagem de debug
                        else if (userMessage.message.equals(";"))
                            sendMessage(new PacketInfo(
                                    TypeMessage.UNICAST,
                                    socket.getLocalAddress(),
                                    (byte)16,
                                    userMessage.getMessage()));

                        // Caso for UniCast
                        else
                            sendMessage(userMessage);

                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    // Talvez não seja socket fechado
                    System.out.println("socket fechado.");
                }
            }
        };

        Thread tSend = new Thread(sendThread);
        tSend.start();
    }
}