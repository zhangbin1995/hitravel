package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.Ticket;
import com.lixiang.hitravel.dto.TicketDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.TicketService;
import com.lixiang.hitravel.util.MyBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author binzhang
 * @date 2020-02-10
 */
@RestController
@Api(value = "门票管理", tags = "门票管理")
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增门票", notes = "新增门票")
    public Result add(@Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = MyBeanUtils.dtoToVo(ticketDto, Ticket.class);
        return ticketService.save(ticket);
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "修改门票信息", notes = "修改门票信息")
    public Result update(@Valid @RequestBody TicketDto ticketDto) {
        Ticket ticket = MyBeanUtils.dtoToVo(ticketDto, Ticket.class);
        return ticketService.updateTicketInfo(ticket);
    }

    @ApiOperation(value = "分页查询某景区下的门票", notes = "分页查询某景区下的门票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scenicId", value = "所属景区", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, paramType = "query", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Interger", defaultValue = "10")
    })
    @PostMapping(value = "/queryByPage")
    public Result login(@RequestParam Integer scenicId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return ticketService.queryByScenicId(scenicId, pageNo, pageSize);
    }
}
