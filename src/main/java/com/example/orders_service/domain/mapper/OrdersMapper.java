package com.example.orders_service.domain.mapper;

import com.example.orders_service.domain.Status;
import com.example.orders_service.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrdersMapper {

    @Select("select * from ORDERS")
    List<Order> selectAll();

    @Select("select * from ORDERS where ID=#{id}")
    Order selectById(Long id);

    @Select("select * from ORDERS where ID::text like #{pattern}")
    List<Order> searchById(String pattern);

    @Insert("insert into ORDERS(created, status) values(#{created}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(Order order);

    @Update("update ORDERS set STATUS=#{status} where ID=#{id}")
    void updateOrderStatus(Long id, Status status);

}
