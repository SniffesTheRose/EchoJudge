package codeJudger;

/**
 * 答案信息
 * 
 * @author STR
 *
 */
public class EvaluationResult {

	/**
	 * 通过
	 */
	public static final int Accepted = 1;
	/**
	 * 程序输出与标准答案不一致（不包括行末空格以及文件末空行）
	 */
	public static final int Wrong_Answer = 2;
	/**
	 * 程序运行时间超过了题目限制
	 */
	public static final int Time_Exceeded = 3;
	/**
	 * 程序运行内存空间超过了题目限制
	 */
	public static final int Memory_Exceeded = 4;
	/**
	 * 程序运行时错误
	 */
	public static final int Runtime_Error = 5;
	/**
	 * 系统错误
	 */
	public static final int System_Error = 6;
	/**
	 * 评测被取消
	 */
	public static final int Canceled = 7;
	/**
	 * 未知错误
	 */
	public static final int Unknown_Error = 8;

	private boolean CustomVerifier; // 是否使用自定义答案校验器
	private String CustomVerifierRet; // 自定义答案校验器返回提示
	private long CustomVerifierScore; // 自定义答案校验器得分

	private int value;// 答案状态

	private long maxMemory;
	private long TimeConsum;

	/**
	 * 构造一个具有未知错误的初始答案
	 */
	public EvaluationResult() {
		value = EvaluationResult.Unknown_Error;
		CustomVerifier = false;
		this.TimeConsum = 0;
	}

	/**
	 * 构造一个具有给定值的初始答案
	 * 
	 * @param value
	 *            评测结果类型
	 */
	public EvaluationResult(int value) {
		this.value = value;
		this.TimeConsum = 0;
	}

	/**
	 * 构造一个自定义答案校验器的初始答案
	 * 
	 * @param value
	 *            测评结果类型
	 * @param CustomVerifier
	 *            是否使用自定义答案校验器
	 * @param CustomVerifierRet
	 *            自定义答案校验器提示信息
	 * @param CustomVerifierScore
	 *            自定义答案校验器得分
	 */
	public EvaluationResult(int value, boolean CustomVerifier, String CustomVerifierRet, long CustomVerifierScore) {
		this.value = value;
		this.CustomVerifierRet = CustomVerifierRet;
		this.CustomVerifier = CustomVerifier;
		this.CustomVerifierScore = CustomVerifierScore;
		this.TimeConsum = 0;
	}

	/**
	 * 设置程序用时
	 * 
	 * @param TimeConsum
	 *            程序用时
	 */
	public void setTimeConsum(long TimeConsum) {
		this.TimeConsum = TimeConsum;
	}

	/**
	 * 设置最大内存占用
	 * 
	 * @param memory
	 *            最大内存占用
	 */
	public void setMaxMemory(long memory) {
		this.maxMemory = memory;
	}

	/**
	 * 设置评测结果类型
	 * 
	 * @param value
	 *            评测结果类型
	 */
	public void SetValue(int value) {
		this.value = value;
	}

	/**
	 * 设置定义答案校验器信息
	 * 
	 * @param CustomVerifier
	 *            是否使用自定义答案校验器
	 * @param CustomVerifierRet
	 *            自定义答案校验器提示信息
	 * @param CustomVerifierScore
	 *            自定义答案校验器得分
	 */
	public void setCustomVerifier(boolean CustomVerifier, String CustomVerifierRet, long CustomVerifierScore) {
		this.CustomVerifierRet = CustomVerifierRet;
		this.CustomVerifier = CustomVerifier;
		this.CustomVerifierScore = CustomVerifierScore;
	}

	/**
	 * 获得结果类型
	 * 
	 * @return 结果类型
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * 获得自定义答案校验器使用情况
	 * 
	 * @return 是否使用自定义答案校验器
	 */
	public boolean getCustomVerifier() {
		return this.CustomVerifier;
	}

	/**
	 * 获得自定义答案校验器提示信息
	 * 
	 * @return 自定义答案校验器提示信息
	 */
	public String getCustomVerifierRet() {
		return this.CustomVerifierRet;
	}

	/**
	 * 获得自定义答案校验器得分
	 * 
	 * @return 自定义答案校验器得分
	 */
	public long getCustomVerifierScore() {
		return this.CustomVerifierScore;
	}

	/**
	 * 获取程序占用最大内存
	 * 
	 * @return 程序占用最大内存
	 */
	public long getMaxMemory() {
		return this.maxMemory;
	}

	/**
	 * 获取程序运行时间
	 * 
	 * @return 程序运行时间
	 */
	public long getTimeConsum() {
		return this.TimeConsum;
	}

}
