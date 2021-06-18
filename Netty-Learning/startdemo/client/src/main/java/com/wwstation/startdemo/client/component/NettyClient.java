package com.wwstation.startdemo.client.component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author william
 * @description
 * @Date: 2021-05-14 12:28
 */
@Slf4j
@Component
public class NettyClient {
    @PostConstruct
    public void initClient() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
            log.info("客户端已经启动!");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000)
                .sync();//当且仅当连接成功后才继续;
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("优雅的停止了客户端！");
        }
    }
}
