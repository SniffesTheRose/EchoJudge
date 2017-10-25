package codeJudger;

/**
 * 评测工具
 * 
 * @author STR
 *
 */
public class JudgeTools {

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
	public static boolean compareAns(String std, String player, int mod) {
		switch (mod) {
		case TestPoint.Byte_By_Byte:
			return std.equals(player);
		case TestPoint.Ignore_Space:
			return JudgeTools.trimeEndSpace(std).equals(JudgeTools.trimeEndSpace(player));
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
	public static String trimeEndSpace(String str) {
		for (int i = str.length() - 1; i >= 0; i--)
			if (str.substring(i) == " ")
				str = str.substring(0, i);

		return str;
	}

}
