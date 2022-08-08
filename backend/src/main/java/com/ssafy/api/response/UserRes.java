package com.ssafy.api.response;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel("UserResponse")
public class UserRes {
    @ApiModelProperty(name = "유저 ID", example = "123")
    Long id;

    @ApiModelProperty(name = "유저 닉네임", example = "유교보이")
    String name;

    @ApiModelProperty(name = "유저 가입일", example = "2022-08-08")
    LocalDate regDate;

    @ApiModelProperty(name = "유저 설명", example = "화이팅")
    String description;

    @ApiModelProperty(name = "프로필 사진", example = "https://via.placeholder.com/150/")
    String profileImgUrl;

    @ApiModelProperty(name = "D-DAY", example = "2022-08-12")
    LocalDate dDay;

    @ApiModelProperty(name = "현재 보유 슬리", example = "10000")
    long currentSley;

    @ApiModelProperty(name = "현재 보유 도감 포인트", example = "9500")
    long currentBookPoint;

    public static UserRes of (User user) {
        UserRes res = new UserRes();
        res.setName(user.getName());
        res.setRegDate(user.getRegDate());
        res.setDescription(user.getDescription());
        res.setProfileImgUrl(user.getProfileImgUrl());
        res.setDDay(user.getDDay());
        res.setCurrentSley(user.getCurrentSley());
        res.setCurrentBookPoint(user.getCurrentBookPoint());
        return res;
    }
}
