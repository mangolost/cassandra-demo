package com.mangolost.cassandra.service;

import com.mangolost.cassandra.entity.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class OrderDataService {

    @Autowired
    @Qualifier("orderCqlTemplate")
    private CqlTemplate cqlTemplate;

    /**
     *
     */
    public int count() {
        String sql = "select count(*) from t_order_data";
        return cqlTemplate.queryForObject(sql, Integer.class);
    }

    /**
     *
     * @param orderData
     */
    public boolean insert(OrderData orderData) {
        String sql = "insert into t_order_data (mobile, create_date, order_no, shop_id, total_amount) values (?, ?, ?, ?, ?) ";
        return cqlTemplate.execute(sql,
                orderData.getMobile(),
                orderData.getCreate_date(),
                orderData.getOrder_no(),
                orderData.getShop_id(),
                orderData.getTotal_amount());
    }

    /**
     *
     * @param mobile
     * @param createTimeBegin
     * @param createTimeEnd
     * @return
     */
    public List<OrderData> query(String mobile, Long createTimeBegin, Long createTimeEnd) {
        String sql = "select * from t_order_data where mobile = ? ";
        List<Object> objList = new ArrayList<>();
        objList.add(mobile);
        if (createTimeBegin != null) {
            sql += " and create_date >= ? ";
            objList.add(createTimeBegin);
        }
        if (createTimeEnd != null) {
            sql += " and create_date <= ? ";
            objList.add(createTimeEnd);
        }
        List<OrderData> list = cqlTemplate.query(sql, (row, rowNum) -> {
            OrderData orderData = new OrderData();
            orderData.setMobile(row.getString("mobile"));
            orderData.setCreate_date(row.getLong("create_date"));
            orderData.setOrder_no(row.getString("order_no"));
            orderData.setShop_id(row.getString("shop_id"));
            orderData.setTotal_amount(row.getDouble("total_amount"));
            return orderData;
        }, objList.toArray());
        return list;

    }


}
