package codeJudger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
}
