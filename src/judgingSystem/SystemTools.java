package judgingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 集成系统工具
 * 
 * @author STR
 *
 */
public class SystemTools {

	/**
	 * 预加载动态库文件
	 */
	public static void prestrain() {
		System.loadLibrary("SystemTools");
	}

	/**
	 * 监控内存
	 * 
	 * @param pid
	 *            进程ID
	 * @return 返回内存占用量 单位 KB
	 */
	public static native long getMemByPID(long pid);

	/**
	 * 编译cpp文件并返回编译信息
	 * 
	 * @param compile_File
	 *            G++编译器目录
	 * @param fileName
	 *            文件名称
	 * @param outPutFile
	 *            输出目录
	 * @return 编译信息
	 * @throws IOException
	 *             编译错误
	 * @throws InterruptedException
	 *             系统异常
	 */
	public static String compileCPP(String compile_File, String fileName, String outPutFile)
			throws IOException, InterruptedException {
		Process process = Runtime.getRuntime()
				.exec("\"" + compile_File + "\" \"" + fileName + "\" -o \"" + outPutFile + "\"");

		new Thread() {
			public void run() {
				try {
					for (int i = 1; i <= 10 && process.isAlive(); i++)
						Thread.sleep(1000);

					process.destroy();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		process.waitFor();

		InputStreamReader ir = new InputStreamReader(process.getErrorStream());
		LineNumberReader input = new LineNumberReader(ir);

		String line, res = "";

		while ((line = input.readLine()) != null)
			res += line + '\n';

		return res == "" ? (process.exitValue() == 0 ? "无编译信息" : "编译超时") + "" : res;
	}

	/**
	 * 测试 exe
	 * 
	 * @param std
	 *            测试点信息
	 * @param file
	 *            文件目录
	 * @param tempOutputFile
	 *            临时输出文件目录
	 * @return 评测结果
	 */
	public static EvaluationResult runCPP(TestPoint std, String file, String tempOutputFile) {
		EvaluationResult ret = new EvaluationResult();

		try {

			String[] cmds = new String[] { "cmd.exe", "/c",
					"\"" + file + "\" < \"" + std.getTest_In() + "\" > \"" + tempOutputFile + "\" " };

			Process process = Runtime.getRuntime().exec(cmds);

			long begin = System.currentTimeMillis();

			new Thread() {
				public void run() {
					long memory;

					while (System.currentTimeMillis() - begin <= std.getTime_Limit() && process.isAlive()) {
						try {
							memory = getMemByPID(process.pid());

							if (memory > ret.getMaxMemory())
								ret.setMaxMemory(memory);

							if (memory > std.getMemory_Limit() * 1024 * 1024) {
								process.destroy();

								ret.SetValue(EvaluationResult.Memory_Exceeded);
								ret.setTimeConsum(System.currentTimeMillis() - begin);

								return;
							}

							Thread.sleep(10);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							ret.SetValue(EvaluationResult.System_Error);
							ret.setTimeConsum(System.currentTimeMillis() - begin);

							return;
						}
					}

					if (process.isAlive()) {
						ret.SetValue(EvaluationResult.Time_Exceeded);
						ret.setTimeConsum(System.currentTimeMillis() - begin);

						process.destroy();
					}
				}
			}.start();

			process.waitFor();

			ret.setTimeConsum(System.currentTimeMillis() - begin);

			if (ret.getValue() != EvaluationResult.Unknown_Error)
				return ret;

			if (process.exitValue() != 0) {
				ret.SetValue(EvaluationResult.Runtime_Error);

				return ret;
			}
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			ret.SetValue(EvaluationResult.System_Error);
			
			return ret;
		}
		
		return ret;

	}

	/**
	 * 传统答案比较
	 * 
	 * @param std
	 *            测试点信息
	 * @param tempOutputFile
	 *            临时输出文件目录
	 * @param result
	 *            运行时得到的评测结果
	 * @return 答案比较结果
	 */
	public static EvaluationResult resultComparison(TestPoint std, String tempOutputFile, EvaluationResult result) {
		if (result.getValue() != EvaluationResult.Unknown_Error)
			return result;

		try {
			BufferedReader Test_Read = new BufferedReader(new FileReader(tempOutputFile));
			BufferedReader STD_Read = new BufferedReader(new FileReader(std.getTest_Out()));
			String STD, player;

			while ((STD = STD_Read.readLine()) != null) {
				player = Test_Read.readLine();

				if (player != null) {
					if (!SystemTools.compareAns(STD, player, std.getJudgeMod())) {
						result.SetValue(EvaluationResult.Wrong_Answer);

						STD_Read.close();
						Test_Read.close();

						return result;
					}
				} else {
					if (STD.trim().equals(""))
						break;
					else {
						result.SetValue(EvaluationResult.Wrong_Answer);

						STD_Read.close();
						Test_Read.close();

						return result;
					}
				}
			}

			while ((STD = STD_Read.readLine()) != null)
				if (!STD.trim().equals("")) {
					result.SetValue(EvaluationResult.Wrong_Answer);

					STD_Read.close();
					Test_Read.close();

					return result;
				}

			while ((player = Test_Read.readLine()) != null)
				if (!player.trim().equals("")) {
					result.SetValue(EvaluationResult.Wrong_Answer);

					STD_Read.close();
					Test_Read.close();

					return result;
				}

			STD_Read.close();
			Test_Read.close();

			result.SetValue(EvaluationResult.Accepted);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			result.SetValue(EvaluationResult.System_Error);

			return result;
		}

		return result;
	}

	/**
	 * 按照给定方式对比输出
	 * 
	 * @param std
	 *            标准输出
	 * @param player
	 *            选手输出
	 * @param mod
	 *            比较方式
	 * @return 比较结果
	 */
	private static boolean compareAns(String std, String player, int mod) {
		switch (mod) {
		case TestPoint.Byte_By_Byte:
			return std.equals(player);
		case TestPoint.Ignore_Space:
			return SystemTools.trimeEndSpace(std).equals(SystemTools.trimeEndSpace(player));
		}

		return false;
	}

	/**
	 * 仅消除字符串末尾空格
	 * 
	 * @param str
	 *            需要消除的字符串
	 * @return 结果
	 */
	private static String trimeEndSpace(String str) {
		for (int i = str.length() - 1; i >= 0; i--)
			if (str.substring(i) == " ")
				str = str.substring(0, i);

		return str;
	}

}
