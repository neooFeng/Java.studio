package fengfei.studio.netty.im;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class Main {
    public static void main(String[] args) {
        NioSocketChannel socketChannel = new NioSocketChannel();
        ChannelFuture future = socketChannel.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
        future.addListener(channelFuture -> System.out.println("done"));
    }
}
