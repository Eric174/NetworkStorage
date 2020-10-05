import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
        out.writeLong(msg.getLength());
        out.writeInt(msg.getFileName().length());
        out.writeCharSequence(msg.getFileName(), charset);
        File file = msg.getFile();
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[8192];
        int i = 0;
        System.out.println(out.readableBytes());
        while (fis.available() > 0) {
            int size = fis.read(bytes);
            out.writeBytes(bytes, 0 , size);
            System.out.println(out.readableBytes());
            System.out.println("Send part "+ (++i));
        }
        fis.close();
    }
}