package judgingSystem;

/**
 * 答案信息
 * 
 * @author lenovo
 *
 */
public class EvaluationResult {

	public static final int Accepted = 1; // 通过
	public static final int Wrong_Answer = 2; // 程序输出与标准答案不一致（不包括行末空格以及文件末空行）
	public static final int Time_Exceeded = 3; // 程序运行时间超过了题目限制
	public static final int Memory_Exceeded = 4; // 程序运行内存空间超过了题目限制
	public static final int Runtime_Error = 5; // 程序运行时错误
	public static final int Compile_Error = 6; // 编译失败
	public static final int System_Error = 7; // 系统错误
	public static final int Canceled = 8; // 评测被取消
	public static final int Unknown_Error = 9; // 未知错误

	private String SPJ_ret; // SPJ返回提示
	private boolean SPJ;// 是否使用SPJ
	private long SPJ_Score;// SPJ得分

	private int value;// 答案状态
	
	private long maxMemory;
	private long TimeConsum;

	public EvaluationResult() {
		value = EvaluationResult.Unknown_Error;
		SPJ = false;
	}

	public EvaluationResult(int value) {
		this.value = value;
	}

	public EvaluationResult(int value, boolean SPJ, String SPJ_ret, long SPJ_Score) {
		this.value = value;
		this.SPJ_ret = SPJ_ret;
		this.SPJ = SPJ;
		this.SPJ_Score = SPJ_Score;
	}

	public void setTimeConsum(long TimeConsum) {
		this.TimeConsum = TimeConsum;
	}
	
	public void setMaxMemory(long memory) {
		this.maxMemory = memory;
	}

	public void SetValue(int value) {
		this.value = value;
	}

	public void setSPJ(boolean SPJ, String SPJ_ret, long SPJ_Score) {
		this.SPJ_ret = SPJ_ret;
		this.SPJ = SPJ;
		this.SPJ_Score = SPJ_Score;
	}

	public int getValue() {
		return this.value;
	}

	public boolean getSPJ() {
		return this.SPJ;
	}

	public String getSPJ_ret() {
		return this.SPJ_ret;
	}

	public long getSPJ_Score() {
		return this.SPJ_Score;
	}

	public long getMaxMemory() {
		return this.maxMemory;
	}
	
	public long getTimeConsum() {
		return this.TimeConsum;
	}

}
