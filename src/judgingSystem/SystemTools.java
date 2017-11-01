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
		Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", "taskkill -t -f -pid " + process.pid() });
	}

}
