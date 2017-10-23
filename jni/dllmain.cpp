/* Replace "dll.h" with the name of your header */
#include "dll.h"
#include "judgingSystem_SystemTools.h"
#include <windows.h>
#include <TlHelp32.h>
#include <Psapi.h>

#pragma comment(lib,"Psapi.lib")

JNIEXPORT jlong JNICALL Java_judgingSystem_SystemTools_getMemByPID (JNIEnv * a, jclass b, jlong pid) {
	PROCESS_MEMORY_COUNTERS_EX procCnt;
	GetProcessMemoryInfo(OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, pid), (PPROCESS_MEMORY_COUNTERS) & procCnt, sizeof(procCnt));
	return 1;
}


BOOL WINAPI DllMain(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved)
{
	switch(fdwReason)
	{
		case DLL_PROCESS_ATTACH:
		{
			break;
		}
		case DLL_PROCESS_DETACH:
		{
			break;
		}
		case DLL_THREAD_ATTACH:
		{
			break;
		}
		case DLL_THREAD_DETACH:
		{
			break;
		}
	}
	
	/* Return TRUE on success, FALSE on failure */
	return TRUE;
}
