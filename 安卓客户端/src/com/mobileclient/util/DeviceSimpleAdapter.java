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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.device_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_deviceName = (TextView)convertView.findViewById(R.id.tv_deviceName);
	  holder.tv_labObj = (TextView)convertView.findViewById(R.id.tv_labObj);
	  holder.iv_devicePhoto = (ImageView)convertView.findViewById(R.id.iv_devicePhoto);
	  holder.tv_devicePrice = (TextView)convertView.findViewById(R.id.tv_devicePrice);
	  holder.tv_deviceCount = (TextView)convertView.findViewById(R.id.tv_deviceCount);
	  /*设置各个控件的展示内容*/
	  holder.tv_deviceName.setText("设备名称：" + mData.get(position).get("deviceName").toString());
	  holder.tv_labObj.setText("所属实验室：" + (new LabInfoService()).GetLabInfo(Integer.parseInt(mData.get(position).get("labObj").toString())).getLabName());
	  holder.iv_devicePhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener devicePhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_devicePhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("devicePhoto"),devicePhotoLoadListener);  
	  holder.tv_devicePrice.setText("设备价格：" + mData.get(position).get("devicePrice").toString());
	  holder.tv_deviceCount.setText("设备数量：" + mData.get(position).get("deviceCount").toString());
	  /*返回修改好的view*/
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
