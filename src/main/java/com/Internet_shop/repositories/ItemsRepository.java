package com.Internet_shop.repositories;

import com.Internet_shop.entities.Items;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemsRepository extends JpaRepository<Items, Long> {
    void deleteByIdEquals(Long id);
    List<Items> findAllByOrderByInTopPageDesc();
}
