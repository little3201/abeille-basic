/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.authority.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.abeille.basic.authority.entity.PermInfo;

/**
 * 权限资源dao
 *
 * @author liwenqiang 2018/12/17 19:37
 **/
public interface PermInfoDao extends JpaRepository<PermInfo, Long> {

}