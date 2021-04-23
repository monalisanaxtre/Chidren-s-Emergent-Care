package com.cec.doctorapp.ui;

public class MenuList {

    public String menuName;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer menuId;

    public MenuList(Integer menuId,String menuName){
        this.menuName = menuName;
        this.menuId = menuId;
    }

    public String getMenuName(){
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
