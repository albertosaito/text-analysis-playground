package io.vuh.text.rss.manager;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import io.vuh.text.rss.model.RSSFeedList;

/**
 * @author asaito
 *
 */
@Startup
@Singleton
public class RSSFeedLoadWorker {
    // @EJB
    // private RSSFeedScheduler scheduler;
    
    @Inject
    private Event<RSSFeedList> triggerRSSFeedListEvent;

    /**
     * Init Scheduler
     */
    @PostConstruct
    public void init() {
	// every five minutes
	//final ScheduleExpression schedule = new ScheduleExpression().hour("*").minute("*/1");
	//scheduler.scheduleEvent(schedule, new RSSFeedList());
	triggerRSSFeedListEvent.fire(new RSSFeedList());
    }

}
