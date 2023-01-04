package ru.hse.miem.hsecourses.ui.graphics

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import ir.mahozad.android.PieChart
import ru.hse.miem.hsecourses.R
import ru.hse.miem.hsecourses.courses.Day
import ru.hse.miem.hsecourses.courses.Module
import ru.hse.miem.hsecourses.courses.Task
import ru.hse.miem.hsecourses.graphics.GraphValue
import ru.hse.miem.hsecourses.graphics.PopupBarChart
import ru.hse.miem.hsecourses.ui.MainActivity
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData
import java.text.DecimalFormat
import java.time.LocalDate

class GraphicsFragment : Fragment() {
    var graph: PopupBarChart? = null
    var textViewHoursCount: TextView? = null
    var listener: CommunicateData? = null
    private var activityContext: Context? = null

    private lateinit var dayList: ArrayList<Day>
    private lateinit var taskList: ArrayList<Task>

    private lateinit var textViewWeeksCount: TextView
    private lateinit var textViewWeeksCountTextView : TextView

    private lateinit var pieChartTime : PieChart

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            this.activityContext = context
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_graphics, container, false)
        pieChartTime = root.findViewById(R.id.pieChartModules)
        graph = root.findViewById(R.id.customBarchart)
        textViewHoursCount = root.findViewById(R.id.textViewHoursCount)

        textViewWeeksCountTextView = root.findViewById(R.id.textViewWeeksCount) as TextView

        loadCourseData()
        return root
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
    private fun loadCourseData() {
        if (listener == null) listener = requireContext() as CommunicateData

        val liveDataDays = listener!!.model.allDays
        liveDataDays.observe(
            requireActivity()
        ) { value ->
            dayList = value as ArrayList<Day>
            if(this::taskList.isInitialized && this::dayList.isInitialized)
                showGraphicTaskCount()
        }

        val liveDataTask = listener!!.model.allTasks
        liveDataTask.observe(
            requireActivity()
        ) { value ->
            taskList = value as ArrayList<Task>
            if(this::taskList.isInitialized && this::dayList.isInitialized)
                showGraphicTaskCount()
        }

        val liveDataModules = listener!!.model.allModules
        liveDataModules.observe(
            requireActivity()
        ) { value ->
            showGraphicHoursCount(value as ArrayList<Module>)
        }
    }

    private fun showGraphicTaskCount() {
        val values: MutableList<GraphValue> = ArrayList()
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek

        val daysHoursCount = DoubleArray(dayList.size)
        var totalCount = 0.0
        for (i in taskList.indices) {
            totalCount += taskList[i].taskTimeCount / (1000 * 60 * 60.0)
            daysHoursCount[i] = taskList[i].taskTimeCount / (1000 * 60 * 60.0)
            val curr = GraphValue(
                i, i, daysHoursCount[i].toInt() * 100 / 24,
                dayOfWeek.value - 1 == dayList[i].dayNumber,
                false
            )
            values.add(curr)
        }
        val decimalFormat = DecimalFormat("#.#")
        val result = decimalFormat.format(totalCount)
        graph!!.setGraphValues(values)
        textViewHoursCount!!.text = "$result ч"
    }

    private fun showGraphicHoursCount(modules: ArrayList<Module>) {
        val countEnded = modules.count { it.isEnded }
        textViewWeeksCountTextView.text=("$countEnded")

        pieChartTime.apply {
            slices = listOf(
                PieChart.Slice(countEnded.toFloat()/modules.count(), Color.rgb(120, 181, 0), Color.rgb(149, 224, 0), legend = "Законченные"),
                PieChart.Slice(modules.count { !it.isEnded }.toFloat()/modules.count(), Color.rgb(249, 228, 0), legend = "Ждут завершения"),
            )
            gradientType = PieChart.GradientType.RADIAL
            legendsIcon = PieChart.DefaultIcons.CIRCLE
            labelType = PieChart.LabelType.OUTSIDE
            labelsColor = context.getColorFromAttr(com.google.android.material.R.attr.colorOnSecondary)
            isLegendEnabled = true
            isLegendsPercentageEnabled = false
            legendPosition = PieChart.LegendPosition.BOTTOM
        }
    }
}