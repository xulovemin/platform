package com.jc.platform.shibie.service;

import com.jc.platform.core.service.BaseService;
import com.jc.platform.shibie.dao.TrainDataDao;
import com.jc.platform.shibie.domain.TrainData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrainDataService extends BaseService<TrainDataDao, TrainData> {

    public List<TrainData> getThousand() {
        return dao.getThousand();
    }

}
