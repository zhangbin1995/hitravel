package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.Room;
import com.lixiang.hitravel.dto.RoomDto;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.RoomService;
import com.lixiang.hitravel.util.MyBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhang
 * @date 2020-01-22
 */
@RestController
@Api(value = "房间管理", tags = "房间管理")
@RequestMapping(value = "/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @ApiOperation(value = "新增房间", notes = "新增房间")
    @PostMapping(value = "/add")
    public Result add(@Valid @RequestBody RoomDto roomDto) {
        Room room = MyBeanUtils.dtoToVo(roomDto, Room.class);
        return roomService.save(room);
    }

    @ApiOperation(value = "修改房间信息", notes = "修改房间信息")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody RoomDto roomDto) {
        Room room = MyBeanUtils.dtoToVo(roomDto, Room.class);
        return roomService.updateRoomInfo(room);
    }

    @ApiOperation(value = "根据主键id查看房间信息", notes = "根据主键id查看房间信息")
    @PostMapping(value = "/queryById")
    public Result queryById(@RequestParam Integer roomId) {
        return roomService.queryById(roomId);
    }

    @ApiOperation(value = "分页查询某酒店下的房间列表", notes = "分页查询某酒店下的房间列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hotelId", value = "所属酒店", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @PostMapping(value = "/queryByHotelId")
    public Result queryByHotelId(@RequestParam Integer hotelId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return roomService.queryByHotelId(hotelId, pageNo, pageSize);
    }

    @ApiOperation(value = "分页查询某酒店下某日的房间列表", notes = "分页查询某酒店下某日的房间列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hotelId", value = "所属酒店", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "inDate", value = "入住日期 如 2020-01-01", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @PostMapping(value = "/queryByHotelIdAndTime")
    public Result queryByHotelIdAndTime(@RequestParam Integer hotelId, @RequestParam String inDate, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return roomService.queryByHotelIdAndTime(hotelId, inDate, pageNo, pageSize);
    }
}
