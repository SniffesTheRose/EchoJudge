package codeJudger.cppJudger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import codeJudger.EvaluationResult;
import codeJudger.Judger;
import codeJudger.TestPoint;
import judgingSystem.SystemTools;

/**
 * c++评测操作
 * 
 * @author STR
 *
 */
public class CppJudger extends Judger {

	@Override
	public String compile(String compile_File, String fileName, String outPutFile)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Process process = Runtime.getRuntime()
				.exec("\"" + compile_File + "\" \"" + fileName + "\" -o \"" + outPutFile + "\"");

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

		InputStreamReader ir = new InputStreamReader(process.getErrorStream());
		LineNumberReader input = new LineNumberReader(ir);

		String line, res = "";

		while ((line = input.readLine()) != null)
			res += line + '\n';

		return res == "" ? (process.exitValue() == 0 ? null : "编译超时") + "" : res;
	}

	@Override
	public EvaluationResult simply_Runner(TestPoint std, String file, String tempOutputFile) {
		// TODO Auto-generated method stub
		EvaluationResult ret = new EvaluationResult();

		try {

			String[] cmds = new String[] { "cmd.exe", "/c",
					"\"" + file + "\" < \"" + std.getTest_In() + "\" > \"" + tempOutputFile + "\" " };

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

	@Override
	public EvaluationResult interact_Runner(TestPoint std, String file, String inte_File, String Score_File,
			String Error_File) {
		// TODO Auto-generated method stub
		EvaluationResult ret = new EvaluationResult();

		try {
			Process player = Runtime.getRuntime().exec("\"" + file + "\"");

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
			Process inte = Runtime.getRuntime().exec("\"" + inte_File + "\" \"" + std.getTest_In() + "\" \""
					+ std.getScore() + "\" \"" + Score_File + "\" \"" + Error_File + "\" ");

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
						ret.setSPJ(false, null, 0);

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
						ret.setSPJ(false, null, 0);

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
						ret.setSPJ(false, null, 0);

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
								ret.setSPJ(false, null, 0);

								return;
							}

							Thread.sleep(10);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							ret.SetValue(EvaluationResult.System_Error);
							ret.setTimeConsum(System.currentTimeMillis() - begin);
							ret.setSPJ(false, null, 0);

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
						ret.setSPJ(false, null, 0);

						return;
					}

					if (inte.isAlive()) {
						inte.destroy();

						ret.SetValue(EvaluationResult.System_Error);
						ret.setSPJ(false, null, 0);

						return;
					}
				}
			}.start();

			player.waitFor();

			ret.setTimeConsum(System.currentTimeMillis() - begin);

			inte.waitFor();

			if (ret.getValue() != EvaluationResult.Unknown_Error)
				return ret;

			if (!new File(Score_File).exists() || !new File(Error_File).exists()) {
				ret.SetValue(EvaluationResult.System_Error);
				ret.setSPJ(false, null, 0);

				return ret;
			}

			BufferedReader score_Reader = new BufferedReader(new FileReader(Score_File));
			BufferedReader error_Reader = new BufferedReader(new FileReader(Error_File));

			String error = "";
			String temp = null;

			while ((temp = error_Reader.readLine()) != null)
				error += temp + "\n";

			ret.setSPJ(true, error.equals("") ? null : error, Long.parseLong(score_Reader.readLine()));

			error_Reader.close();
			score_Reader.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			ret.setSPJ(false, null, 0);
		}

		return ret;
	}
}
