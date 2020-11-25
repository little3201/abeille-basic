/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.abeille.basic.hypervisor.entity.SourceInfo;

/**
 * 权限资源dao
 *
 * @author liwenqiang 2018/12/17 19:37
 **/
@Repository
public interface SourceRepository extends JpaRepository<SourceInfo, Long> {

}