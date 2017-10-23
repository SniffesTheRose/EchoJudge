package judgingSystem;

/**
 * 测试点信息
 * 
 * @author STR
 *
 */
public class TestPoint {

	/**
	 * 逐字比较方式
	 */
	public static final int Byte_By_Byte = 1;
	/**
	 * 忽略空行以及行末空格比较方式
	 */
	public static final int Ignore_Space = 2;

	private long Time_Limit; // 时间限制
	private long Memory_Limit; // 内存限制
	private long Score; // 分数

	private String Test_In;// 测试点输入文件路径
	private String Test_Out;// 测试点输出文件路径

	private int JudgeMod;

	private boolean SPJ;// 是否含有SPJ
	private String SPj_File; // SPJ路径

	/**
	 * 通过给定答案比较方式构造测试点对象
	 * 
	 * @param JudgeMod
	 *            答案毕节方式
	 */
	public TestPoint(int JudgeMod) {
		this.JudgeMod = JudgeMod;
		Time_Limit = 0;
		Memory_Limit = 0;
		Score = 0;
		SPJ = false;
	}

	/**
	 * 通过时间限制，内存限制，测试点分值，答案比较方式，输入文件位置，输出文件位置构造测试点对象
	 * 
	 * @param Time_Limit
	 *            时间限制
	 * @param Memory_Limit
	 *            内存限制
	 * @param Score
	 *            测试点分值
	 * @param JudgeMod
	 *            答案比较方式
	 * @param Test_In
	 *            输入文件位置
	 * @param Test_Out
	 *            输出文件位置
	 */
	public TestPoint(long Time_Limit, long Memory_Limit, long Score, int JudgeMod, String Test_In, String Test_Out) {
		this.JudgeMod = JudgeMod;
		this.Time_Limit = Time_Limit;
		this.Memory_Limit = Memory_Limit;
		this.Score = Score;
		this.Test_In = Test_In;
		this.Test_Out = Test_Out;
		this.SPJ = false;
	}

	/**
	 * 通过时间限制，内存限制，测试点分值，答案比较方式，输入文件位置，输出文件位置，SPJ使用情况，SPJ文件目录构造测试点对象
	 * 
	 * @param Time_Limit
	 *            时间限制
	 * @param Memory_Limit
	 *            内存限制
	 * @param Score
	 *            测试点分值
	 * @param JudgeMod
	 *            答案比较方式
	 * @param Test_In
	 *            输入文件位置
	 * @param SPJ
	 *            SPJ使用情况
	 * @param SPJ_File
	 *            SPJ文件目录
	 */
	public TestPoint(long Time_Limit, long Memory_Limit, long Score, int JudgeMod, String Test_In, boolean SPJ,
			String SPJ_File) {
		this.JudgeMod = JudgeMod;
		this.Time_Limit = Time_Limit;
		this.Memory_Limit = Memory_Limit;
		this.Score = Score;
		this.Test_In = Test_In;
		this.SPJ = SPJ;
		this.SPj_File = SPJ_File;
	}

	/**
	 * 设置答案比较方式
	 * 
	 * @param JudgeMod
	 *            答案比较方式
	 */
	public void SetJudgeMod(int JudgeMod) {
		this.JudgeMod = JudgeMod;
	}

	/**
	 * 设置输出文件目录
	 * 
	 * @param Test_Out
	 *            输出文件目录
	 */
	public void setTest_Out(String Test_Out) {
		this.Test_Out = Test_Out;
	}

	/**
	 * 设置输入文件目录
	 * 
	 * @param Test_In
	 *            输入文件目录
	 */
	public void setTest_In(String Test_In) {
		this.Test_In = Test_In;
	}

	/**
	 * 设置SPJ文件目录
	 * 
	 * @param SPJ_File
	 *            SPJ文件目录
	 */
	public void setSPJFIle(String SPJ_File) {
		this.SPj_File = SPJ_File;
	}

	/**
	 * 设置SPJ使用情况
	 * 
	 * @param SPJ
	 *            SPJ使用情况
	 */
	public void setSPJ(boolean SPJ) {
		this.SPJ = SPJ;
	}

	/**
	 * 设置时间限制
	 * 
	 * @param time
	 *            时间限制
	 */
	public void setTime_Limit(long time) {
		this.Time_Limit = time;
	}

	/**
	 * 设置内存限制
	 * 
	 * @param memory
	 *            内存限制
	 */
	public void setMemory_Limit(long memory) {
		this.Memory_Limit = memory;
	}

	/**
	 * 设置测试点分值
	 * 
	 * @param Score
	 *            测试点分值
	 */
	public void setScore(long Score) {
		this.Score = Score;
	}

	/**
	 * 获取测试点时间限制
	 * 
	 * @return 测试点时间限制
	 */
	public long getTime_Limit() {
		return this.Time_Limit;
	}

	/**
	 * 获取测试点内存限制
	 * 
	 * @return 测试点内存限制
	 */
	public long getMemory_Limit() {
		return this.Memory_Limit;
	}

	/**
	 * 获取测试点分值
	 * 
	 * @return 测试点分值
	 */
	public long getScore() {
		return this.Score;
	}

	/**
	 * 获取SPJ使用情况
	 * 
	 * @return 是否使用SPJ
	 */
	public boolean getSPJ() {
		return this.SPJ;
	}

	/**
	 * 获取SPJ文件目录
	 * 
	 * @return SPJ文件目录
	 */
	public String getSPJFile() {
		return this.SPj_File;
	}

	/**
	 * 获取输入文件目录
	 * 
	 * @return 输入文件目录
	 */
	public String getTest_In() {
		return this.Test_In;
	}

	/**
	 * 获取输出文件目录
	 * 
	 * @return 输出文件目录
	 */
	public String getTest_Out() {
		return this.Test_Out;
	}

	/**
	 * 获取答案比较方式
	 * 
	 * @return 答案比较方式
	 */
	public int getJudgeMod() {
		return this.JudgeMod;
	}

}
