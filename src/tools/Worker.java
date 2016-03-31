package tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Worker für das Ausführen von Anweisungen auf anderen Threads
 * @author Robert *
 */
public class Worker
{
	private static ExecutorService executorService;

	static
	{
		initWorker();
	}

	private static void initWorker()
	{
		int nThreads = Runtime.getRuntime().availableProcessors();
		executorService = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
		{

			@Override
			public <T> Future<T> submit(final Callable<T> task)
			{
				Callable<T> wrappedTask = () -> {
					try
					{
						return task.call();
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw e;
					}
				};

				return super.submit(wrappedTask);
			}

			@Override
			public <T> Future<T> submit(Runnable task, T result)
			{
				Runnable wrapperTask = () -> {
					try
					{
						task.run();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				};

				return super.submit(wrapperTask, result);
			}
		};
	}

	/**
	 * führt die Anweisungen in anderen Threads aus
	 * @param runnable
	 * @return
	 */
	public static Future<Void> runLater(Runnable runnable)
	{
		if(executorService == null)
		{
			initWorker();
		}
		Future<Void> future = executorService.submit(runnable, null);
		return future;
	}

	/**
	 * beendet den Worker
	 */
	public static void shutdown()
	{
		executorService.shutdown();
		executorService = null;
	}
}