package judgingSystem;

/**
 * 测试点信息
 * 
 * @author STR
 *
 */
public class TestPoint {

	public static final int Byte_By_Byte = 1;
	public static final int Ignore_Space = 2;
	
	private long Time_Limit; //时间限制
	private long Memory_Limit; //内存限制
	private long Score; //分数

	private String Test_In;//测试点输入文件路径
	private String Test_Out;//测试点输出文件路径
	
	private int JudgeMod;
	
	private boolean SPJ;//是否含有SPJ
	private String SPj_File; //SPJ路径

	public TestPoint(int JudgeMod) {
		this.JudgeMod = JudgeMod;
		Time_Limit = 0;
		Memory_Limit = 0;
		Score = 0;
		SPJ = false;
	}

	public TestPoint(long Time_Limit, long Memory_Limit, long Score, int JudgeMod, String Test_In, String Test_Out) {
		this.JudgeMod = JudgeMod;
		this.Time_Limit = Time_Limit;
		this.Memory_Limit = Memory_Limit;
		this.Score = Score;
		this.Test_In = Test_In;
		this.Test_Out = Test_Out;
		this.SPJ = false;
	}
	
	public TestPoint(long Time_Limit, long Memory_Limit, long Score, int JudgeMod, String Test_In, String Test_Out, boolean SPJ, String SPJ_File) {
		this.JudgeMod = JudgeMod;
		this.Time_Limit = Time_Limit;
		this.Memory_Limit = Memory_Limit;
		this.Score = Score;
		this.Test_In = Test_In;
		this.Test_Out = Test_Out;
		this.SPJ = SPJ;
		this.SPj_File = SPJ_File;
	}
	
	public void SetJudgeMod(int JudgeMod) {
		this.JudgeMod = JudgeMod;
	}
	
	public void setTest_Out(String Test_Out) {
		this.Test_Out = Test_Out;
	}
	
	public void setTest_In(String Test_In) {
		this.Test_In = Test_In;
	}
	
	public void setSPJFIle(String SPJ_File) {
		this.SPj_File = SPJ_File;
	}
	
	public void setSPJ(boolean SPJ) {
		this.SPJ = SPJ;
	}

	public void setTime_Limit(long time) {
		this.Time_Limit = time;
	}

	public void setMemory_Limit(long memory) {
		this.Memory_Limit = memory;
	}

	public void setScore(long Score) {
		this.Score = Score;
	}

	public long getTime_Limit() {
		return this.Time_Limit;
	}

	public long getMemory_Limit() {
		return this.Memory_Limit;
	}

	public long getScore() {
		return this.Score;
	}
	
	public boolean getSPJ() {
		return this.SPJ;
	}

	public String getSPJFile() {
		return this.SPj_File;
	}
	
	public String getTest_In() {
		return this.Test_In;
	}
	
	public String getTest_Out() {
		return this.Test_Out;
	}
	
	public int getJudgeMod() {
		return this.JudgeMod;
	}
	
}
