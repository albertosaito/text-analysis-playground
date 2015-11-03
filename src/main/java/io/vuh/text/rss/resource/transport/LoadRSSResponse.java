package io.vuh.text.rss.resource.transport;

/**
 * @author nobuji.saito
 *
 */
public class LoadRSSResponse {
	private double timeElapsed;
	private int loadedArticles;

	/**
	 * @return the loadedArticles
	 */
	public int getLoadedArticles() {
		return loadedArticles;
	}

	/**
	 * @return
	 */
	public double getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * @param loadedArticles
	 *            the loadedArticles to set
	 */
	public void setLoadedArticles(final int loadedArticles) {
		this.loadedArticles = loadedArticles;
	}

	/**
	 * @param timeElapsed
	 */
	public void setTimeElapsed(final double timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

}
