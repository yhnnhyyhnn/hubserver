package com.piqu.handler;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.MessageUtil;
import com.util.PBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import proto.CmdConstantsProto.CmdCode;

public class AbstractProxyHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(AbstractProxyHandler.class);

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "REGISTERED"));
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "UNREGISTERED"));
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "ACTIVE"));
		
		ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);

		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "INACTIVE"));
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info(format(ctx, "RECEIVED", msg));
		
		

		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "READCOMPLETE"));
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		logger.info(format(ctx, "WRITABILITYCHANGED"));
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(format(ctx, "USER_EVENT", cause), cause);
		super.exceptionCaught(ctx, cause);
	}

	private String format(ChannelHandlerContext ctx, String eventName) {
		String chStr = ctx.channel().toString();
		String readd = "";
		if (ctx.channel().remoteAddress() != null) {
			readd = ctx.channel().remoteAddress().toString();
		}
		return new StringBuilder(chStr.length() + 1 + eventName.length() + 1 + readd.length()).append(chStr).append(' ')
				.append(eventName).append(' ').append(readd).toString();
	}

	private String format(ChannelHandlerContext ctx, String eventName, Object arg) {
		if (arg instanceof ByteBuf) {
			return formatByteBuf(ctx, eventName, (ByteBuf) arg);
		} else if (arg instanceof ByteBufHolder) {
			return formatByteBufHolder(ctx, eventName, (ByteBufHolder) arg);
		} else {
			return formatSimple(ctx, eventName, arg);
		}
	}

	private static String formatByteBuf(ChannelHandlerContext ctx, String eventName, ByteBuf msg) {
		String chStr = ctx.channel().toString();
		int length = msg.readableBytes();
		if (length == 0) {
			StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 4);
			buf.append(chStr).append(' ').append(eventName).append(": 0B");
			return buf.toString();
		} else {
			int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
			StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + 10 + 1 + 2 + rows * 80);

			buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B').append(NEWLINE);
			appendPrettyHexDump(buf, msg);

			return buf.toString();
		}
	}

	private static String formatByteBufHolder(ChannelHandlerContext ctx, String eventName, ByteBufHolder msg) {
		String chStr = ctx.channel().toString();
		String msgStr = msg.toString();
		ByteBuf content = msg.content();
		int length = content.readableBytes();
		if (length == 0) {
			StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 4);
			buf.append(chStr).append(' ').append(eventName).append(", ").append(msgStr).append(", 0B");
			return buf.toString();
		} else {
			int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
			StringBuilder buf = new StringBuilder(
					chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 2 + 10 + 1 + 2 + rows * 80);

			buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).append(", ").append(length)
					.append('B').append(NEWLINE);
			appendPrettyHexDump(buf, content);

			return buf.toString();
		}
	}

	private static String formatSimple(ChannelHandlerContext ctx, String eventName, Object msg) {
		String chStr = ctx.channel().toString();
		String msgStr = String.valueOf(msg);
		StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length());
		return buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).toString();
	}

	private class HeartBeatTask implements Runnable {

		private ChannelHandlerContext ctx;

		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			PBMessage pbMessage = buildHeartbeart();
			System.out.println("发送心跳包");
			ctx.writeAndFlush(pbMessage);

		}

		private PBMessage buildHeartbeart() {
			PBMessage msg = MessageUtil.buildMessage(CmdCode.SERVER_HEART, null);
			return msg;
		}

	}

}
