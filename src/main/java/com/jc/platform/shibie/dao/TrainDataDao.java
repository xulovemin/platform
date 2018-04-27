package com.jc.platform.shibie.dao;

import com.jc.platform.shibie.domain.TrainData;

import java.util.List;

public interface TrainDataDao {

    List<TrainData> getThousand();

    int update(TrainData trainData);

}
