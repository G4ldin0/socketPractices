import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

// Implementação de Thread que envia mensagens ao sistema
abstract class ProcessSendMessage implements Runnable {

//    DatagramSocket socket;

//    public ProcessSendMessage() throws SocketException {socket = new DatagramSocket(Processo.socket.getLocalSocketAddress());}
    private byte[] generateBuffer(PacketInfo p) throws IOException {
        // Serializa a classe e coloca no buffer
        ByteArrayOutputStream ObjBytes =  new ByteArrayOutputStream();
        ObjectOutputStream objOutput = new ObjectOutputStream(ObjBytes);

        objOutput.writeObject(p);
        objOutput.close();

        byte[] buf = new byte[4096];
        System.arraycopy(ObjBytes.toByteArray(), 0, buf, 0, ObjBytes.size());

        return buf;
    }

    public void sendMessage(PacketInfo pInfo, InetAddress addr) throws IOException {
        // Gera buffer com objeto serializado
        byte[] buf = generateBuffer(pInfo);

        DatagramPacket packet = new DatagramPacket(
                buf,
                4096,
                addr,
                5000
        );

        Processo.socket.send(packet);
    }

    public void sendMessage(PacketInfo pInfo) throws IOException {
        byte[] buf = generateBuffer(pInfo);

        DatagramPacket packet = new DatagramPacket(
                buf,
                4096,
                getIDAddress(pInfo.destID),
                5000
        );

        Processo.socket.send(packet);
    }

    public InetAddress getIDAddress(byte id) {
        InetAddress rAddr = null;

        for(var listEntry : Processo.addresses)
        {
            // Faz comparação lógica do ID com os valores da tabela
            if( ( listEntry.l() & id ) != 0 ){
                rAddr = listEntry.r();
                break;
            }
        }
        return rAddr;
    }

}