import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<RequestData> {
    private final int BYTE_ARRAY_SIZE = 8192;
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //System.out.println(in.readableBytes());
        RequestData data = new RequestData();
        data.setLength(in.readLong());
        int strLen = in.readInt();
        data.setFileName(in.readCharSequence(strLen, charset).toString());
        File file = new File(data.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        //System.out.println(data.getFileName() + " " + data.getLength());
        //System.out.println(in.readableBytes());
        byte[] bytes = new byte[BYTE_ARRAY_SIZE];
        long cycleCount = data.getLength() / BYTE_ARRAY_SIZE;
        int countBytesToRead = BYTE_ARRAY_SIZE;
        for (long i = 0; i <= cycleCount; i++) {
            if (i == cycleCount) {
                countBytesToRead = (int)(data.getLength() % BYTE_ARRAY_SIZE);
            }
            in.readBytes(bytes,0, countBytesToRead);
            fos.write(bytes,0, countBytesToRead);
        }
        fos.close();
        data.setFile(file);
        out.add(data);
    }
}