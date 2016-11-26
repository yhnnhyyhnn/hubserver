package com.util;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import proto.ErrorMsgProto.ErrorCode;

public class BusinessException extends Exception {
	private final ErrorCode errorCode;
	private final String message;
	private static final long serialVersionUID = 1L;

	public BusinessException(ErrorCode errorCode) {
		this(errorCode, errorMap.get().get(errorCode));
	}

	public BusinessException(ErrorCode errorCode, String message) {
		this.errorCode = errorCode;
		if (StringUtils.isEmpty(message)) {
			this.message = "出错代码:" + errorCode.getNumber();
		} else {
			this.message = message;
		}
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public static void init(Map<String, String> map) {
		Map<ErrorCode, String> m = new HashMap<>();
		for (Map.Entry<String, String> e : map.entrySet()) {
			m.put(ErrorCode.valueOf(e.getKey()), e.getValue());
		}
		errorMap.set(m);
	}

//	public static Map<String, String> getErrorMap() {
//		Map<String, String> map = new HashMap<>();
//
//		for (Map.Entry<ErrorCode, String> e : errorMap.get().entrySet()) {
//			map.put(e.getKey().toString(), e.getValue());
//		}
//		return map;
//	}

	private static AtomicReference<Map<ErrorCode, String>> errorMap = new AtomicReference<>(
			new EnumMap<ErrorCode, String>(ErrorCode.class) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					// for s in open('/tmp/a.txt').readlines():
					// print('put(ErrorCode.%s,"");' % s[ s.find('return ') + 7:
					// -2])

					/*----------start----------*/
					put(ErrorCode.UNKNOW, "未知错误");
					put(ErrorCode.SUCCEED, "成功");
					put(ErrorCode.SYS_SESSION_EXPIRY, "您需要重新登录");
					put(ErrorCode.PLAYER_IS_OTHER_LOGIN, "101-200个人共同提示消息");
					put(ErrorCode.LOGIN_ACCOUNT_PASSWORD_ERROR, "用户名或密码错误");
					put(ErrorCode.LOGIN_ACCOUNT_NULLITY, "您的帐号暂停使用，审核后将自动恢复；您也可以先尝试联系“在线客服”");
					put(ErrorCode.LOGIN_BINDING_OTHER_MACHINE, "绑定其它机器");
					put(ErrorCode.LOGIN_SYSTEM_ENJOIN_LOGON, "由于系统维护，暂时停止游戏系统的登录服务，请留意网站公告信息！");
					put(ErrorCode.LOGIN_SYSTEM_ENJOIN_LOGON_IP, "抱歉地通知您，系统禁止了您所在的 IP 地址的登录功能，请联系客户服务中心了解详细情况！");
					put(ErrorCode.LOGIN_SYSTEM_ENJOIN_LOGIN_MACHINE, "抱歉地通知您，系统禁止了您的机器的登录功能，请联系客户服务中心了解详细情况！");
					put(ErrorCode.REGISTER_SYSTEM_ENJOIN, "注册暂停 提示");
					put(ErrorCode.LOGIN_SYSTEM_ENJOIN, "登陆暂停 提示");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_NAME, "抱歉地通知您，您所输入的登录帐号名含有限制字符串，请更换帐号名后再次申请帐号！");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_NICKNAME, "抱歉地通知您，您所输入的昵称名含有限制字符串，请更换帐号名后再次申请帐号！");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_IP, "抱歉地通知您，系统禁止了您所在的 IP 地址的注册功能，请联系客户服务中心了解详细情况！");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_MACHINE, "抱歉地通知您，系统禁止了您的机器的注册功能，请联系客户服务中心了解详细情况！");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_ACCOUNTS_REG, "此账号已被注册，请换另一账号尝试再次注册！");
					put(ErrorCode.REGISTER_SYSTEM_FORBID_NICKNAME_REG, "此昵称已被注册，请换另一昵称尝试再次注册！");
					put(ErrorCode.GAME_SERVER_LOGON, "你已经在别的房间了 无法登陆此房间");
					put(ErrorCode.GAME_SERVER_ENTERROOM, "登陆房间人数已满");
					put(ErrorCode.GAME_SERVER_ENTERROOMChari, "椅子位置已被占用");
					put(ErrorCode.GAME_SERVER_ENTERROOMCHARITABLE, "你已经在一个桌子中了 请勿重复 坐下");
					put(ErrorCode.GAME_SERVER_MACHEGAMEOVER, "比赛已经结束或者比赛还没开始");
					put(ErrorCode.GAME_SERVER_ISNOTSINGUP, "你没有报名不能参加比赛");
					put(ErrorCode.SAVE_MONEY_ENJOININSURE, "系统禁止了您所在的 IP 地址的游戏服务权限");
					put(ErrorCode.SAVE_MONEY_ENJOINLOGIN, "系统禁止了您的机器的游戏服务权限");
					put(ErrorCode.SAVE_MONEY_NOTHISUSER, "您的帐号不存在或者密码输入有误");
					put(ErrorCode.SAVE_MONEY_NULLITY, "您的帐号暂时处于冻结状态");
					put(ErrorCode.SAVE_MONEY_STUNDOWN, "您的帐号使用了安全关闭功能");
					put(ErrorCode.SAVE_MONEY_MOORMACHINE, "您的帐号使用固定机器登录功能");
					put(ErrorCode.SAVE_MONEY_LESSMONEY, "存入保险柜的游戏币数目过少");
					put(ErrorCode.SAVE_MONEY_LESSGAMEMONEY, "您当前游戏币的可用余额不足，游戏币存入失败");
					put(ErrorCode.SAVE_MONEY_INGAME, "您正在游戏中");
					put(ErrorCode.SAVE_MONEY_BADPWD, "您的保险柜密码不正确或者输入有误，请查证后再次尝试！");
					put(ErrorCode.CHANGE_MONEY_LESSMONEY, "兑换的金币数目过少");
					put(ErrorCode.CHANGE_MONEY_NOTMONEY, "用户金币数目没有或者兑换数目超过了拥有的金币数");
					put(ErrorCode.GET_USERINFO_NOUSER, "该用户不存在");
					put(ErrorCode.GET_USERINFO_FORBIDNICKMAE, "您所输入的游戏昵称名含有限制字符串，请更换昵称名后再次修改！");
					put(ErrorCode.GET_USERINFO_INUSERNICKNAME, "此昵称已被其他玩家使用了，请更换昵称名后再次修改！");
					put(ErrorCode.GET_USERINFO_NULLNICKNAME, "昵称不能为空");
					put(ErrorCode.UPDATE_LOGINPWD_NOTINPWD, "新帐号密码为空，不允许设置密码为空，请查证后再次尝试！");
					put(ErrorCode.SAVEORTAKE_MONEY_ERROR, "存入或者取出幸运豆数量不能为空");
					put(ErrorCode.TAKE_MONEY_NOCODE, "取出幸运豆是，请输入保险柜密码");
					put(ErrorCode.UPDATE_PWD_NOPWD, "原始密码不能为空");
					put(ErrorCode.SAVE_MONEY_NOTENOUGHMONEY, "您的保险柜余额不足");
					put(ErrorCode.MONEY_NOT_ENOUGH, "你身上带的钱不足，不能进入房间");
					put(ErrorCode.ROOM_FULL, "人数已满");
					put(ErrorCode.SING_UP_MAX, "最大人报名次数限制");
					put(ErrorCode.SING_NOT_UP, "比赛开始不能退出比赛");
					put(ErrorCode.USER_LOCK_MACHINE, "锁定本家失败密码输入有误");
					put(ErrorCode.PROP_CHANGE_LESS, "兑换的道具大于自己拥有的。");
					put(ErrorCode.USER_LOGIN_OTHER, "您的账户已在异地登录  ");
					put(ErrorCode.LOGIN_ACCOUNT_ERROR, "用户名不存在");
					put(ErrorCode.LOGIN_PASSWORD_ERROR, "密码错误");
					put(ErrorCode.LOGIN_NOTFIND_GIFT, "没找到对应的道具");
					put(ErrorCode.LOGIN_USERSCORE_ERROR, "用户财富信息出错");
					put(ErrorCode.LOGIN_USERSCORE_NOTENOUGH, "用户钱财不足");
					put(ErrorCode.LOGIN_UPDATE_DATE_ERROR, "更新数据出错了");
					put(ErrorCode.LOGIN_BANK_PASWD_ERROR, "保险柜密码错误");
					put(ErrorCode.LOGIN_GIVE_ROSE_NO_ZERO, "赠送的数量不能为0");
					put(ErrorCode.LOGIN_GIVE_ROSE_TO_ME, "不能自己赠送给自己");
					put(ErrorCode.LOGIN_IP_COUNT, "你注册使用的ip 数已经达到最大上限");
					put(ErrorCode.LOGIN_MAC_COUNT, "你所使用的机器码  今天已经达到了最大上限");
					put(ErrorCode.INVITE_USER_SCORE, "邀请用户领取幸运豆失败");
					put(ErrorCode.USER_INVITED_SCORE, "已经被邀请过不能重复领取奖励");
					put(ErrorCode.RADPACKETCODING_ERR, "红包兑换码错误");
					put(ErrorCode.RADPACKETCODING_USED, "红包码使用过");
					put(ErrorCode.USER_INVITED_USERSELF, "用户不能自己邀请自己");
					put(ErrorCode.USER_INVITED_NOEXIST, "邀请码错误");
					put(ErrorCode.RADPACKETCODING_ERROE, "红包兑换码生成错误");
					put(ErrorCode.LOGIN_MAC_EVER_COUNT, "你所使用的机器码  已经达到了最大上限");
					put(ErrorCode.LOGIN_EVENT_NOTBEGIN, "活动还未开始");
					put(ErrorCode.LOGIN_EVENT_FINISHED, "活动已经结束");
					put(ErrorCode.LOGIN_EVENT_ZZ_NOTENOUGH, "你的粽子不够了");
					put(ErrorCode.RADPACKETCODING_RECEIVED, "您已经领取过新手礼包，请勿重新使用！");
					put(ErrorCode.GAME_RANK_NOT_IN_RANK, "未进入排名，无法领奖");
					put(ErrorCode.GAME_RANK_RANK_AWARD_RECVED, "排名奖励已经领取过");
					put(ErrorCode.GAME_RANK_THUMB_DONE, "不能重复点赞");
					put(ErrorCode.GAME_RANK_THUMB_REACH_MAX, "此榜单当日点赞次数已达最大值");
					put(ErrorCode.GAME_ONLINE_TIME_LOW, "领取在线奖励的时间不到");
					put(ErrorCode.GAME_ONLINE_RECEIVE, "你已经领取过奖励了");
					put(ErrorCode.GAME_RANK_SAVING, "排行榜单生成中");
					put(ErrorCode.LOGIN_VERIFY_FAILED, "手机验证失败");
					put(ErrorCode.QIAN_DAO_TODAY_SIGNED, "今日已经签到");
					put(ErrorCode.QIAN_DAO_RECV_DISABLE, "条件不足,无法领奖");
					put(ErrorCode.QIAN_DAO_RECV_DONE, "您已经领取过奖励了");
					put(ErrorCode.RANK_TODAY_CACHE_EMPTY, "当日排行缓存为空");
					put(ErrorCode.RANK_YESTERDAY_CACHE_EMPTY, "昨日排行为空");
					put(ErrorCode.LOGIN_PASSWD_LENGTH_ERR, "请设置6-32位之间的密码");

					put(ErrorCode.LOGIN_PHONE_FORMAT_ERR, "手机号码输错了吧");
					put(ErrorCode.LOGIN_VERIFYCODE_TOOFREQUENT, "你获取验证码太频繁");
					put(ErrorCode.LOGIN_USEITEM_LEVEL_NOTENOUGH, "等级或vip不足，无法使用该物品");
					put(ErrorCode.LOGIN_USEPACK_INROOM, "房间中不能使用背包");
					put(ErrorCode.LOGIN_USERPACK_FULL, "您的背包空间不足，请清理后重试");
					put(ErrorCode.LOGIN_USER_ALREADY_GETPRIZE, "您已领取过节日礼包");
					put(ErrorCode.LOGIN_USER_NOFESTIVAL, "还没有到节日，请改天再领");
					put(ErrorCode.LOGIN_MUSTBEPOSITIVE, "请输入大于0的数字");

					put(ErrorCode.GAME_RANK_THUMB_TO_SELF, "游戏排行榜,不能对自己点赞");
					put(ErrorCode.MATCH_YI_BAO_MING, "您已经报名此场比赛");
					put(ErrorCode.MATCH_WEI_BAO_MING, "您未报名此场比赛");
					put(ErrorCode.MATCH_NOT_EXISTS_TYPE, "不存在的比赛小类型");
					put(ErrorCode.MATCH_TYPE_EMPTY, "服务器未配置比赛类型");
					put(ErrorCode.MATCH_NOT_ENOUGH_SCORE, "幸运豆不足,无法报名");
					put(ErrorCode.MATCH_ALREADY_BEGIN_CANT_CANCEL, "比赛已经开始,无法取消报名");
					put(ErrorCode.ERROR_MATCH_INFO, "");
					put(ErrorCode.MATCH_STATUS_NOT_START, "");
					put(ErrorCode.MATCH_ALREADY_OVER, "比赛已经结束");
					put(ErrorCode.MATCH_SIGUP_DUPLICATE, "你报名的比赛还没开始，请勿重复报名，请耐心等待");
					put(ErrorCode.MATCH_SIGUP_USERSOCORE_ERROR, "查询玩家的信息出错了");
					put(ErrorCode.MATCH_SIGUP_USERSOCORE_LACK, "你身上的钱不够无法报名");
					put(ErrorCode.MATCH_SIGUP_EMPTY_SIT, "当前已经没有玩家能坐下的位置了");
					put(ErrorCode.MATCH_SIGUP_CREATEMATCH_ERROR, "创建比赛失败了");
					put(ErrorCode.MATCH_NOT_SIGUP_ERROR, "当前已经没有玩家能坐下的位置了");
					put(ErrorCode.MATCH_NOT_SIGUP_NOT_BENGIN, "报名的时间还没到");
					put(ErrorCode.MATCH_NOT_SIGUP_END_BENGIN, "报名的时间已经结束了");
					put(ErrorCode.MATCH_NOT_SIGUP_MAINTAIN, "报名的时间已经结束了");
					put(ErrorCode.MATCH_NO_PRIZEINFO, "比赛未配置奖励信息");
					put(ErrorCode.MATCH_SIGUP_NOT_EXIST, "报名的比赛不存在");
					put(ErrorCode.MATCH_BEGIN_DELY, "比赛开始倒计时中,请参加比赛");
					put(ErrorCode.MATCH_VIP_LIMIT, "您的Vip等级不足Vip%d，无法报名！");
					put(ErrorCode.MATCH_LEVEL_LIMIT, "您的等级不足%d级，无法报名！");
					put(ErrorCode.MATCH_SCORE_LIMIT, "最低需携带 %d 幸运豆才可报名该比赛！");
					put(ErrorCode.NO_SUCHTHING, "您没有该物品");
					put(ErrorCode.CANT_USE, "该物品不能使用");
					put(ErrorCode.NUM_NOTENOUGH, "您没有足够的该物品");
					put(ErrorCode.CANT_SELL, "该物品无法出售");
					put(ErrorCode.TODAY_UPPER_LIMIT, "该玩家邀请人数已达到当日最大限制");
					put(ErrorCode.NOT_OPEN, "改道具未开启");
					put(ErrorCode.VERSION_TOO_LOW, "版本太低");
					put(ErrorCode.NOT_ENOUGH_SCORE, "幸运豆不足");
					put(ErrorCode.CHAT_DISABLE, "聊天禁言");
					put(ErrorCode.CHAT_LIMIT_NOT_FOUND, "未找到聊天限制的配置信息chat.csv");
					put(ErrorCode.INVITE_USER_BIND, "您未绑定手机，无法领取奖励");
					put(ErrorCode.LOGIN_NO_SUCH_ACTIVITY, "没有此活动");
					put(ErrorCode.LOGIN_VIP_LEVEL_LOW, "vip等级不足");
					put(ErrorCode.LOGIN_VIP_LEVEL_NOTEXSIT, "不存在该vip级别的奖励");
					put(ErrorCode.ACTIVITY_ALREADY_RECVED, "您已经领取过该奖励");
					put(ErrorCode.ACTIVITY_NO_SUCH_ITEM, "不存在该阶段的奖励");
					put(ErrorCode.ACTIVITY_DISALLOWED, "您目前不满足领取该阶段奖励的条件");
					put(ErrorCode.GAMETASK_SCORE_ERROR, "幸运豆不足");
					put(ErrorCode.GAMETASK_IS_NOT_OVER, "任务没完成");
					put(ErrorCode.GAMETASK_IS_NOT_REF, "任务刷新次数已经完成");
					put(ErrorCode.GAME_TIAOZHAN_SCORE_LOW, "挑战赛未达到当前阶段积分");
					put(ErrorCode.LOGIN_GET_XCLB, "获取新春礼包");
					put(ErrorCode.USE_TOO_FREQUENT, "物品使用过于频繁");
					put(ErrorCode.ITEM_EXPIRE, "物品已过期");
					put(ErrorCode.LOGIN_GET_YK, "获取月卡");
					put(ErrorCode.ITEM_COMPOSE_CANTCOMPOSE, "不能参与合成");
					put(ErrorCode.ITEM_COMPOSE_CANTBECOMPOSE, "不能被合成");
					put(ErrorCode.USER_DEF_ERROR, "自定义动态错误内容");
					put(ErrorCode.MATCH_IN_LEVEL_LOW, "进入比赛等级过低");
					put(ErrorCode.MATCH_IN_VIP_LOW, "进入比赛vip过低");
					put(ErrorCode.ARENA_ON, "您尚有竞技场比赛进行中");
					put(ErrorCode.ARENA_COUNT_OVER, "您当日竞技场挑战次数已用尽");
					put(ErrorCode.ARENA_ITEM_LACK, "报名所需消耗品不足");
					put(ErrorCode.ARENA_FEE_LACK, "报名所需幸运豆不足");
					put(ErrorCode.ARENA_LEVEL_LOW, "等级过低无法解锁竞技场");
					put(ErrorCode.ARENA_VIP_LOW, "vip等级过低无法解锁竞技场");
					put(ErrorCode.ARENA_UNLOCK_ITEM_LOW, "解锁竞技场所需消耗品不足");
					put(ErrorCode.ARENA_LOCK, "竞技场尚未解锁");
					put(ErrorCode.ARENA_TIME_OFF, "当前时间段竞技场未开启");
					put(ErrorCode.ARENA_ROOM_LACK, "竞技场房间不足");
					put(ErrorCode.READ_PACK_INVALID, "1.渠道不符 您使用的红包码无效，请联系客服");
					put(ErrorCode.READ_PACK_NOT_EXIST, "2.开始  该红包码不存在，请联系客服");
					put(ErrorCode.READ_PACK_NOT_LOSE, "3.结束  该红包码已失效，请联系客服");
					put(ErrorCode.READ_PACK_NOT_AC_REPEAT, "4.账号超过次数  您的账号已使用过此类红包码");
					put(ErrorCode.READ_PACK_NOT_MAC_REPEAT, "5.设备超过次数  您的设备已使用过此类红包码");
					put(ErrorCode.FREE_GOLD_EXCHANGE_NOT_ENOUGH_SCORE, "您的邀请积分不足,无法兑换");
					put(ErrorCode.DISABLED_AFTER_ACCESS_NEW_FREEGOLD, "您已经使用过新版免费金币,请使用新版本客户端");
					put(ErrorCode.LOGIN_DISABLE_ANONYMOUS, "您的这台设备已经绑定过手机哦，请使用手机号登录，或重新注册");
					put(ErrorCode.VERIFYCODE_INVALID, "验证码无效");
					put(ErrorCode.MOBILE_ALREADY_BIND_OTHER, "此手机号曾经注册过哦");
					put(ErrorCode.MOBILE_ALREADY_BIND, "用户已经绑定过手机号");
					put(ErrorCode.MOBILE_NOT_BIND, "此手机号还没注册过吧");
					put(ErrorCode.MOBILE_INCONSISTENT_BIND, "和绑定的手机号不一致");
					/*----------end----------*/

				}
			});

}
