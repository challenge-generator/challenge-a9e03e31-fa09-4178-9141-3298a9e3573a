package com.trading.events.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Bean(name = "eventProcessingExecutor", destroyMethod = "shutdown")
    public ExecutorService eventProcessingExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        int maxPoolSize = corePoolSize * 4;
        long keepAliveTime = 60L;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "event-processor-" + counter.getAndIncrement());
                    thread.setDaemon(true);
                    thread.setPriority(Thread.NORM_PRIORITY);
                    return thread;
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
        );

        executor.allowCoreThreadTimeOut(true);
        log.info("EventProcessingExecutor initialized with core={}, max={}", corePoolSize, maxPoolSize);
        return executor;
    }

    @Bean(name = "ioBoundExecutor", destroyMethod = "shutdown")
    public ExecutorService ioBoundExecutor() {
        int corePoolSize = 50;
        int maxPoolSize = 200;
        long keepAliveTime = 30L;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5000),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "io-worker-" + counter.getAndIncrement());
                    thread.setDaemon(true);
                    return thread;
                }
            },
            new ThreadPoolExecutor.AbortPolicy()
        );

        log.info("IO-bound executor initialized with core={}, max={}", corePoolSize, maxPoolSize);
        return executor;
    }

    @Bean(name = "scheduledExecutor", destroyMethod = "shutdown")
    public ScheduledExecutorService scheduledExecutor() {
        int poolSize = 4;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(poolSize, r -> {
            Thread thread = new Thread(r, "scheduled-worker");
            thread.setDaemon(true);
            return thread;
        });

        log.info("Scheduled executor initialized with pool size={}", poolSize);
        return executor;
    }

    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();

        log.info("Async task executor initialized");
        return executor;
    }

    @Bean(name = "parallelProcessingExecutor")
    public ForkJoinPool parallelProcessingExecutor() {
        int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(
            parallelism,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true
        );
        log.info("ForkJoinPool initialized with parallelism={}", parallelism);
        return pool;
    }

    @ConfigurationProperties(prefix = "thread-pool")
    public static class ThreadPoolProperties {
        private int eventCoreSize = 8;
        private int eventMaxSize = 32;
        private int ioCoreSize = 50;
        private int ioMaxSize = 200;
        private int queueCapacity = 1000;
        private long keepAliveSeconds = 60;

        public int getEventCoreSize() { return eventCoreSize; }
        public void setEventCoreSize(int eventCoreSize) { this.eventCoreSize = eventCoreSize; }
        public int getEventMaxSize() { return eventMaxSize; }
        public void setEventMaxSize(int eventMaxSize) { this.eventMaxSize = eventMaxSize; }
        public int getIoCoreSize() { return ioCoreSize; }
        public void setIoCoreSize(int ioCoreSize) { this.ioCoreSize = ioCoreSize; }
        public int getIoMaxSize() { return ioMaxSize; }
        public void setIoMaxSize(int ioMaxSize) { this.ioMaxSize = ioMaxSize; }
        public int getQueueCapacity() { return queueCapacity; }
        public void setQueueCapacity(int queueCapacity) { this.queueCapacity = queueCapacity; }
        public long getKeepAliveSeconds() { return keepAliveSeconds; }
        public void setKeepAliveSeconds(long keepAliveSeconds) { this.keepAliveSeconds = keepAliveSeconds; }
    }

    @Bean
    public ThreadPoolProperties threadPoolProperties() {
        return new ThreadPoolProperties();
    }
}