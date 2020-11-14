package com.Internet_shop.repositories;

import com.Internet_shop.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    List<Categories> findAllByIdIn(List<Long> id);
}
