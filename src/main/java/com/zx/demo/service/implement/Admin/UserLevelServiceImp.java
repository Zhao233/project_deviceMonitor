package com.zx.demo.service.implement.Admin;

import com.zx.demo.domain.UserLevel;
import com.zx.demo.repository.Admin.UserLevelDao;
import com.zx.demo.service.Admin.UserLevelService;
import com.zx.demo.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userLevelService")
public class UserLevelServiceImp implements UserLevelService {
    @Autowired
    private UserLevelDao userLevelDao;

    @Override
    public Page<UserLevel> getAll(String search, Pageable pageable) {
        return userLevelDao.getAll(search,pageable);
    }

    @Override
    public void addOneForAdmin(String description, Integer level) {
        UserLevel userLevel = new UserLevel();
        userLevel.setDescription(description);
        userLevel.setLevel(level);
        userLevel.setCreateTime(DateTimeUtil.getTimeNow());
        userLevel.setUpdateTime(DateTimeUtil.getTimeNow());

        userLevelDao.save(userLevel);
    }

    @Override
    public UserLevel getOneByIdForAdmin(Long id) {
        return userLevelDao.getOneById(id);
    }

    @Override
    public void updateOneForAdmin(UserLevel userLevel) {
        userLevelDao.save(userLevel);
    }

    @Override
    public void deleteOneForAdmin(Long id) {
        userLevelDao.deleteById(id);
    }
}
