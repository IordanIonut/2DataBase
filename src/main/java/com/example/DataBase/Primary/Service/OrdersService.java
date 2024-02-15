package com.example.DataBase.Primary.Service;

import com.example.DataBase.Primary.Model.Orders;
import com.example.DataBase.Primary.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository repository;

    public Orders saveOrders(Orders order) {
        return repository.save(order);
    }

    public List<Orders> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Orders> getByIdOrders(Long id_order) {
        return repository.findById(id_order);
    }

    public void deleteOrders(Long id_order) {
        repository.deleteById(id_order);
    }

    public Orders updateOrders(Long id_order, Orders order) {
        if (repository.existsById(id_order)) {
            order.setId_order(id_order);
            return repository.save(order);
        }
        return null;
    }
}
