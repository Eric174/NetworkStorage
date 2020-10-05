import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class ResponseDataDecoder extends ReplayingDecoder<ResponseData> {
    private final int BYTE_ARRAY_SIZE = 8192;
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ResponseData data = new ResponseData();
        data.setLength(in.readLong());
        int strLen = in.readInt();
        data.setFileName(in.readCharSequence(strLen, charset).toString() + "_copy");
        //String fileNameWithOutExt = data.getFileName().replaceFirst("[.][^.]+$", "");
        File file = new File(data.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
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