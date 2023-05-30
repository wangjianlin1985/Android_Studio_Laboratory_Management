package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CourseService;
import com.mobileclient.service.WeekInfoService;
import com.mobileclient.service.LabInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.app.Declare;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class CourseTestSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public CourseTestSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.coursetest_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_courseObj = (TextView)convertView.findViewById(R.id.tv_courseObj);
	  holder.tv_testName = (TextView)convertView.findViewById(R.id.tv_testName);
	  holder.tv_weekObj = (TextView)convertView.findViewById(R.id.tv_weekObj);
	  holder.tv_labTime = (TextView)convertView.findViewById(R.id.tv_labTime);
	  holder.tv_labObj = (TextView)convertView.findViewById(R.id.tv_labObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_courseObj.setText("所属课程：" + (new CourseService()).GetCourse(mData.get(position).get("courseObj").toString()).getCourseName());
	  holder.tv_testName.setText("实验名称：" + mData.get(position).get("testName").toString());
	  holder.tv_weekObj.setText("上课周：" + (new WeekInfoService()).GetWeekInfo(Integer.parseInt(mData.get(position).get("weekObj").toString())).getWeekName());
	  holder.tv_labTime.setText("实验时间：" + mData.get(position).get("labTime").toString());
	  holder.tv_labObj.setText("上课实验室：" + (new LabInfoService()).GetLabInfo(Integer.parseInt(mData.get(position).get("labObj").toString())).getLabName());
	  
	   
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_courseObj;
    	TextView tv_testName;
    	TextView tv_weekObj;
    	TextView tv_labTime;
    	TextView tv_labObj;
    }
} 
