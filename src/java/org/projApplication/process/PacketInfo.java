package org.projApplication.process;


import java.io.Serial;
import java.io.Serializable;
import java.net.InetAddress;

public class PacketInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 3275063138377064310L;
    protected TypeMessage type;
    protected InetAddress source;
    protected byte destID;
    protected String message;

    public PacketInfo(TypeMessage t, InetAddress s, byte d, String m)
     {
         type = t;
         source = s;
         destID = d;
         message = m;
     }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PacketInfo p)) return false;
        boolean t = type == p.type;
        boolean s = source.equals(p.source);
        boolean id = destID == p.destID;
        boolean m = message.equals(p.message);
        return t && s && id && m;
    }

    public boolean isBroadCast() {return type == TypeMessage.BROADCAST; }

    public InetAddress getSource() {
        return source;
    }

    public byte getDestID() {
        return destID;
    }

    public String getMessage() {
        return message;
    }
}
