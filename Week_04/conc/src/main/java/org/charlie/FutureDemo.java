package org.charlie;

import java.util.concurrent.*;

/**
 * 多线程获取返回值
 *
 * @author Charlie
 * @date 2021/2/1
 */
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        getResultByThreadPool();
        getResultByFutureTask();
        getResultByCompletableFuture();
        getResultByCompletionService();
    }

    /**
     * 通过线程池获取异步结果
     */
    public static void getResultByThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> f = executor.submit(() -> "hello world");
        System.out.println(f.get());
        executor.shutdown();
    }

    /**
     * {@link FutureTask} 获取异步结果
     */
    public static void getResultByFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> f = new FutureTask<>(() -> "hello world");
        new Thread(f).start();
        System.out.println(f.get());
    }

    /**
     * {@link CompletableFuture} 获取异步结果
     */
    public static void getResultByCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "hello world");
        System.out.println(f.get());
    }

    /**
     * {@link CompletionService} 获取异步结果
     */
    public static void getResultByCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        ExecutorCompletionService<String> cs = new ExecutorCompletionService<>(executor);
        cs.submit(() -> "hello world");
        String result = cs.take().get();
        System.out.println(result);
        executor.shutdown();
    }

}
