package com.piqu.handler;

import com.util.MessageUtil;
import com.util.PBMessage;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import proto.CmdConstantsProto.CmdCode;

public class HeartbearHandler extends ChannelDuplexHandler {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                	PBMessage msg = MessageUtil.buildMessage(CmdCode.SERVER_HEART, null);
                    ctx.writeAndFlush(msg);
                    System.out.println("send ping to server----------");
                    break;
                default:
                    break;
            }
        }
	}

}
