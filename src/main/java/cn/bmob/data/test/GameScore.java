package cn.bmob.data.test;


import cn.bmob.data.bean.BmobObject;
import lombok.Data;

@Data
public class GameScore extends BmobObject {


    private String playerName;

    private Integer score;

    private Boolean cheatMode;

}
