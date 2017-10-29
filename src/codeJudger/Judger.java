package codeJudger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import judgingSystem.SystemTools;

/**
 * 代码评测类
 * 
 * @author STR
 *
 */
public abstract class Judger {

	/**
	 * 编译文件并返回编译信息
	 * 
	 * @param compile_File
	 *            编译器目录
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
	public abstract String compile(String compile_File, String fileName, String outPutFile)
			throws IOException, InterruptedException;

	/**
	 * 传统测试
	 * 
	 * @param std
	 *            测试点信息
	 * @param file
	 *            文件目录
	 * @param tempOutputFile
	 *            临时输出文件目录
	 * @return 评测结果
	 */
	public abstract EvaluationResult simply_Runner(TestPoint std, String file, String tempOutputFile);

	/**
	 * 交互测试
	 * 
	 * @param std
	 *            测试点信息
	 * @param file
	 *            文件目录
	 * @param inte_File
	 *            交互文件目录
	 * @param Score_File
	 *            最终得分目录
	 * @param Error_File
	 *            错误报告目录
	 * @return 得分情况
	 */
	public abstract EvaluationResult interact_Runner(TestPoint std, String file, String inte_File, String Score_File,
			String Error_File);

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
	public static EvaluationResult simplyResultComparison(TestPoint std, String tempOutputFile,
			EvaluationResult result) {
		if (result.getValue() != EvaluationResult.Unknown_Error)
			return result;

		try {
			BufferedReader Test_Read = new BufferedReader(new FileReader(tempOutputFile));
			BufferedReader STD_Read = new BufferedReader(new FileReader(std.getTest_Out()));
			String STD, player;

			while ((STD = STD_Read.readLine()) != null) {
				player = Test_Read.readLine();

				if (player != null) {
					if (!JudgeTools.compareAns(STD, player, std.getJudgeMod())) {
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
	 * Special Judge
	 * 
	 * @param std
	 *            测试点信息
	 * @param SPJ_File
	 *            SPJ文件目录
	 * @param tempOutputFile
	 *            临时输出文件目录
	 * @param result
	 *            运行时得到的测评结果
	 * @param Score_File
	 *            最终得分目录
	 * @param Error_File
	 *            错误报告目录
	 * @return 答案比较结果
	 */
	public static EvaluationResult specialResultComparison(TestPoint std, String SPJ_File, String tempOutputFile,
			EvaluationResult result, String Score_File, String Error_File) {
		try {
			Process process = Runtime.getRuntime()
					.exec("\"" + SPJ_File + "\" \"" + std.getTest_In() + "\" \"" + tempOutputFile + "\" \""
							+ std.getTest_Out() + "\" \"" + std.getScore() + "\" \"" + Score_File + "\" \"" + Error_File
							+ "\" ");

			long begin = System.currentTimeMillis();
			new Thread() {
				@Override
				public void run() {
					try {
						while (process.isAlive() && System.currentTimeMillis() - begin <= 5000)
							Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result.SetValue(EvaluationResult.System_Error);
						return;
					}
				}
			}.start();

			String temp = null;
			String Error = "";

			BufferedReader score_Reader = new BufferedReader(new FileReader(Score_File));
			BufferedReader error_Reader = new BufferedReader(new FileReader(Error_File));

			while ((temp = error_Reader.readLine()) != null)
				Error += temp + "\n";

			result.setCustomVerifier(true, Error.equals("") ? null : Error, Long.parseLong(score_Reader.readLine()));

			error_Reader.close();
			score_Reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过给定的编译命令编译
	 * 
	 * @param cmd
	 *            编译语句
	 * @return 编译信息
	 * @throws IOException
	 *             编译错误
	 * @throws InterruptedException
	 *             系统异常
	 */
	public static String compile(String cmd) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(cmd);

		new Thread() {
			@Override
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

		LineNumberReader input = new LineNumberReader(new InputStreamReader(process.getErrorStream()));

		String line, res = "";

		while ((line = input.readLine()) != null)
			res += line + '\n';

		input = new LineNumberReader(new InputStreamReader(process.getInputStream()));

		while ((line = input.readLine()) != null)
			res += line + '\n';

		return res.equals("") ? (process.exitValue() == 0 ? null : "编译超时") : res;
	}

	/**
	 * 通过给定运行命令和测试点信息传统运行程序
	 * 
	 * @param std
	 *            测试点信息
	 * @param cmd
	 *            运行命令
	 * @return 运行结果
	 */
	public static EvaluationResult simply_Runner(TestPoint std, String cmd) {
		EvaluationResult ret = new EvaluationResult();

		try {

			String[] cmds = new String[] { "cmd.exe", "/c", cmd };

			Process process = Runtime.getRuntime().exec(cmds);

			long begin = System.currentTimeMillis();

			new Thread() {
				@Override
				public void run() {
					long memory;

					while (System.currentTimeMillis() - begin <= std.getTime_Limit() && process.isAlive()) {
						try {
							memory = SystemTools.getMemByPID(process.pid());

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
	 * 通过给定命令运行选手程序和交互器，并不进行答案比较
	 * 
	 * @param std
	 *            测试点信息
	 * @param player_cmd
	 *            选手程序运行命令
	 * @param interact_cmd
	 *            交互器运行命令
	 * @return 运行结果，不包含答案比较
	 */
	public static EvaluationResult interact_Runner(TestPoint std, String player_cmd, String interact_cmd) {
		EvaluationResult ret = new EvaluationResult();

		try {
			Process player = Runtime.getRuntime().exec(player_cmd);

			if (std.getTest_In() != null) {
				BufferedReader file_reader = new BufferedReader(new FileReader(std.getTest_In()));

				String temp = null;

				while ((temp = file_reader.readLine()) != null) {
					player.getOutputStream().write((temp + "\n").getBytes());
					player.getOutputStream().flush();
				}

				file_reader.close();
			}

			long begin = System.currentTimeMillis();
			Process inte = Runtime.getRuntime().exec(interact_cmd);

			new Thread() {
				@Override
				public void run() {
					try {
						String temp = null;
						BufferedReader read = new BufferedReader(new InputStreamReader(player.getInputStream()));

						while (player.isAlive() && inte.isAlive() && (temp = read.readLine()) != null) {
							inte.getOutputStream().write((temp + "\n").getBytes());
							inte.getOutputStream().flush();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setTimeConsum(System.currentTimeMillis() - begin);
						ret.setCustomVerifier(false, null, 0);

						return;
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					try {
						String temp = null;
						BufferedReader read = new BufferedReader(new InputStreamReader(inte.getInputStream()));

						while (player.isAlive() && inte.isAlive() && (temp = read.readLine()) != null) {
							player.getOutputStream().write((temp + "\n").getBytes());
							player.getOutputStream().flush();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setTimeConsum(System.currentTimeMillis() - begin);
						ret.setCustomVerifier(false, null, 0);

						return;
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					try {
						String temp = null;
						BufferedReader read = new BufferedReader(new InputStreamReader(inte.getErrorStream()));

						while (inte.isAlive() && (temp = read.readLine()) != null) {
							if (temp.trim().equals("System_Shutdown"))
								player.destroy();
						}

						if (player.isAlive())
							player.destroy();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setTimeConsum(System.currentTimeMillis() - begin);
						ret.setCustomVerifier(false, null, 0);

						return;
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					long memory;

					while (System.currentTimeMillis() - begin <= std.getTime_Limit() && player.isAlive()
							&& inte.isAlive()) {
						try {
							memory = SystemTools.getMemByPID(player.pid());

							if (memory > ret.getMaxMemory())
								ret.setMaxMemory(memory);

							if (memory > std.getMemory_Limit() * 1024 * 1024) {
								player.destroy();

								ret.SetValue(EvaluationResult.Memory_Exceeded);
								ret.setTimeConsum(System.currentTimeMillis() - begin);
								ret.setCustomVerifier(false, null, 0);

								return;
							}

							Thread.sleep(10);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							ret.SetValue(EvaluationResult.System_Error);
							ret.setTimeConsum(System.currentTimeMillis() - begin);
							ret.setCustomVerifier(false, null, 0);

							return;
						}
					}

					if (player.isAlive()) {
						ret.SetValue(EvaluationResult.Time_Exceeded);
						ret.setTimeConsum(System.currentTimeMillis() - begin);

						player.destroy();
					}

					try {
						while (System.currentTimeMillis() - begin <= std.getTime_Limit() + 5000 && inte.isAlive())
							Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setTimeConsum(System.currentTimeMillis() - begin);
						ret.setCustomVerifier(false, null, 0);

						return;
					}

					if (inte.isAlive()) {
						inte.destroy();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setCustomVerifier(false, null, 0);

						return;
					}
				}
			}.start();

			player.waitFor();

			ret.setTimeConsum(System.currentTimeMillis() - begin);

			inte.waitFor();
			return ret;
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ret.setCustomVerifier(false, null, 0);
		}

		return ret;
	}
}
