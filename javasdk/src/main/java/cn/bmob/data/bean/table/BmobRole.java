package cn.bmob.data.bean.table;

import cn.bmob.data.bean.type.BmobRelation;

public class BmobRole extends BmobObject {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色的子角色
     */
    private BmobRelation roles = new BmobRelation();
    /**
     * 角色的用户
     */
    private BmobRelation users = new BmobRelation();

    /**
     * 创建角色
     */
    public BmobRole(){
        super("_Role");
    }

    /**
     * 获取角色名称
     * @return 角色名
     */
    public String getName() {
        return name;
    }
    /**
     * 设置角色名称
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取当前角色的子角色
     * @return 返回当前角色的子角色
     */
    public BmobRelation getRoles() {
        return roles;
    }
    /**
     * 获取当前角色的用户
     * @return 返回当前角色的用户
     */
    public BmobRelation getUsers() {
        return users;
    }


}
