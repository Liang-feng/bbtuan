package boyue.bbtuan.utils;

public class HttpConstants {

	
	public final static String HTTP_HEAD="http://";
	
	//内网
	//public final static String HTTP_IP="127.0.0.1:6000";
	//外网
	public final static String HTTP_IP="aa1094143832.eicp.net:80";
	public final static String HTTP_CONTEXT="/boyue/";

	public final static String HTTP_REQUEST=HTTP_HEAD+HTTP_IP+HTTP_CONTEXT;
	/**
	 * 用户反馈
	 */
	public static String HTTP_FEEDBACK=HTTP_REQUEST+"servlet/FeedBackServlet";
	
	/**
	 * 用户注册
	 */
	public static String HTTP_REGISTER=HTTP_REQUEST+"servlet/RegisterServlet";
	
	/**
	 * 用户登录
	 */
	public static String HTTP_LOGIN=HTTP_REQUEST+"servlet/LoginServlet";
	
	/**
	 * 修改用户图像
	 */
	public static String HTTP_UPDATE_USERICON=HTTP_REQUEST+"servlet/ChangeUserImgServlet";
	
	/**
	 * 修改用户资料
	 */
	public static String HTTP_UPDATE_USERDATA=HTTP_REQUEST+"servlet/ChangeDataServlet";
	
	/**
	 * 修改用户密码
	 */
	public static String HTTP_CHANGE_USERPWD=HTTP_REQUEST+"servlet/ChangePwdServlet";
	
	/**
	 * 寻找学霸
	 */
	public static String HTTP_FIND_STU=HTTP_REQUEST+"servlet/FindStuServlet";
	
	/**
	 * 获取当天所有公众心情
	 */
	public static String HTTP_ALL_DIARYS=HTTP_REQUEST+"diary/allDiarys.do";
	
	/**
	 * 根据id查看指定id相关心情
	 */
	public static String HTTP_ALL_DIARY_FORUSERID=HTTP_REQUEST+"diary/allMeDiarys.do";
	
	
	/**
	 * 对心情发表评论
	 */
	public static String HTTP_PINGLUN=HTTP_REQUEST+"comment/add.do";
	
	/**
	 * 根据ID 获取个人资料
	 */
	public static String HTTP_GET_USERINFO=HTTP_REQUEST+"user/getUserInfoId.do";
	
	/**
	 * 私信
	 */
	public static String HTTP_SEND_MESSAGE=HTTP_REQUEST+"message/add.do";
	
	/**
	 * 查询私信
	 */
	public static String HTTP_QUERY_AllMESSAGE=HTTP_REQUEST+"message/queryAllMessage.do";
	
	/**
	 * 版本更新
	 */
	public static String HTTP_VERSION_UPDATE=HTTP_REQUEST+"servlet/UpdateServlet";
	
	/**
	 * 拉黑
	 */
	public static String HTTP_PULL_THE_BLACK=HTTP_REQUEST+"message/addBlackList.do";
}
