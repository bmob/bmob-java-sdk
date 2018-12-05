package cn.bmob.data.bean.type;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * bmob时间类型
 */
public class BmobDate implements Serializable {
    private String iso;
    private String __type = "Date";


    /**
     * 创建BmobDate类型
     * @param date 时间
     */
    public BmobDate(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.iso = df.format(date);
    }

    private String get__type() {
        return __type;
    }
    private String getIso() {
        return iso;
    }
    public String getDate(){
        return iso;
    }

    /**将String转换为long时间戳
     * @param  createdAt:Bmob数据的创建或修改时间（其格式为：yyyy-MM-dd HH:mm:ss）
     * @return long(毫秒)
     */
    public static long getTimeStamp(String createdAt){
        long timestamp=0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(createdAt);
            timestamp =date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**创建BmobDate
     * @param  formatType:格式类型：类似"yyyy-MM-dd"这种
     * @param  time：时间字符串：类似"2015-09-24"，必须和formatType格式相同，否则创建失败
     * @return BmobDate
     */
    public static BmobDate createBmobDate(String formatType,String time){
        BmobDate bmobDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date = formatter.parse(time);
            bmobDate =new BmobDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmobDate;
    }

}
