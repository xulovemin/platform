package com.jc.platform.core.service;

import com.jc.platform.core.dao.BaseDao;
import com.jc.platform.core.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class BaseService<D extends BaseDao<T>, T extends BaseEntity<T>> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }


    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        if (entity.isSave()) {
            dao.save(entity);
        } else {
            dao.update(entity);
        }
    }

    /**
     * 修改方法
     *
     * @param o 实体类
     * @return Integer 修改的记录数
     * @author
     */
    @Transactional(readOnly = false)
    public Integer update(T o) {
        return dao.update(o);
    }

    /**
     * 批量保存方法
     *
     * @param list 实体类集合
     * @return Integer 添加的记录数
     * @author
     */
    @Transactional(readOnly = false)
    public Integer saveList(List<T> list) {
        return dao.saveList(list);
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

}
