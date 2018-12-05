package cn.bmob.data.bean.table;

import cn.bmob.data.bean.type.BmobRelation;

public class BmobRole extends BmobObject {

    public static String tableName = "_Role";
    protected String name;
    private BmobRelation roles = new BmobRelation();
    private BmobRelation users = new BmobRelation();

    @Override
    public String getTableName() {
        return "_Role";
    }

    /**
     * 创建角色
     * @param name 角色名称
     */
    public BmobRole(String name){
        setName(name);
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
