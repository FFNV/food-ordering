package com.foodordering.data;

import java.util.Optional;

import com.foodordering.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);

    Optional<TacoOrder> findById(Long id);

}