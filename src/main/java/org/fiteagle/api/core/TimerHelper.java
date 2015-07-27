package org.fiteagle.api.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;

import org.apache.jena.atlas.web.HttpException;
import org.fiteagle.api.tripletStoreAccessor.TripletStoreAccessor;
import org.fiteagle.api.tripletStoreAccessor.TripletStoreAccessor.ResourceRepositoryException;

import com.hp.hpl.jena.shared.NotFoundException;



@Singleton
@Startup
public class TimerHelper{
    private static final Logger LOGGER = Logger.getLogger(TimerHelper.class.getName());

	private Callable<Void> task;
	long delay;
	private int failureCounter = 0;
	private Timer timer;
	private Random random = new Random();
	
	@Resource
	private ManagedThreadFactory threadFactory;
	@Resource
	private ManagedExecutorService executorService;
	@Resource
	private TimerService timerService;
	
	Map list = new HashMap<String,Callable<Void>>();
	ThreadGroup group = new ThreadGroup("timer");

	public TimerHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public TimerHelper(Callable<Void> task) {
		delay = 500;
	}
	
	public void setNewTimer(Callable<Void> task){
	this.task = task;
	timerService.createIntervalTimer(0, 5000, new TimerConfig());
}

	@Timeout
	public void timerMethod(Timer timer) throws Exception {
		
		if(failureCounter <10){
			
			try{
				Future<Void> future = executorService.submit(task);
				future.get();		
				LOGGER.log(Level.INFO, "Added something to RDF-Database");
				failureCounter =0;
				timer.cancel();
				}catch(Exception e){
					failureCounter++;
					LOGGER.log(Level.WARNING, "Errored while adding something to Database - will try again " + (10 -failureCounter) +" times");
				}

		}else{
			timer.cancel();
			LOGGER.log(Level.SEVERE, "Tried to add something to Database several times, but failed. Please check the OpenRDF-Database");

		}
		

	}

}