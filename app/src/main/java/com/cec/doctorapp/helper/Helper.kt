package com.cec.doctorapp.helper

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.bottomnavigation.BottomNavigationView

class Helper {
    companion object{
        public fun disableTooltip(view: BottomNavigationView) {
            val content: View = view.getChildAt(0)
            if (content is ViewGroup) {
                for(it in content.children){
                    it.setOnLongClickListener {
                        return@setOnLongClickListener true
                    }
// disable vibration also
                    it.isHapticFeedbackEnabled = false
                }
            }
        }
    }
}