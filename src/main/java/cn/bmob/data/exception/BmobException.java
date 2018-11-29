package cn.bmob.data.exception;

public class BmobException extends Exception{

    private Integer code;

    public BmobException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    public BmobException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
