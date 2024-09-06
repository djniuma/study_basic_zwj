package com.basic.mapper;

import com.basic.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    public void addUserDTO(UserDTO userDTO);

    UserDTO queryUserDTO(@Param("username") String username);

    UserDTO queryUserDTOById(@Param("userId") String userId);
}
