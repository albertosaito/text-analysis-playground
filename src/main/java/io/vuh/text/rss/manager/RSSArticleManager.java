package io.vuh.text.rss.manager;

import io.vuh.text.rss.model.RSSFeedList;
import io.vuh.text.rss.resource.transport.LoadRSSResponse;

/**
 * @author nobuji.saito
 *
 */
public interface RSSArticleManager {

    /**
     * @param url
     * @return {@link LoadRSSResponse}
     */
    LoadRSSResponse loadRSSFeed(String url);

	void loadScheduledRSSFeeds(RSSFeedList list);

}
