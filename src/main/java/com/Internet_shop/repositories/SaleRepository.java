package com.Internet_shop.repositories;


import com.Internet_shop.entities.Sale;
import com.Internet_shop.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findByItems_Id(Long item_id);
    List<Users> findAllByItems_Id(Long item_id);
}
