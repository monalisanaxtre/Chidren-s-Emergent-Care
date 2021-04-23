package com.cec.doctorapp.ui.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cec.doctorapp.R
import com.cec.doctorapp.model.custom.RecyclerCalenderConfiguration
import com.cec.doctorapp.model.custom.RecyclerCalenderViewItem
import com.cec.doctorapp.utility.CalenderUtils
import org.joda.time.DateTimeComparator
import java.util.*

class HorizontalRecyclerCalenderAdapter constructor(startDate: Date,
                                         endDate: Date,
                                         private val configuration: RecyclerCalenderConfiguration,
                                         private var selectedDate: Date,
                                         private val dateSelectListener: OnDateSelected,

 ) : RecyclerCalenderBaseAdapter(startDate, endDate, configuration) {







     interface OnDateSelected {
        fun onDateSelected(date: Date)
    }


    public fun selectedDate(): Date {
        return selectedDate;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_calender_horizontal, parent, false)
        return MonthCalendarViewHolder(
                view
        )
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            calendarItem: RecyclerCalenderViewItem
    ) {
        val monthViewHolder: MonthCalendarViewHolder = holder as MonthCalendarViewHolder
        val context: Context = monthViewHolder.itemView.context
        monthViewHolder.itemView.visibility = View.VISIBLE

        monthViewHolder.itemView.setOnClickListener(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            monthViewHolder.itemView.background = null
        } else {
            monthViewHolder.itemView.setBackgroundDrawable(null)
        }
        monthViewHolder.textViewDay.setTextColor(
                ContextCompat.getColor(
                        context,
                        R.color.blue
                )
        )
        monthViewHolder.textViewDate.setTextColor(
                ContextCompat.getColor(
                        context,
                        R.color.blue
                )
        )

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            monthViewHolder.itemView.tag= selectedCalendar.time
            val date: String = CalenderUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = selectedCalendar.time,
                    format = CalenderUtils.DISPLAY_MONTH_FORMAT
            ) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()
            monthViewHolder.textViewDate.text = date


            monthViewHolder.textViewDay.text = year.toString()
            val toDay=Date(System.currentTimeMillis())
            Log.e(javaClass.simpleName,"${toDay.time}  ::"+selectedCalendar.timeInMillis)

            if (DateTimeComparator.getDateOnlyInstance().compare(toDay,Date(selectedCalendar.timeInMillis))==0)
            {
                monthViewHolder.textViewDay.text = "Today"
            }else{
                monthViewHolder.textViewDay.text = date
            }

            monthViewHolder.itemView.setOnClickListener(null)
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance()
            calendarDate.time = calendarItem.date

            val stringCalendarTimeFormat: String =
                    CalenderUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = calendarItem.date,
                            format = CalenderUtils.DB_DATE_FORMAT
                    )
                            ?: ""
            val stringSelectedTimeFormat: String =
                    CalenderUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = selectedDate,
                            format = CalenderUtils.DB_DATE_FORMAT
                    ) ?: ""

            if (stringCalendarTimeFormat == stringSelectedTimeFormat) {
                monthViewHolder.itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.layout_round_corner)
                monthViewHolder.textViewDay.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.white
                        )
                )
                monthViewHolder.textViewDate.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.white
                        )
                )

            }else{
                monthViewHolder.itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.layout_round_corner_whilte_bg)
            }

            val day: String = CalenderUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalenderUtils.DISPLAY_WEEK_DAY_FORMAT
            ) ?: ""

            monthViewHolder.textViewDay.text = day

            monthViewHolder.itemView.tag= calendarDate.time
            val toDay=Date(System.currentTimeMillis())
            Log.e(javaClass.simpleName,"${toDay.time}  ::"+calendarDate.timeInMillis)
            if (DateTimeComparator.getDateOnlyInstance().compare(toDay,Date(calendarDate.timeInMillis))==0)
            {
                monthViewHolder.textViewDay.text = "Today"
            }else{
                monthViewHolder.textViewDay.text = day
            }

            monthViewHolder.textViewDate.text =
                    CalenderUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = calendarDate.time,
                            format = CalenderUtils.DISPLAY_DATE_FORMAT
                    ) ?: ""


            monthViewHolder.itemView.setOnClickListener {
                selectedDate = calendarItem.date
                dateSelectListener.onDateSelected(calendarItem.date)
                notifyDataSetChanged()


            }

        }
    }

    inner class MonthCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDay: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDay)
        val textViewDate: TextView = itemView.findViewById(R.id.textCalenderItemHorizontalDate)



    }

}

