package com.piqu.server;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piqu.handler.AbstractProxyHandler;
import com.piqu.handler.HeartbearHandler;
import com.util.MessageUtil;
import com.util.PBMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import proto.CmdConstantsProto.CmdCode;
import proto.ServerRegReqProto.ServerRegReq;
import proto.coder.SimpleProtobufEncoder;

public class MainServer {
	
	private static Logger logger = LoggerFactory.getLogger(MainServer.class);

	public static void main(String[] args) {
		if(args.length == 1) {
			logger.error("no found remoteport ...");
			return;
		}
		if(args.length == 0) {
			logger.error("no found remotehost ...");
			return;
		}
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		
		
		try{
			Bootstrap b = new Bootstrap();
			b.group(workerGroup).channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
							new SimpleProtobufEncoder(),
							new AbstractProxyHandler());
					
				}
			});
			
			ChannelFuture future = b.connect(args[0], NumberUtils.toInt(args[1])).sync();
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()){
						Channel outboundChannel = future.channel();
						Properties p = new Properties();
						try (InputStream input = new java.io.FileInputStream(new File("/etc/cdnserver.properties"))) {
							p.load(input);
						}
						p.forEach((k,v)->{
							logger.info("k={},v={}",k,v);
							String[] address = String.valueOf(k).split("_");
							String[] split = String.valueOf(v).split("_");
							ServerRegReq.Builder req = ServerRegReq.newBuilder();
							req.setServerid(NumberUtils.toInt(split[0]));
							req.setServerType(NumberUtils.toInt(split[1]));// 1.登录服务器；2.游戏服务器；3.代理登录服务器；4.代理游戏服务器
							req.setGameId(NumberUtils.toInt(split.length==3?split[2]:null));
							req.setClicentName("代理服务器");
							req.setClicentIp(address[0]);
							req.setClicentprot(NumberUtils.toInt(address[1]));
							req.setAllowSaveScoreInGame(true);
							req.setAllowTakeScoreInGame(true);
							PBMessage regMessage = MessageUtil.buildMessage(CmdCode.SERVER_REG, req.build());
							outboundChannel.writeAndFlush(regMessage);
							logger.info("发送注册，host:{},port:{},serverid:{},type:{}", address[0], address[1],split[0],split[1]);
						});
						
					}
					
				}
			});
			
			future.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
		}

	}

}
