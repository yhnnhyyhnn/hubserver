package proto.coder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.PBMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SimpleProtobufEncoder extends MessageToByteEncoder<PBMessage> {

	Logger logger = LoggerFactory.getLogger(SimpleProtobufEncoder.class);
	
//	@Override
//	protected void encode(ChannelHandlerContext ctx, PBMessage msg, List<Object> out) throws Exception {
//		logger.info("开始进行protobuf编码...");
//		byte[] content = msg.getMessage() != null ? msg.getMessage().toByteArray() : msg.getBytes();
//		int size = PBMessage.HDR_SIZE;
//		if(content!=null){
//			size += content.length;
//		}
//		ByteBuf buffer = ctx.alloc().buffer(size);
//		buffer.writeShort((short)size);
//		buffer.writeShort((short)msg.getCode().getNumber());
//		buffer.writeInt(0);
//		if(content != null){
//			buffer.writeBytes(content);
//		}
//		
//		out.add(buffer);
//	}

	@Override
	protected void encode(ChannelHandlerContext ctx, PBMessage msg, ByteBuf out) throws Exception {
		logger.info("开始进行protobuf编码...");
		byte[] content = msg.getMessage() != null ? msg.getMessage().toByteArray() : msg.getBytes();
		int size = PBMessage.HDR_SIZE;
		if(content!=null){
			size += content.length;
		}
		ByteBuf buffer = ctx.alloc().buffer(size);
		buffer.writeShort((short)size);
		buffer.writeShort((short)msg.getCode().getNumber());
		buffer.writeInt(0);
		if(content != null){
			buffer.writeBytes(content);
		}
		
		out.writeBytes(buffer);
		
	}


}
