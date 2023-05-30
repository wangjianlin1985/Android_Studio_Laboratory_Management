package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.LatStateService;
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

public class LabInfoSimpleAdapter extends SimpleAdapter { 
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

    public LabInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.labinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_labName = (TextView)convertView.findViewById(R.id.tv_labName);
	  holder.iv_labPhoto = (ImageView)convertView.findViewById(R.id.iv_labPhoto);
	  holder.tv_personNum = (TextView)convertView.findViewById(R.id.tv_personNum);
	  holder.tv_labAddress = (TextView)convertView.findViewById(R.id.tv_labAddress);
	  holder.tv_labStateObj = (TextView)convertView.findViewById(R.id.tv_labStateObj);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_labName.setText("ʵ�������ƣ�" + mData.get(position).get("labName").toString());
	  holder.iv_labPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener labPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_labPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("labPhoto"),labPhotoLoadListener);  
	  holder.tv_personNum.setText("����������" + mData.get(position).get("personNum").toString());
	  holder.tv_labAddress.setText("ʵ���ҵ�ַ��" + mData.get(position).get("labAddress").toString());
	  holder.tv_labStateObj.setText("ʵ����״̬��" + (new LatStateService()).GetLatState(mData.get(position).get("labStateObj").toString()).getStateName());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_labName;
    	ImageView iv_labPhoto;
    	TextView tv_personNum;
    	TextView tv_labAddress;
    	TextView tv_labStateObj;
    }
} 
