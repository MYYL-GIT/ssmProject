package com.ssm.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @GetMapping(value = "/index")
    private String index(){
        return "frontend/index";
    }

    @GetMapping(value = "/shoplist")
    private String showShopList(){
        return "frontend/shoplist";
    }

    @GetMapping(value = "/shopdetail")
    private String showShopDetail(){
        return "frontend/shopdetail";
    }

    @GetMapping(value = "/productdetail")
    private String showProductDetail(){
        return "frontend/productdetail";
    }
}
