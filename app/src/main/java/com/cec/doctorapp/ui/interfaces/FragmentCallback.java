package com.cec.doctorapp.ui.interfaces;




import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface FragmentCallback {
    default void setNotificationsCount(int count){}
    default void hideProgress(){}
    default void showProgress(){}
    default void gotoFrag(int position){}


}
