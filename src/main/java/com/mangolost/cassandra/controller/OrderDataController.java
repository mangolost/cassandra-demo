package com.mangolost.cassandra.controller;

import com.mangolost.cassandra.common.CommonResult;
import com.mangolost.cassandra.entity.OrderData;
import com.mangolost.cassandra.service.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/api/orderdata")
public class OrderDataController {

    private final OrderDataService orderDataService;

    @Autowired
    public OrderDataController(OrderDataService orderDataService) {
        this.orderDataService = orderDataService;
    }

    /**
     *
     * @return
     */
    @RequestMapping("count")
    public CommonResult count() {
        CommonResult commonResult = new CommonResult();

        int count = orderDataService.count();

        return commonResult.setData(count);
    }

    /**
     *
     * @return
     */
    @PostMapping("insert")
    public CommonResult insert(@RequestBody OrderData orderData) {
        CommonResult commonResult = new CommonResult();

        boolean flag = orderDataService.insert(orderData);

        return commonResult.setData(flag);
    }

    /**
     *
     * @param mobile
     * @param create_date_begin
     * @param create_date_end
     * @return
     */
    @RequestMapping("query")
    public CommonResult query(@RequestParam String mobile,
                              @RequestParam(required = false) Long create_date_begin,
                              @RequestParam(required = false) Long create_date_end) {
        CommonResult commonResult = new CommonResult();

        if (create_date_begin > create_date_end) {
            return commonResult.setCodeAndMessage(430, "开始时间要不能晚于结束时间");
        }

        List<OrderData> list = orderDataService.query(mobile, create_date_begin, create_date_end);

        return commonResult.setData(list);
    }

}
