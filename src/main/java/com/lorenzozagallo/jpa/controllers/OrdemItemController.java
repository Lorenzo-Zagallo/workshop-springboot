package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.services.OrderItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workshop/order_items")
public class OrdemItemController {

    private OrderItemService orderItemService;


}
