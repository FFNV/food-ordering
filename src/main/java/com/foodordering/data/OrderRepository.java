package com.foodordering.data;

import com.foodordering.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {

}
