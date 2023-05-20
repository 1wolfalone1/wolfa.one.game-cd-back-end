package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.UserRegistrationDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface AccountMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "user.address", source = "address")
    @Mapping(target = "user.phone", source = "phone")
    @Mapping(target = "user.fullName", source = "name")
    Account toEntity(UserRegistrationDTO resgisUesr);

}
