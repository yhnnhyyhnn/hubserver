package com.util;

import com.google.protobuf.MessageLite;

import proto.CmdConstantsProto;
import proto.ErrorMsgProto;
import proto.ErrorMsgProto.ErrorCode;

public class MessageUtil {

	public static PBMessage buildErrorMessage(CmdConstantsProto.CmdCode businessCmd, BusinessException e) {
		ErrorMsgProto.ErrorMsg.Builder err = ErrorMsgProto.ErrorMsg.newBuilder();
		err.setCmd(businessCmd.getNumber());
		err.setErrorcode(e.getErrorCode());
		err.setMsg(e.getMessage());
		return buildMessage(CmdConstantsProto.CmdCode.SERVER_ERROR, err.build());
	}

	public static PBMessage buildErrorMessage(CmdConstantsProto.CmdCode businessCmd, ErrorCode errorCode) {
		return buildErrorMessage(businessCmd, new BusinessException(errorCode));
	}

	public static PBMessage buildErrorMessage(CmdConstantsProto.CmdCode businessCmd, ErrorCode errorCode, String mesg) {
		return buildErrorMessage(businessCmd, new BusinessException(errorCode, mesg));
	}

	/**
	 * 消息创建
	 * 
	 * @param code
	 * @param client
	 * @return
	 */
	public static PBMessage buildMessage(CmdConstantsProto.CmdCode code, MessageLite message) {
		PBMessage pbMessage = new PBMessage(code);
		pbMessage.setMessage(message);
		return pbMessage;
	}

}
