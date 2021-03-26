package com.ffs.netty.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * @author fengfasong
 * @date 2021/3/5
 */
public class TimeClient {

    public void connect(int port,String host){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        try {
            if(args != null && args.length > 0){
                port = Integer.valueOf(args[0]);
            }
        }catch (Exception e){

        }
        new TimeClient().connect(port,"127.0.0.1");
    }
}
