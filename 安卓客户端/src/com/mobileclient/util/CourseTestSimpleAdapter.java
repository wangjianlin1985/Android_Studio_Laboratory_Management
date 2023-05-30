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
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
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
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.coursetest_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_courseObj = (TextView)convertView.findViewById(R.id.tv_courseObj);
	  holder.tv_testName = (TextView)convertView.findViewById(R.id.tv_testName);
	  holder.tv_weekObj = (TextView)convertView.findViewById(R.id.tv_weekObj);
	  holder.tv_labTime = (TextView)convertView.findViewById(R.id.tv_labTime);
	  holder.tv_labObj = (TextView)convertView.findViewById(R.id.tv_labObj);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_courseObj.setText("�����γ̣�" + (new CourseService()).GetCourse(mData.get(position).get("courseObj").toString()).getCourseName());
	  holder.tv_testName.setText("ʵ�����ƣ�" + mData.get(position).get("testName").toString());
	  holder.tv_weekObj.setText("�Ͽ��ܣ�" + (new WeekInfoService()).GetWeekInfo(Integer.parseInt(mData.get(position).get("weekObj").toString())).getWeekName());
	  holder.tv_labTime.setText("ʵ��ʱ�䣺" + mData.get(position).get("labTime").toString());
	  holder.tv_labObj.setText("�Ͽ�ʵ���ң�" + (new LabInfoService()).GetLabInfo(Integer.parseInt(mData.get(position).get("labObj").toString())).getLabName());
	  
	   
	  /*�����޸ĺõ�view*/
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
