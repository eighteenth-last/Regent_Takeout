package com.gpt.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gpt.Common.BaseContextCommon;
import com.gpt.Common.R;
import com.gpt.Entity.AddressBookEntity;
import com.gpt.Service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     */
    @PostMapping
    public R<AddressBookEntity> save(@RequestBody AddressBookEntity addressBookEntity) {
        Long userId = BaseContextCommon.getCurrentUserId();
        addressBookEntity.setUserId(userId);
        
        log.info("新增地址:{}", addressBookEntity);
        
        // 查询当前用户是否已有地址
        LambdaQueryWrapper<AddressBookEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBookEntity::getUserId, userId);
        long count = addressBookService.count(queryWrapper);
        
        // 如果是第一个地址，自动设置为默认地址
        if (count == 0) {
            addressBookEntity.setIsDefault(1);
            log.info("用户首次添加地址，自动设置为默认地址");
        }
            // 如果不是第一个地址且未指定是否默认，则设置为非默认
            // 注意：int类型的isDefault默认值为0，不需要特别处理
            // 如果前端传了值，保持前端的值；如果没传，默认就是0（非默认地址）

        
        addressBookService.save(addressBookEntity);
        return R.success(addressBookEntity);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBookEntity> setDefault(@RequestBody AddressBookEntity addressBookEntity) {
        log.info("addressBook:{}", addressBookEntity);
        LambdaUpdateWrapper<AddressBookEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBookEntity::getUserId, BaseContextCommon.getCurrentUserId());
        wrapper.set(AddressBookEntity::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);

        addressBookEntity.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBookEntity);
        return R.success(addressBookEntity);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBookEntity addressBookEntity = addressBookService.getById(id);
        if (addressBookEntity != null) {
            return R.success(addressBookEntity);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBookEntity> getDefault() {
        LambdaQueryWrapper<AddressBookEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBookEntity::getUserId, BaseContextCommon.getCurrentUserId());
        queryWrapper.eq(AddressBookEntity::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBookEntity addressBookEntity = addressBookService.getOne(queryWrapper);

        if (null == addressBookEntity) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBookEntity);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBookEntity>> list(AddressBookEntity addressBookEntity) {
        addressBookEntity.setUserId(BaseContextCommon.getCurrentUserId());
        log.info("addressBook:{}", addressBookEntity);

        //条件构造器
        LambdaQueryWrapper<AddressBookEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBookEntity.getUserId(), AddressBookEntity::getUserId, addressBookEntity.getUserId());
        queryWrapper.orderByDesc(AddressBookEntity::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));
    }

    /**
     * 删除地址
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除地址：id={}", ids);
        
        if (ids != null) {
            // 查询地址是否存在
            AddressBookEntity addressBook = addressBookService.getById(ids);
            if (addressBook != null) {
                // 验证该地址是否属于当前用户
                Long userId = BaseContextCommon.getCurrentUserId();
                if (addressBook.getUserId().equals(userId)) {
                    // 删除地址
                    addressBookService.removeById(ids);
                    return R.success("地址删除成功");
                } else {
                    return R.error("无权删除该地址");
                }
            } else {
                return R.error("地址不存在");
            }
        }
        return R.error("删除失败");
    }

    /**
     * 修改地址
     */
    @PutMapping
    public R<AddressBookEntity> update(@RequestBody AddressBookEntity addressBook) {
        log.info("修改地址：{}", addressBook);
        
        if (addressBook.getId() == null) {
            return R.error("地址ID不能为空");
        }
        
        // 查询地址是否存在
        AddressBookEntity existAddress = addressBookService.getById(addressBook.getId());
        if (existAddress == null) {
            return R.error("地址不存在");
        }
        
        // 验证该地址是否属于当前用户
        Long userId = BaseContextCommon.getCurrentUserId();
        if (!existAddress.getUserId().equals(userId)) {
            return R.error("无权修改该地址");
        }
        
        // 设置用户ID（防止被篡改）
        addressBook.setUserId(userId);
        
        // 更新地址
        addressBookService.updateById(addressBook);
        
        return R.success(addressBook);
    }
}
