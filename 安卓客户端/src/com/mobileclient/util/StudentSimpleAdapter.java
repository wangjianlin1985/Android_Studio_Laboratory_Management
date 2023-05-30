package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ClassInfoService;
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

public class StudentSimpleAdapter extends SimpleAdapter { 
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

    public StudentSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.student_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_studentNo = (TextView)convertView.findViewById(R.id.tv_studentNo);
	  holder.tv_classObj = (TextView)convertView.findViewById(R.id.tv_classObj);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_gender = (TextView)convertView.findViewById(R.id.tv_gender);
	  holder.tv_birthDate = (TextView)convertView.findViewById(R.id.tv_birthDate);
	  holder.iv_studentPhoto = (ImageView)convertView.findViewById(R.id.iv_studentPhoto);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_studentNo.setText("ѧ�ţ�" + mData.get(position).get("studentNo").toString());
	  holder.tv_classObj.setText("���ڰ༶��" + (new ClassInfoService()).GetClassInfo(mData.get(position).get("classObj").toString()).getClassName());
	  holder.tv_name.setText("������" + mData.get(position).get("name").toString());
	  holder.tv_gender.setText("�Ա�" + mData.get(position).get("gender").toString());
	  try {holder.tv_birthDate.setText("�������ڣ�" + mData.get(position).get("birthDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.iv_studentPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener studentPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_studentPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("studentPhoto"),studentPhotoLoadListener);  
	  holder.tv_telephone.setText("��ϵ�绰��" + mData.get(position).get("telephone").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_studentNo;
    	TextView tv_classObj;
    	TextView tv_name;
    	TextView tv_gender;
    	TextView tv_birthDate;
    	ImageView iv_studentPhoto;
    	TextView tv_telephone;
    }
} 
