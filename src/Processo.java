import java.io.*;
import java.net.*;
import java.util.*;

public class Processo {
    static protected DatagramSocket socket;
    static protected byte ID;
    static protected Vector<Pair<Byte, InetAddress>> addresses;

    static protected Vector<PacketInfo> log;
    public Processo(String addr, String[] neighbours) {
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

            log = new Vector<>();



            // Executa a rotina principal
            rodar();

        } catch(SocketException | UnknownHostException e){
            throw new RuntimeException(e);
        }
    }

    protected static void log(PacketInfo msg)
    {
         log.add(msg);
    }

    private void rodar() throws SocketException {
        System.out.println("Processo " + ID + socket.getLocalAddress() + ":"+ socket.getLocalPort() + " conectado.");

        // Criação das Threads
        ProcessReceiveMessage receiveThread = new ProcessReceiveMessage();
        Thread tRec = new Thread(receiveThread); tRec.setDaemon(true);
        tRec.start();

        ProcessSendMessage sendThread = new ProcessSendMessage()
        {
        // Tipos de entrada e respectivas ações
            @Override public void run()
            {

                Scanner keyboard = new Scanner(System.in);

                while (!socket.isClosed()) {
                    try {
                        Scanner message = new Scanner(keyboard.nextLine());
                        String msg;

                        PacketInfo pInfo;


                        // Tipos de entrada e ações equivalentes
                        if (message.hasNext("/"))
                        {
                            System.out.println("Encerrando processo");
                            message.close();
                            break;
                        }
                        else if (message.hasNext("\\p{Punct}"))
                        {
                            switch (message.next()) {
                                case ".":
                                    // UNICAST
                                    byte id = message.nextByte();

                                    try { msg = message.nextLine().trim(); } catch (Exception e) {msg = " ";}

                                    pInfo = new PacketInfo(typeMessage.UNICAST,
                                            socket.getLocalAddress(),
                                            (byte) (1 << (id - 1)),
                                            msg);

                                    sendMessage(pInfo);

                                    break;
                                case "!":
                                    // BROADCAST
                                    try { msg = message.nextLine().trim(); } catch (Exception e) {msg = " ";}

                                    pInfo = new PacketInfo(typeMessage.BROADCAST,
                                            socket.getLocalAddress(),
                                            ID,
                                            msg
                                    );

                                    for(int i = 0; i < addresses.size() - 1; ++i)
                                        sendMessage(pInfo, addresses.get(i).r());

                                    break;
                                case ";":
                                    // DEBUG : LOOP PELA REDE
                                    pInfo = new PacketInfo(typeMessage.UNICAST,
                                            socket.getLocalAddress(),
                                            (byte) 255,
                                            "Debug > Loop");

                                    sendMessage(pInfo);

                                    break;
                            }
                        } else {
                            // MULTICAST
                            try { msg = message.nextLine().trim(); } catch (Exception e) {msg = " ";}

                            pInfo = new PacketInfo(typeMessage.MULTICAST,
                                    socket.getLocalAddress(),
                                    ID,
                                    msg
                            );

                            for(int i = 0; i < addresses.size() - 1; ++i)
                                sendMessage(pInfo, addresses.get(i).r());
                        }
                    } catch (IOException e) {
                        keyboard = new Scanner(System.in);
                    }

                }
                keyboard.close();
            }
        };

        Thread tSend = new Thread(sendThread);
        tSend.start();
    }
}