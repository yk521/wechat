package com.springmvc.api;

import com.springmvc.menu.Menu;

/**
 */
public class GetMenuResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
