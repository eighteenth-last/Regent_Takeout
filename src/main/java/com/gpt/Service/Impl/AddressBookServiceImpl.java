package com.gpt.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpt.Entity.AddressBookEntity;
import com.gpt.Mapper.AddressBookMapper;
import com.gpt.Service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBookEntity> implements AddressBookService {

}
