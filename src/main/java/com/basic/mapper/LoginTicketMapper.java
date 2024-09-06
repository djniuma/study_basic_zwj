package com.basic.mapper;

import com.basic.dto.LoginTicketDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketMapper {

    public void addLoginTicketDTO(LoginTicketDTO loginTicketDTO);

    LoginTicketDTO queryTicketByUserId(@Param("userId") String userId);

    void updateLoginTicketDTO(LoginTicketDTO loginTicketDTO);

    LoginTicketDTO queryTicketByTicket(@Param("ticket") String ticket);
}
