package com.demo.panguso.mvp_mode.component.db;

import android.util.Log;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.ApiConstants;
import com.demo.panguso.mvp_mode.utils.SharedPreferencesUtil;

import java.util.Arrays;
import java.util.List;

import de.greenrobot.dao.query.Query;
import greendao.NewsChannelTable;
import greendao.NewsChannelTableDao;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
public class NewsChannelTableManager {
    /**
     * 首次打开程序初始化
     */
    public static void initDB() {
        Log.e("TAG",".P.P.P.P");
        if (!SharedPreferencesUtil.getIsBoolean()) {
            NewsChannelTableDao dao = App.getNewsChannelTableDao();
            List<String> channelName = Arrays.asList(App.getAppContext().getResources()
                    .getStringArray(R.array.news_channel_name));

            List<String> channelId = Arrays.asList(App.getAppContext().getResources()
                    .getStringArray(R.array.news_channel_id));
            for (int i = 0; i < channelName.size(); i++) {
                NewsChannelTable entity = new NewsChannelTable(channelName.get(i), channelId.get(i),
                        ApiConstants.getType(channelId.get(i)), i <= 2, i, i == 0);
                dao.insert(entity);
            }
            SharedPreferencesUtil.setIsBoolean(true);
        }
    }

    public static List<NewsChannelTable> loadNewsChannels() {
        Query<NewsChannelTable> build = App.getNewsChannelTableDao()
                .queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build();
        return build.list();
    }
}
