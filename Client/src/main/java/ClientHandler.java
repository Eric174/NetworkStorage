import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RequestData msg = new RequestData();
        String fileName = "./Client/1.txt";
        File file = new File(fileName);
        System.out.println(file.getName() + " size: " + file.length());
        System.out.println(file.exists());
        msg.setLength(file.length());
        msg.setFileName(file.getName());
        msg.setFile(file);
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseData data = (ResponseData) msg;
        System.out.println(data.getLength());
        System.out.println(data.getFileName());
        System.out.println(data.getFile().exists());
        ctx.close();
    }
}