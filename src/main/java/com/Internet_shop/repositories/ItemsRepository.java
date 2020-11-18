package com.Internet_shop.repositories;

import com.Internet_shop.entities.Brands;
import com.Internet_shop.entities.Categories;
import com.Internet_shop.entities.Items;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemsRepository extends JpaRepository<Items, Long> {

    @Query(value = "select i from Items  i where lower(i.name) like lower(?1) and i.price between ?2 and ?3 order by i.price asc")
    List<Items> findAllByNameLikeAndPriceBetweenOrderByPriceAsc(String name, double price1, double price2);

    @Query(value = "select i from Items  i where lower(i.name) like lower(?1) and i.price between ?2 and ?3 and i.brand.id = ?4 order by i.price desc")
    List<Items> findAllByNameLikeAndPriceBetweenAndBrandIsOrderByPriceDesc(String name, double price1, double price2, Long brand_id);

    List<Items> findAllByOrderByPriceAsc();
    List<Items> findAllByOrderByPriceDesc();

    List<Items> findAllByBrandIs(Brands brand);

    List<Items> findAllByCategories(Categories category);



}
