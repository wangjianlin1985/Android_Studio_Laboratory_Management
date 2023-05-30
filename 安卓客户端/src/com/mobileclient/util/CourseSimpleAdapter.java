package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ClassInfoService;
import com.mobileclient.service.TeacherService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class CourseSimpleAdapter extends SimpleAdapter { 
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

    public CourseSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.course_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_courseNo = (TextView)convertView.findViewById(R.id.tv_courseNo);
	  holder.tv_classObj = (TextView)convertView.findViewById(R.id.tv_classObj);
	  holder.tv_courseName = (TextView)convertView.findViewById(R.id.tv_courseName);
	  holder.tv_courseHours = (TextView)convertView.findViewById(R.id.tv_courseHours);
	  holder.tv_courseScore = (TextView)convertView.findViewById(R.id.tv_courseScore);
	  holder.tv_teacherObj = (TextView)convertView.findViewById(R.id.tv_teacherObj);
	  holder.tv_courseDesc = (TextView)convertView.findViewById(R.id.tv_courseDesc);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_courseNo.setText("�γ̱�ţ�" + mData.get(position).get("courseNo").toString());
	  holder.tv_classObj.setText("�����༶��" + (new ClassInfoService()).GetClassInfo(mData.get(position).get("classObj").toString()).getClassName());
	  holder.tv_courseName.setText("�γ����ƣ�" + mData.get(position).get("courseName").toString());
	  holder.tv_courseHours.setText("�γ���ѧʱ��" + mData.get(position).get("courseHours").toString());
	  holder.tv_courseScore.setText("�γ�ѧ�֣�" + mData.get(position).get("courseScore").toString());
	  holder.tv_teacherObj.setText("�Ͽ���ʦ��" + (new TeacherService()).GetTeacher(mData.get(position).get("teacherObj").toString()).getName());
	  holder.tv_courseDesc.setText("�γ�������" + mData.get(position).get("courseDesc").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_courseNo;
    	TextView tv_classObj;
    	TextView tv_courseName;
    	TextView tv_courseHours;
    	TextView tv_courseScore;
    	TextView tv_teacherObj;
    	TextView tv_courseDesc;
    }
} 
