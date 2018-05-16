package com.jc.platform.core.dao;

import java.util.List;

/**
 * DAO支持类实现
 */
public interface BaseDao<T> {

    /**
     * 保存方法
     *
     * @param o 实体类
     * @return 新增的数目数
     * @author
     */
    public Integer save(T o);

    /**
     * 批量保存方法
     *
     * @param list 实体类集合
     * @return Integer 添加的记录数
     * @author
     */
    public Integer saveList(List<T> list);

    /**
     * 修改方法
     *
     * @param o 实体类
     * @return Integer 修改的记录数
     * @author
     */
    public Integer update(T o);

    /**
     * 逻辑删除
     *
     * @param o 实体类
     * @return Integer 删除的记录数
     * @author
     */
    public Integer delete(T o);

    /**
     * 查询所有记录方法
     *
     * @return List 查询的所有记录
     * @author
     * @version 2014-03-24
     */
    public List<T> findList();

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id);

    /**
     * 查询单条记录方法
     *
     * @param o 实体类
     * @return T 查询的记录
     * @author
     */
    public T get(T o);

}