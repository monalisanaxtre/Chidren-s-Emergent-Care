package com.cec.doctorapp.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GetBookResponseBeanModel {
    @SerializedName("date")
    @Expose
    var date = "1900-01-01"

    @SerializedName("filled")
    @Expose
    var filled = ArrayList<DataBookModel>()

    fun getDate(): Date {
        return try {
            val date=SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(date)
            date
        } catch (e: ParseException) {
            e.printStackTrace()
            Date(System.currentTimeMillis())
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Date(System.currentTimeMillis())
        }
    }
}