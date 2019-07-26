package com.zx.demo.controller.admin;

import com.zx.demo.domain.User;
import com.zx.demo.domain.UserLevel;
import com.zx.demo.service.Admin.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/console/user_level_management")
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    @RequestMapping("/getAll")
    @ResponseBody
    private Map<String,Object> getAllDevice(@RequestParam("search") String search,
                                            @RequestParam("limit") Integer limit,
                                            @RequestParam("offset") Integer offset){
        Map<String,Object> map = new HashMap<>();

        //User user = UserUtil.getCureentUser();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<UserLevel> page;

        page = userLevelService.getAll(search.trim(), pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        return map;
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    private UserLevel getDeviceById(@PathVariable Long id){

        UserLevel userLevel = userLevelService.getOneByIdForAdmin(id);

        return userLevel;
    }

    @RequestMapping("/add")
    @ResponseBody
    private Map<String, Object> addUserLevel(@RequestParam(value = "description") String description,
                                          @RequestParam(value = "level") Integer level ){
        Map<String, Object> map = new HashMap<>();

        userLevelService.addOneForAdmin(description, level);

        map.put("status","SUCCEED");

        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    private Map<String, Object> updateDevice(@Valid UserLevel userLevel){
        Map<String, Object> map = new HashMap<>();

        userLevelService.updateOneForAdmin(userLevel);

        map.put("status","SUCCEED");

        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    private Map<String, Object> deleteDevice(@RequestParam("list_ID") List<Long> id ){
        Map<String, Object> map = new HashMap<>();

        for(Long temp_id : id){
            userLevelService.deleteOneForAdmin(temp_id);
        }

        map.put("status","SUCCEED");

        return map;
    }

}
