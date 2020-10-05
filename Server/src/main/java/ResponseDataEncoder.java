import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        out.writeLong(msg.getLength());
        out.writeInt(msg.getFileName().length());
        out.writeCharSequence(msg.getFileName(), charset);
        File file = msg.getFile();
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[8192];
        while (fis.available() > 0) {
            int size = fis.read(bytes);
            out.writeBytes(bytes, 0 , size);
        }
        fis.close();
    }
}