package com.ljp.hellogithub.activity.threadpool;

import com.ljp.hellogithub.activity.threadpool.db.DownloadEntity;
import com.ljp.hellogithub.activity.threadpool.db.DownloadHelper;
import com.ljp.hellogithub.activity.threadpool.file.FileStorageManager;
import com.ljp.hellogithub.activity.threadpool.http.DownloadCallback;
import com.ljp.hellogithub.activity.threadpool.http.HttpManager;
import com.ljp.hellogithub.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/18.
 */

public class DownloadManager {

    public final static int MAX_THREAD = 2;
    public final static int LOCAL_PROGRESS_SIZE = 1;

//    private static final DownloadManager sManager = new DownloadManager();
    private static DownloadManager sManager;

    private static ExecutorService sLocalProgressPool = Executors.newFixedThreadPool(LOCAL_PROGRESS_SIZE);

    private HashSet<DownloadTask> mHashSet = new HashSet<>();
    private List<DownloadEntity> mCache;

    private long mLength;

    /*当线程池池创建的线程数量大于 corePoolSize 后，新来的任务将会加入到堵塞队列（workQueue）中等待有空闲线程来执行。workQueue的类型为BlockingQueue，
    通常可以取下面三种类型：
    ArrayBlockingQueue：基于数组的FIFO队列，是有界的，创建时必须指定大小
    LinkedBlockingQueue： 基于链表的FIFO队列，是无界的，默认大小是 Integer.MAX_VALUE
    synchronousQueue:一个比较特殊的队列，虽然它是无界的，但它不会保存任务，每一个新增任务的线程必须等待另一个线程取出任务，也可以把它看成容量为0的队列*/

     //(int corePoolSize核心线程, int maximumPoolSize最大线程,
     // long keepAliveTime整个线程存活时间, TimeUnit unit,//存活时间单位
     // BlockingQueue<Runnable> workQueue//队列,
     // ThreadFactory threadFactory)
    private static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {

        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
            return thread;
        }
    });

    public static DownloadManager getInstance() {
        if (sManager == null) {
            synchronized (DownloadManager.class) {
                if (sManager == null) {
                    sManager = new DownloadManager();
                }
            }
        }
        return sManager;
    }

    public static class Holder {
        private static DownloadManager sManager = new DownloadManager();
        public static DownloadManager getInstance() {
            return sManager;
        }
    }

    public void download(final String url, final DownloadCallback callback) {
        final DownloadTask task = new DownloadTask(url, callback);
        if (mHashSet.contains(task)) {
            callback.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "任务已经执行了");
            return;
        }
        mHashSet.add(task);

        mCache = DownloadHelper.getInstance().getAll(url);
        if (mCache == null || mCache.size() == 0) {
            HttpManager.getInstance().asyncRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    finish(task);
                    Logger.debug("nate", "onFailure ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful() && callback != null) {
                        callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
                        return;
                    }

                    mLength = response.body().contentLength();
                    if (mLength == -1) {
                        callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "content length -1");
                        return;
                    }
                    processDownload(url, mLength, callback, mCache);
                    finish(task);

                }
            });

        } else {
            // TODO: 16/9/3 处理已经下载过的数据
            for (int i = 0; i < mCache.size(); i++) {
                DownloadEntity entity = mCache.get(i);
                if (i == mCache.size() - 1) {
                    mLength = entity.getEnd_position() + 1;
                }
                long startSize = entity.getStart_position() + entity.getProgress_position();
                long endSize = entity.getEnd_position();
                sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
            }
        }

        sLocalProgressPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        File file = FileStorageManager.getInstance().getFileByName(url);
                        long fileSize = file.length();
                        int progress = (int) (fileSize * 100.0 / mLength);
                        if (progress >= 100) {
                            callback.progress(progress);
                            return;
                        }
                        callback.progress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void processDownload(String url, long length, DownloadCallback callback, List<DownloadEntity> cache) {
        // 100   2  50  0-49  50-99
        long threadDownloadSize = length / MAX_THREAD;

        if (cache == null && cache.size() == 0) {
            mCache = new ArrayList<>();
        }

        for (int i = 0; i < MAX_THREAD; i++) {
            DownloadEntity entity = new DownloadEntity();
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (endSize == MAX_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }

            entity.setDownload_url(url);
            entity.setStart_position(startSize);
            entity.setEnd_position(endSize);
            entity.setThread_id(i + 1);
            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback, entity));
        }
    }

    private void finish(DownloadTask task) {
        mHashSet.remove(task);
    }

    public void init(DownloadConfig config) {
        sThreadPool = new ThreadPoolExecutor(config.getCoreThreadSize(), config.getMaxThreadSize(), 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
            private AtomicInteger mInteger = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
                return thread;
            }
        });

        sLocalProgressPool = Executors.newFixedThreadPool(config.getLocalProgressThreadSize());

    }
}
