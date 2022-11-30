package api.statistic;

import models.modelsHibernate.message.Comment;
import models.modelsHibernate.place.Country;

import java.util.Set;

public interface StatisticAPI {
    void getTagClassHierarchy();

    Set<Comment> getPopularComments(Integer k);

    Country getCountryWithMostTraffic();

    public Country getCountryWithMostTrafficJAVA();
}
