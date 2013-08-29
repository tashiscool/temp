package com.pearson.ed.thread.impl;
import java.util.concurrent.ThreadFactory;
import java.lang.Thread;
import java.lang.Runnable;

public class SimpleThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	}
}
