import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

// Implementação de Thread que recebe mensagens do sistema
public class ProcessReceiveMessage implements Runnable {
    final private DatagramPacket packet;
    public ProcessReceiveMessage() {
        packet = new DatagramPacket(new byte[4096], 4096);
    }

    @Override
    public void run()
    {
        while (!Processo.socket.isClosed())
        {
            try {
                // Recebe pacote
                Processo.socket.receive(packet);

                // Deserializa objeto no buffer
                ObjectInputStream objInput = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                PacketInfo received = (PacketInfo) objInput.readObject();

                // Procedimento conforme o tipo de mensagem recebida
                switch (received.type) {
                    case UNICAST -> unicastMessage(received);
                    case MULTICAST -> multicastMessage(received);
                    case BROADCAST -> broadcastMessage(received);
                }

            }
            catch (SocketException ignored) { }
            catch (IOException e) { e.printStackTrace(); }
            catch (ClassNotFoundException e) { throw new RuntimeException(e); }
        }
    }

    private void broadcastMessage(PacketInfo received) throws SocketException {


    new Thread(
            new ProcessSendMessage() {
                @Override
                public void run() {

                    try {

                        // Confere se a mensagem já foi recebida anteriormente
                        int count = 3;
                        boolean newMsg = true;
                        var i = Processo.log.listIterator(Processo.log.size());
                        while (i.hasPrevious() && count-- >= 0 && newMsg) {
                            PacketInfo compare = i.previous();
                            newMsg = newMsg && !compare.equals(received);
                        }

                        // Se não foi recebida, então passa o broadcast adiante
                        if(newMsg) {
                            Processo.log(received);
                            InetAddress source = packet.getAddress();

                            // Envia mensagem para todos, exceto para onde foi recebida a mensagem
                            for (var iterate = Processo.addresses.listIterator(Processo.addresses.size() - 1); iterate.hasPrevious(); ) {
                                var target = iterate.previous().r();
                                if (!source.equals(target)) sendMessage(received, target);
                            }

                            System.out.println(received.source + ":" + packet.getPort() + " !> " + received.message);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            }


    private void multicastMessage(PacketInfo received) {
        Processo.log(received);

        System.out.println(packet.getAddress() + ":" + packet.getPort() + " $> "  + received.message);
    }

    private void unicastMessage(PacketInfo received) throws SocketException {
        Processo.log(received);

        // Se for um loop
        if(received.source.equals( Processo.socket.getLocalAddress()) )
            System.out.println(received.message);

        // Se for uma mensagem enviada para mim
        else if(received.destID == Processo.ID)
            System.out.println(received.source + ":" + packet.getPort() + " .> " + received.message);

        // Se for uma mensagem para outro endereço
        // Cria uma nova Thread para enviar a mensagem adiante
        else
        {

            ProcessSendMessage send = new ProcessSendMessage() {
                @Override
                public void run() {
                    try { sendMessage(received); }
                    catch (IOException e) { throw new RuntimeException(e); }
                }
            };
            new Thread(send).start();
            System.out.println("Forward " + send.getIDAddress(received.destID));
        }
    }

}
