package com.cec.doctorapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.cec.doctorapp.R
import com.cec.doctorapp.databinding.ActivityBookingConsultBinding
import com.cec.doctorapp.helper.Vu
import com.cec.doctorapp.model.custom.RecyclerCalenderConfiguration
import com.cec.doctorapp.model.request.GetBookData
import com.cec.doctorapp.model.response.*
import com.cec.doctorapp.network.NetworkListner
import com.cec.doctorapp.ui.adapters.BookedConsultAdapter
import com.cec.doctorapp.ui.adapters.HorizontalRecyclerCalenderAdapter
import kotlinx.android.synthetic.main.activity_booking_consult.*
import java.text.SimpleDateFormat
import java.util.*

class BookingConsultActivity : BaseActivity(), BookedConsultAdapter.BookInterface {
    private val sevenDayBookings by lazy {
        ArrayList<GetBookResponseBeanModel>()
    }


    private val dateFormat by lazy{
        SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    }

    private var date: String = ""
    private var date2: String = ""
    private var adapter: BookedConsultAdapter? = null
    var dataList: ArrayList<DataBookModel>? = null
    private var bookdata: DataBookModel? = null
    private val binding by lazy {
        ActivityBookingConsultBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        rootLayout = binding.rootLayout
        var current: Fragment? = null
//        Vu.gone(binding.toolBar.back)
        Vu.setTransparentBG(binding.toolBar.commontoolbar)
        binding.toolBar.notify.setImageResource(R.drawable.ic_baseline_blue_notifications_24)
        this.initview()
        this.bookdata(Date().apply {
            val c = Calendar.getInstance()
            c.timeInMillis = System.currentTimeMillis()
            c.add(Calendar.DATE, -1)
            this.time = c.timeInMillis
        })
        binding.toolBar.notify.setOnClickListener { v ->
            val i = Intent(this@BookingConsultActivity, NotificationActivity::class.java)
            startActivity(i)
        }
        binding.toolBar.back.setOnClickListener { v ->
            val i = Intent(this@BookingConsultActivity, HomeActivity::class.java)
            backBtnPressed(v)

    }
        val calendarRecyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView)
        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val configuration =
                RecyclerCalenderConfiguration(
                        calenderViewType = RecyclerCalenderConfiguration.CalenderViewType.HORIZONTAL,
                        calendarLocale = Locale.getDefault(),
                        includeMonthHeader = true
                )

//        textViewSelectedDate.text =
//                CalenderUtils.dateStringFromFormat(
//                        locale = configuration.calendarLocale,
//                        date = date,
//                        format = CalenderUtils.LONG_DATE_FORMAT
//                ) ?: ""

        val calendarAdapterHorizontal =
                HorizontalRecyclerCalenderAdapter(
                        startDate = startCal.time,
                        endDate = endCal.time,
                        configuration = configuration,
                        selectedDate = date,
                        dateSelectListener = object : HorizontalRecyclerCalenderAdapter.OnDateSelected {
                            override fun onDateSelected(date: Date) {
                                val mDate = dateFormat.format(date)
                                //here do compare and show data from the list if you have it else hit api
                                val item = sevenDayBookings.find {
                                    Log.d("TAG", it.date + " :::" + date.toString())
                                    val found = (mDate == it.date)
                                    found
                                }
                                if (item == null) {
                                    bookdata(Date(date.time).apply {
                                        val c = Calendar.getInstance()
                                        c.timeInMillis = this.time
                                        c.add(Calendar.DATE, -1)
                                        this.time = c.timeInMillis
                                    }, true, date.time)
                                } else {
                                    binding.root.post {
                                        if (item.filled.isNotEmpty()) {
                                            Vu.gone(binding.txtNoBookings)
                                            val adpter = BookedConsultAdapter(
                                                    item.filled,
                                                    BookedConsultAdapter.BookInterface { dataBookModel, position ->

                                                    })
                                            binding.consultRv.setAdapter(adpter)

                                        } else {
                                            binding.consultRv.setAdapter(null)
                                            Vu.show(binding.txtNoBookings)
                                            Vu.show(binding.imptxt)
                                        }
                                    }
                                }
                            }
                        }
                )


        calendarRecyclerView.adapter = calendarAdapterHorizontal

        val snapHelper = PagerSnapHelper() // Or LinearSnapHelper
        snapHelper.attachToRecyclerView(calendarRecyclerView)
        binding.contnuebtn.setOnClickListener {
            val intent = Intent(this@BookingConsultActivity, BookingDetailActivity::class.java)
                intent.putExtra("date", calendarAdapterHorizontal.selectedDate().time)

            startActivity(intent)
        }
    }

    private fun bookdata(date: Date, selected: Boolean = false, selectedTime: Long = 0) {

        val getBookData = GetBookData()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val currentDateandTime: String = sdf.format(date);
        Log.d("currentDateandTime", currentDateandTime);
//        date.time = System.currentTimeMillis()
        getBookData.setDate(currentDateandTime)
        graph.serviceCaller.callService(graph.apis.get_booking_data(getBookData), object : NetworkListner {
            override fun onSuccess(responseBody: ResultModel<*>) {
                if (responseBody.status == 0) {
//
                    showNoData("No Booking found")
                } else {
                    hideProgress()
                    if (responseBody.data is ArrayList<*>) {
                        if (!selected) {
                            date.time = System.currentTimeMillis()
                        } else {
                            date.time = selectedTime
                        }
                        sevenDayBookings.clear()
                        //noinspection unchecked
                        sevenDayBookings.addAll(responseBody.data as ArrayList<GetBookResponseBeanModel>)
                        val mDate = dateFormat.format(date)
                        val item = sevenDayBookings.find {
                            Log.d("TAG", it.date + " :::" + date.toString())
                            val found = (mDate == it.date)
                            found
                        }
                        if (item == null) {
                            bookdata(date, selected, selectedTime)
                        } else {
                            binding.root.post {
                                if (item.filled.isNotEmpty()) {
                                    Vu.gone(binding.txtNoBookings)
                                    val adpter = BookedConsultAdapter(
                                            item.filled,
                                            BookedConsultAdapter.BookInterface { dataBookModel, position ->

                                            })
                                    binding.consultRv.setAdapter(adpter)

                                } else {
                                    binding.consultRv.setAdapter(null)
                                    Vu.show(binding.txtNoBookings)
                                }
                            }
                        }

                    }
                }
            }

            override fun onError(msg: String) {
                hideProgress()
            }

            override fun onComplete() {
                hideProgress()
            }

            override fun onStart() {
                showProgress()
            }
        })
    }


    private fun initview() {
        graph.getServiceCaller().callService(graph.getApis().getavailabilty(), object : NetworkListner {
            override fun onSuccess(responseBody: ResultModel<*>) {
                hideProgress()
                Log.d("TAG", responseBody.data.toString() + "")
                val getAvailabilityModel = responseBody.data as GetAvailabilityModel
                if (getAvailabilityModel != null) {
                    Log.d("timehck", getAvailabilityModel.timing.toString() + " ")
                    if (getAvailabilityModel.futureAvailStatus == 1) {
                        getAvailabilityModel.fromDate
                        getAvailabilityModel.toDate

                    }

                }
            }

            override fun onError(msg: String) {
                Log.d("TAG", msg + "")
                hideProgress()
            }

            override fun onComplete() {
                Log.d("TAG", "onComplete")
                hideProgress()
            }

            override fun onStart() {
                Log.d("TAG", "onstart")
                showProgress()
            }
        })

    }

    override fun book(dataBookModel: DataBookModel?, position: Int) {
        this.bookdata = dataBookModel

    }
}