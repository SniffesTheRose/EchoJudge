package judgingSystem;

import java.io.IOException;

/**
 * 集成系统工具
 * 
 * @author STR
 *
 */
public class SystemTools {
	/**
	 * 当前环境：0 未获得 1 Windows 2 Linux
	 */
	private static int SystemType;

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
	 * 强制结束process以及由其启动的子进程
	 * 
	 * @param process
	 *            结束对象
	 * @throws IOException
	 *             结束时发生异常
	 */
	public static void taskKill(Process process) throws IOException {
		SystemTools.setSystemFlag();

		if (SystemType == 1)
			Runtime.getRuntime().exec("taskkill -t -f -pid " + process.pid());
		else if (SystemType == 2)
			Runtime.getRuntime().exec("kill -TERM " + process.pid());
	}

	/**
	 * 设置当前环境
	 */
	private static void setSystemFlag() {
		if (SystemType == 0) {
			String os = System.getProperty("os.name");
			SystemType = os.toLowerCase().startsWith("win") ? 1 : 2;
		}
	}

	/**
	 * 获取当前系统的命令行名称
	 * 
	 * @return 命令行名称
	 */
	public static String getShell() {
		SystemTools.setSystemFlag();
		return SystemType == 1 ? "cmd" : "sh";
	}
}
