package nativeWindows;

import java.lang.management.ManagementFactory;

public class PID
{
	public static String getCurrentPID()
	{
		return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}
}