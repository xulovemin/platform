package com.jc.platform.shibie.dao;

import com.jc.platform.core.dao.BaseDao;
import com.jc.platform.shibie.domain.TrainData;

import java.util.List;

public interface TrainDataDao extends BaseDao<TrainData> {

    List<TrainData> getThousand();

}
