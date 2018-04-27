package com.jc.platform.shibie.service;

import com.jc.platform.shibie.dao.TrainDataDao;
import com.jc.platform.shibie.domain.TrainData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrainDataService {

    @Autowired
    private TrainDataDao trainDataDao;

    public List<TrainData> getThousand() {
        return trainDataDao.getThousand();
    }

    @Transactional(readOnly = false)
    public int update(TrainData trainData) {
        return trainDataDao.update(trainData);
    }
}
