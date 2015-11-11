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

@Singleton
@Lock(LockType.READ)
public class RSSFeedScheduler {
	@Resource
	private TimerService timerService;

	@Resource
	private BeanManager beanManager;

	public void scheduleEvent(ScheduleExpression schedule, Object event, Annotation... qualifiers) {

		timerService.createCalendarTimer(schedule, new TimerConfig(new EventConfig(event, qualifiers), false));
	}

	@Timeout
	private void timeout(Timer timer) {
		final EventConfig config = (EventConfig) timer.getInfo();
		System.out.println("*******FIRING EVENT!!!");

		beanManager.fireEvent(config.getEvent(), config.getQualifiers());
	}

	// Doesn't actually need to be serializable, just has to implement it
	private final class EventConfig implements Serializable {

		private static final long serialVersionUID = 8867847902507621913L;
		private final Object event;
		private final Annotation[] qualifiers;

		private EventConfig(Object event, Annotation[] qualifiers) {
			this.event = event;
			this.qualifiers = qualifiers;
		}

		public Object getEvent() {
			return event;
		}

		public Annotation[] getQualifiers() {
			return qualifiers;
		}
	}

}
