package com.zfj.android.newcalender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zfj_ on 2017/5/16.
 */

public class NewCalendar extends LinearLayout {

    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private Calendar curDate = Calendar.getInstance();
    private String displayFormat;
    public NewCalenderListener listener;

    /**
     * 三个构造函数，除无参构造函数外，在里面调用初始化函数，
     */
    public NewCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public NewCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    public NewCalendar(Context context) {
        super(context);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void initControl(Context context, AttributeSet attrs){
        bindControl(context);
        bindControlEvent();
        //主要是加载自定义的dateformat，写法固定
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NewCalendar);
        try{
            String format = ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat = format;
            if(displayFormat == null){
                displayFormat = "MMM yyyy";
            }
        }finally {
            ta.recycle();
        }
        renderCalendar();
    }

    /**
     * 绑定控件
     * @param context
     */
    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view, this);
        btnPrev = (ImageView) findViewById(R.id.btnPrev);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        txtDate = (TextView) findViewById(R.id.txtDate);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    /**
     * 添加事件
     */
    private void bindControlEvent() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendar类，月份减1
                curDate.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendar类，月份加1
                curDate.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });
    }
    private void renderCalendar(){
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);
        //月份和年份显示到text中
        txtDate.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //计算上个月有几天
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);

        int maxCellCount = 6 * 7;
        while(cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        grid.setAdapter(new CalendarAdapter(getContext(), cells));
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener == null) {
                    return false;
                }else {
                   listener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }

            }
        });
    }

    /**
     * GridView的适配器
     */
    private class CalendarAdapter extends ArrayAdapter<Date>{

        LayoutInflater inflater;
        public CalendarAdapter(Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day, days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.calendar_text_day,
                        parent, false);
            }

            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));

            /**
             * 错误
             */
//            //获取当前月数天数
//            Calendar calendar = (Calendar) curDate.clone();
//            calendar.set(Calendar.DAY_OF_MONTH,1);
//            int daysInMonth = calendar.getActualMaximum(Calendar.DATE);
                // 当前月份的天
//                if(day >= 1 && day<= daysInMonth){
//                    ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
//                }else{
//                    ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
//                }

            Boolean isTheSameMonth = false;
            //今天
            Date now = new Date();
            if(date.getMonth() == now.getMonth()){
                isTheSameMonth = true;
            }
            if(isTheSameMonth){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else{
                ((TextView)convertView).setTextColor(Color.parseColor("#666666"));
            }




            if(now.getDate() == date.getDate()&&now.getMonth() == date.getMonth()
                    &&now.getYear() == date.getYear()){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
                ((Calendar_day_textView)convertView).isToday = true;

            }


            return convertView;
        }
    }

    /**
     * 回调方法，当单击GridView子视图调用。
     */
    public interface NewCalenderListener{
        void onItemLongPress(Date day);
    }
}
