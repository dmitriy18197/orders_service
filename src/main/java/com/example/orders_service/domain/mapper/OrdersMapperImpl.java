package com.example.orders_service.domain.mapper;

import com.example.orders_service.domain.Status;
import com.example.orders_service.domain.Order;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrdersMapperImpl implements OrdersMapper {

    private final SqlSessionFactory sqlSessionFactory;


    @Override
    public List<Order> selectAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return getMapper(sqlSession).selectAll();
        }
    }

    @Override
    public Order selectById(Long id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return getMapper(sqlSession).selectById(id);
        }
    }

    @Override
    public List<Order> selectByStatus(Status status) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return getMapper(sqlSession).selectByStatus(status);
        }
    }

    @Override
    public List<Order> searchById(String pattern) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return getMapper(sqlSession).searchById(pattern);
        }
    }

    @Override
    public void insert(Order order) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            getMapper(sqlSession).insert(order);
            sqlSession.commit();
        }
    }

    @Override
    public void updateOrderStatus(Long id, Status status) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            getMapper(sqlSession).updateOrderStatus(id, status);
            sqlSession.commit();
        }
    }

    private OrdersMapper getMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(OrdersMapper.class);
    }
}
