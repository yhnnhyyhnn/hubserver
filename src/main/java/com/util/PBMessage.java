package com.util;

import java.io.Serializable;

import com.google.protobuf.MessageLite;

import proto.CmdConstantsProto;

/**
 * 服务器和客户端,服务器和服务器直接数据传输的对象
 **/
public class PBMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final short HDR_SIZE = 8;
	public static final short MAX_DATA_SIZE = Short.MAX_VALUE-HDR_SIZE-1;

	private CmdConstantsProto.CmdCode code; // CODE
	private byte[] bytes; // 数据体
	private MessageLite message ; // Proto
	public CmdConstantsProto.CmdCode getCode() {
		return code;
	}

	public PBMessage(byte[] bytes) {
		this.bytes = bytes;
	}

	public boolean empty() {
		return bytes == null || bytes.length == 0;// ?
	}

	public PBMessage(CmdConstantsProto.CmdCode code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public MessageLite getMessage() {
		return message;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setMessage(MessageLite message) {
		this.message = message;
	}

}
