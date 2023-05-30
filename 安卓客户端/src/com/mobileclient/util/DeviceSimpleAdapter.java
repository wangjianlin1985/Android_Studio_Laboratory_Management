package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.LabInfoService;
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

public class DeviceSimpleAdapter extends SimpleAdapter { 
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

    public DeviceSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.device_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_deviceName = (TextView)convertView.findViewById(R.id.tv_deviceName);
	  holder.tv_labObj = (TextView)convertView.findViewById(R.id.tv_labObj);
	  holder.iv_devicePhoto = (ImageView)convertView.findViewById(R.id.iv_devicePhoto);
	  holder.tv_devicePrice = (TextView)convertView.findViewById(R.id.tv_devicePrice);
	  holder.tv_deviceCount = (TextView)convertView.findViewById(R.id.tv_deviceCount);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_deviceName.setText("�豸���ƣ�" + mData.get(position).get("deviceName").toString());
	  holder.tv_labObj.setText("����ʵ���ң�" + (new LabInfoService()).GetLabInfo(Integer.parseInt(mData.get(position).get("labObj").toString())).getLabName());
	  holder.iv_devicePhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener devicePhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_devicePhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("devicePhoto"),devicePhotoLoadListener);  
	  holder.tv_devicePrice.setText("�豸�۸�" + mData.get(position).get("devicePrice").toString());
	  holder.tv_deviceCount.setText("�豸������" + mData.get(position).get("deviceCount").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_deviceName;
    	TextView tv_labObj;
    	ImageView iv_devicePhoto;
    	TextView tv_devicePrice;
    	TextView tv_deviceCount;
    }
} 
