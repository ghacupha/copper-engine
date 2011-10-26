/*
 * Copyright 2002-2011 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.copper.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import de.scoopgmbh.copper.ProcessingEngine;
import de.scoopgmbh.copper.Workflow;

public abstract class PriorityProcessorPool implements ProcessorPool {

	private static final Logger logger = Logger.getLogger(PriorityProcessorPool.class);

	protected final Queue<Workflow<?>> queue = createQueue();
	private final List<Processor> workerThreads = new ArrayList<Processor>();

	private ProcessingEngine engine = null;
	private String id = null;
	private int numberOfThreads = 2;
	private int threadPriority = Thread.NORM_PRIORITY;
	
	private boolean started = false;
	private boolean shutdown = false;
	
	public PriorityProcessorPool() {
	}
	
	public PriorityProcessorPool(String id, int numberOfThreads) {
		super();
		this.id = id;
		this.numberOfThreads = numberOfThreads;
	}
	
	protected Queue<Workflow<?>> createQueue() {
		return new WfPriorityQueue();
	}

	@Override
	public void setEngine(ProcessingEngine engine) {
		if (this.engine != null) {
			throw new IllegalArgumentException("engine is already set");
		}
		this.engine = engine;
	}
	
	public void setId(String id) {
		if (id != null) {
			throw new IllegalArgumentException("id is already set to "+this.id);
		}
		this.id = id;
	}
	
	public synchronized void setNumberOfThreads(int numberOfThreads) {
		if (numberOfThreads <= 0 || numberOfThreads >= 2048) throw new IllegalArgumentException();
		if (this.numberOfThreads != numberOfThreads) {
			logger.info("ProcessorPool "+id+": Setting new number of processor threads");
			this.numberOfThreads = numberOfThreads;
			if (started) {
				updateThreads();
			}
		}
	}
	
	private void updateThreads() {
		if (numberOfThreads == workerThreads.size())
			return;
		while (numberOfThreads < workerThreads.size()) {
			Processor p = workerThreads.remove(workerThreads.size()-1);
			p.shutdown();
			try {
				p.join(5000);
			} 
			catch (InterruptedException e) {
				// ignore
			}
		}
		while (numberOfThreads > workerThreads.size()) {
			Processor p = newProcessor(id+"#"+workerThreads.size(), queue, threadPriority, engine);
			p.start();
			workerThreads.add(p);
		}
	}
	
	protected abstract Processor newProcessor(String id, Queue<Workflow<?>> queue, int threadPrioriry, ProcessingEngine engine);

	public int getNumberOfThreads() {
		return numberOfThreads;
	}
	
	public synchronized void setThreadPriority(int threadPriority) {
		if (threadPriority != this.threadPriority) {
			logger.info("ProcessorPool "+id+": Setting new thread priority to "+threadPriority);
			this.threadPriority = threadPriority;
			for (Thread t : workerThreads) {
				t.setPriority(threadPriority);
			}
		}
	}
	
	public int getThreadPriority() {
		return threadPriority;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public synchronized void shutdown() {
		if (shutdown)
			return;
		
		logger.info("ProcessorPool "+id+": Shutting down");

		shutdown = true;
		synchronized (queue) {
			queue.notifyAll();
		}
		
		for (Processor p : workerThreads) {
			p.shutdown();
		}
	}
	
	public synchronized void startup() {
		if (id == null) throw new NullPointerException();
		if (engine == null) throw new NullPointerException();
		
		if (started)
			return;
		
		logger.info("ProcessorPool "+id+": Starting up");
		
		started = true;
		updateThreads();
	}
	
	protected ProcessingEngine getEngine() {
		return engine;
	}

}