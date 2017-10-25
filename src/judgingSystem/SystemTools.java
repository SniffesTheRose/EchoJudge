package judgingSystem;

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

}
