package io.vuh.text.rss.scheduler;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.spi.BeanManager;

import io.vuh.text.rss.model.RSSFeedList;

/**
 * @author asaito
 *
 */
@Singleton
@Lock(LockType.READ)
public class RSSFeedScheduler {
	/**
	 *  {@link TimerService} for {@link RSSFeedScheduler}
	 */
	@Resource
	private TimerService timerService;

	/**
	 * {@link BeanManager} for {@link RSSFeedScheduler}
	 */
	@Resource
	private BeanManager beanManager;

	/**
	 * @param schedule
	 * @param event
	 * @param qualifiers
	 */
	public void scheduleEvent(ScheduleExpression schedule, RSSFeedList event, Annotation... qualifiers) {

		timerService.createCalendarTimer(schedule, new TimerConfig(new EventConfig(event, qualifiers), false));
	}

	/**
	 * @param timer
	 */
	@Timeout
	private void timeout(Timer timer) {
		final EventConfig config = (EventConfig) timer.getInfo();
		System.out.println("*******FIRING EVENT!!!");

		beanManager.fireEvent(config.getEvent(), config.getQualifiers());
	}

	// Doesn't actually need to be serializable, just has to implement it
	/**
	 * @author asaito
	 *
	 */
	private final class EventConfig implements Serializable {

		private static final long serialVersionUID = 8867847902507621913L;
		private final RSSFeedList event;
		private final Annotation[] qualifiers;

		private EventConfig(RSSFeedList event, Annotation[] qualifiers) {
			this.event = event;
			this.qualifiers = qualifiers;
		}

		public RSSFeedList getEvent() {
			return event;
		}

		public Annotation[] getQualifiers() {
			return qualifiers;
		}
	}

}
