package com.ssm.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/productadmin",method = {RequestMethod.GET})
public class ProductAdminController {
    @RequestMapping(value = "/productlist")
    public String productList(){
        return "shop/productlist";
    }

    @RequestMapping(value = "/productoperation")
    public String shopOperation(){
        return "shop/productoperation";

    }
}
