package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Device;
import com.mobileclient.service.DeviceService;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class DeviceDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明设备id控件
	private TextView TV_deviceId;
	// 声明设备名称控件
	private TextView TV_deviceName;
	// 声明所属实验室控件
	private TextView TV_labObj;
	// 声明设备图片图片框
	private ImageView iv_devicePhoto;
	// 声明设备价格控件
	private TextView TV_devicePrice;
	// 声明设备数量控件
	private TextView TV_deviceCount;
	// 声明设备描述控件
	private TextView TV_deviceDesc;
	/* 要保存的实验设备信息 */
	Device device = new Device(); 
	/* 实验设备管理业务逻辑层 */
	private DeviceService deviceService = new DeviceService();
	private LabInfoService labInfoService = new LabInfoService();
	private int deviceId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.device_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看实验设备详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_deviceId = (TextView) findViewById(R.id.TV_deviceId);
		TV_deviceName = (TextView) findViewById(R.id.TV_deviceName);
		TV_labObj = (TextView) findViewById(R.id.TV_labObj);
		iv_devicePhoto = (ImageView) findViewById(R.id.iv_devicePhoto); 
		TV_devicePrice = (TextView) findViewById(R.id.TV_devicePrice);
		TV_deviceCount = (TextView) findViewById(R.id.TV_deviceCount);
		TV_deviceDesc = (TextView) findViewById(R.id.TV_deviceDesc);
		Bundle extras = this.getIntent().getExtras();
		deviceId = extras.getInt("deviceId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DeviceDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    device = deviceService.GetDevice(deviceId); 
		this.TV_deviceId.setText(device.getDeviceId() + "");
		this.TV_deviceName.setText(device.getDeviceName());
		LabInfo labObj = labInfoService.GetLabInfo(device.getLabObj());
		this.TV_labObj.setText(labObj.getLabName());
		byte[] devicePhoto_data = null;
		try {
			// 获取图片数据
			devicePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + device.getDevicePhoto());
			Bitmap devicePhoto = BitmapFactory.decodeByteArray(devicePhoto_data, 0,devicePhoto_data.length);
			this.iv_devicePhoto.setImageBitmap(devicePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_devicePrice.setText(device.getDevicePrice() + "");
		this.TV_deviceCount.setText(device.getDeviceCount() + "");
		this.TV_deviceDesc.setText(device.getDeviceDesc());
	} 
}
