package codeJudger.pascalJudger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import codeJudger.EvaluationResult;
import codeJudger.Judger;
import codeJudger.TestPoint;

/**
 * Pascal测评
 * 
 * @author STR
 *
 */
public class PascalJudger extends Judger {

	@Override
	public String compile(String compile_File, String fileName, String outPutFile, String... supplements)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String cmds = "\"" + compile_File + "\" \"" + fileName + "\" -o\"" + outPutFile + "\" ";

		for (int i = 0; i < supplements.length; i++)
			cmds += supplements[i] + " ";

		return Judger.compile(cmds);
	}

	@Override
	public EvaluationResult simply_Runner(TestPoint std, String file, String tempOutputFile) {
		// TODO Auto-generated method stub
		String cmd = "\"" + file + "\"";

		if (std.getTest_In() != null && !std.getTest_In().equals(""))
			cmd += " < \"" + std.getTest_In() + "\"";

		if (tempOutputFile != null && !tempOutputFile.equals(""))
			cmd += " > \"" + tempOutputFile + "\" ";

		return Judger.simply_Runner(std, cmd);
	}

	@Override
	public EvaluationResult interact_Runner(TestPoint std, String file, String inte_File, String Score_File,
			String Error_File) {
		// TODO Auto-generated method stub
		EvaluationResult ret = new EvaluationResult();

		try {
			ret = Judger.interact_Runner(std, "\"" + file + "\"",
					"\"" + inte_File + "\" \"" + std.getTest_In() + "\" \"" + std.getCustomVerifier_In() + "\" \""
							+ std.getScore() + "\" \"" + Score_File + "\" \"" + Error_File + "\" ");

			if (ret.getValue() != EvaluationResult.Unknown_Error)
				return ret;

			if (!new File(Score_File).exists() || !new File(Error_File).exists()) {
				ret.SetValue(EvaluationResult.System_Error);
				ret.setCustomVerifier(false, null, 0);

				return ret;
			}

			BufferedReader score_Reader = new BufferedReader(new FileReader(Score_File));
			BufferedReader error_Reader = new BufferedReader(new FileReader(Error_File));

			String error = "";
			String temp = null;

			while ((temp = error_Reader.readLine()) != null)
				error += temp + "\n";

			temp = score_Reader.readLine();

			if (temp == null) {
				ret.setCustomVerifier(false, null, 0);
				ret.SetValue(EvaluationResult.System_Error);

				error_Reader.close();
				score_Reader.close();

				return ret;
			}

			ret.setCustomVerifier(true, error.equals("") ? null : error, Long.parseLong(score_Reader.readLine()));

			error_Reader.close();
			score_Reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ret.setCustomVerifier(false, null, 0);
		}

		return ret;
	}

}
