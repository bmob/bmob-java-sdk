package cn.bmob.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentScore {
    private String playerName;
    private int score;
}
