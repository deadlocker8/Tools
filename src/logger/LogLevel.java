package logger;

public enum LogLevel
{	
	OFF(0),
	INFO(1),
	WARNING(2),
	ERROR(3),
	NORMAL(4),
	DEBUG(5),
	ALL(6);
	
	private int level;

	private LogLevel(int level)
	{
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}
}