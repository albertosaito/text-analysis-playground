package io.vuh.text.rss.manager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import io.vuh.text.rss.model.RSSFeedList;
import io.vuh.text.rss.scheduler.RSSFeedScheduler;

/**
 * @author asaito
 *
 */
@Startup
@Singleton
public class RSSFeedLoadWorker {
	@EJB
	private RSSFeedScheduler scheduler;

	/**
	 * Init Scheduler
	 */
	@PostConstruct
	public void init() {
		// every five minutes
		final ScheduleExpression schedule = new ScheduleExpression().hour("*").minute("*/1");
		scheduler.scheduleEvent(schedule, new RSSFeedList());
	}

}
