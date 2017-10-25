package codeJudger;

/**
 * 答案信息
 * 
 * @author lenovo
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

	private String SPJ_ret; // SPJ返回提示
	private boolean SPJ;// 是否使用SPJ
	private long SPJ_Score;// SPJ得分

	private int value;// 答案状态

	private long maxMemory;
	private long TimeConsum;

	/**
	 * 构造一个具有未知错误的初始答案
	 */
	public EvaluationResult() {
		value = EvaluationResult.Unknown_Error;
		SPJ = false;
	}

	/**
	 * 构造一个具有给定值的初始答案
	 * 
	 * @param value
	 *            评测结果类型
	 */
	public EvaluationResult(int value) {
		this.value = value;
	}

	/**
	 * 构造一个指定SPJ的初始答案
	 * 
	 * @param value
	 *            测评结果类型
	 * @param SPJ
	 *            是否使用SPJ
	 * @param SPJ_ret
	 *            SPJ提示信息
	 * @param SPJ_Score
	 *            SPJ得分
	 */
	public EvaluationResult(int value, boolean SPJ, String SPJ_ret, long SPJ_Score) {
		this.value = value;
		this.SPJ_ret = SPJ_ret;
		this.SPJ = SPJ;
		this.SPJ_Score = SPJ_Score;
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
	 * 设置SPJ信息
	 * 
	 * @param SPJ
	 *            是否使用SPJ
	 * @param SPJ_ret
	 *            SPJ提示信息
	 * @param SPJ_Score
	 *            SPJ得分
	 */
	public void setSPJ(boolean SPJ, String SPJ_ret, long SPJ_Score) {
		this.SPJ_ret = SPJ_ret;
		this.SPJ = SPJ;
		this.SPJ_Score = SPJ_Score;
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
	 * 获得SPJ使用情况
	 * 
	 * @return 是否使用SPJ
	 */
	public boolean getSPJ() {
		return this.SPJ;
	}

	/**
	 * 获得SPJ提示信息
	 * 
	 * @return SPJ提示信息
	 */
	public String getSPJ_ret() {
		return this.SPJ_ret;
	}

	/**
	 * 获得SPJ得分
	 * 
	 * @return SPJ得分
	 */
	public long getSPJ_Score() {
		return this.SPJ_Score;
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
