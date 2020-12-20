package com.Internet_shop.repositories;


import com.Internet_shop.entities.Pictures;
import com.Internet_shop.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PicturesRepository extends JpaRepository<Pictures, Long> {
    List<Pictures> findAllByItems_Id(Long item_id);
    Pictures findByIdEquals(Long id);
}
