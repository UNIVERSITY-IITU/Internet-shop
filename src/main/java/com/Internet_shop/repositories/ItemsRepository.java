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


    List<Items> findAllByOrderByPriceAsc();
    List<Items> findAllByOrderByPriceDesc();

    List<Items> findAllByNameLikeAndPriceBetweenOrderByPriceAsc(String name, double price1, double price2);
    List<Items> findAllByNameLikeAndPriceBetweenOrderByPriceDesc(String name, double price1, double price2);

    List<Items> findAllByPriceBetweenOrderByPriceAsc(double price1, double price2);
    List<Items> findAllByPriceBetweenOrderByPriceDesc(double price1, double price2);

}
