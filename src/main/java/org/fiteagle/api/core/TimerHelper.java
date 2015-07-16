package org.fiteagle.api.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.TimeLimitExceededException;

public class TimerHelper extends TimerTask {
    private static final Logger LOGGER = Logger.getLogger(TimerHelper.class.getName());

	
	Callable<Void> task;
	private Timer timer;
	long delay;

	public TimerHelper(Callable<Void> task) {
		timer = new Timer();
		delay = 500;
		this.task = task;
		run();
	}

	@Override
	public void run() {

		if (delay < 3600000) {

			try {
				task.call();
				timer.cancel();
			} catch (Exception e) {
				delay = delay + delay;
				timer.schedule(new TimerHelper(task), delay);
			}
		} else {
			try {
				timer.cancel();
				throw new TimeLimitExceededException();
			} catch (TimeLimitExceededException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE,"Tried to write to TripletStore several times, but could not succeed");
				e.printStackTrace();
			}
		}
	}

}
