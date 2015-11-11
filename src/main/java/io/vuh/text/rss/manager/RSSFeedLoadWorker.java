package io.vuh.text.rss.manager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import io.vuh.text.rss.model.RSSFeedList;
import io.vuh.text.rss.scheduler.RSSFeedScheduler;

@Startup
@Singleton
public class RSSFeedLoadWorker {
	@EJB
	private RSSFeedScheduler scheduler;

	@PostConstruct
	public void init() {
		System.out.println("init");
		// every five minutes
		final ScheduleExpression schedule = new ScheduleExpression().hour("*").minute("*/5").second("*");
		System.out.println("schedule ready");
		scheduler.scheduleEvent(schedule, new RSSFeedList());
		System.out.println("schedule registered");
	}

}
