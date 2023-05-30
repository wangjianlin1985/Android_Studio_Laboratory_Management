package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Device;
import com.mobileclient.service.DeviceService;
import com.mobileclient.domain.LabInfo;
import com.mobileclient.service.LabInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class DeviceAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明设备名称输入框
	private EditText ET_deviceName;
	// 声明所属实验室下拉框
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null;
	/*所属实验室管理业务逻辑层*/
	private LabInfoService labInfoService = new LabInfoService();
	// 声明设备图片图片框控件
	private ImageView iv_devicePhoto;
	private Button btn_devicePhoto;
	protected int REQ_CODE_SELECT_IMAGE_devicePhoto = 1;
	private int REQ_CODE_CAMERA_devicePhoto = 2;
	// 声明设备价格输入框
	private EditText ET_devicePrice;
	// 声明设备数量输入框
	private EditText ET_deviceCount;
	// 声明设备描述输入框
	private EditText ET_deviceDesc;
	protected String carmera_path;
	/*要保存的实验设备信息*/
	Device device = new Device();
	/*实验设备管理业务逻辑层*/
	private DeviceService deviceService = new DeviceService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.device_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加实验设备");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_deviceName = (EditText) findViewById(R.id.ET_deviceName);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// 获取所有的所属实验室
		try {
			labInfoList = labInfoService.QueryLabInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int labInfoCount = labInfoList.size();
		labObj_ShowText = new String[labInfoCount];
		for(int i=0;i<labInfoCount;i++) { 
			labObj_ShowText[i] = labInfoList.get(i).getLabName();
		}
		// 将可选内容与ArrayAdapter连接起来
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// 设置下拉列表的风格
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_labObj.setAdapter(labObj_adapter);
		// 添加事件Spinner事件监听
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				device.setLabObj(labInfoList.get(arg2).getLabId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_labObj.setVisibility(View.VISIBLE);
		iv_devicePhoto = (ImageView) findViewById(R.id.iv_devicePhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_devicePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DeviceAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_devicePhoto);
			}
		});
		btn_devicePhoto = (Button) findViewById(R.id.btn_devicePhoto);
		btn_devicePhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_devicePhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_devicePhoto);  
			}
		});
		ET_devicePrice = (EditText) findViewById(R.id.ET_devicePrice);
		ET_deviceCount = (EditText) findViewById(R.id.ET_deviceCount);
		ET_deviceDesc = (EditText) findViewById(R.id.ET_deviceDesc);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加实验设备按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取设备名称*/ 
					if(ET_deviceName.getText().toString().equals("")) {
						Toast.makeText(DeviceAddActivity.this, "设备名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_deviceName.setFocusable(true);
						ET_deviceName.requestFocus();
						return;	
					}
					device.setDeviceName(ET_deviceName.getText().toString());
					if(device.getDevicePhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						DeviceAddActivity.this.setTitle("正在上传图片，稍等...");
						String devicePhoto = HttpUtil.uploadFile(device.getDevicePhoto());
						DeviceAddActivity.this.setTitle("图片上传完毕！");
						device.setDevicePhoto(devicePhoto);
					} else {
						device.setDevicePhoto("upload/noimage.jpg");
					}
					/*验证获取设备价格*/ 
					if(ET_devicePrice.getText().toString().equals("")) {
						Toast.makeText(DeviceAddActivity.this, "设备价格输入不能为空!", Toast.LENGTH_LONG).show();
						ET_devicePrice.setFocusable(true);
						ET_devicePrice.requestFocus();
						return;	
					}
					device.setDevicePrice(Float.parseFloat(ET_devicePrice.getText().toString()));
					/*验证获取设备数量*/ 
					if(ET_deviceCount.getText().toString().equals("")) {
						Toast.makeText(DeviceAddActivity.this, "设备数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_deviceCount.setFocusable(true);
						ET_deviceCount.requestFocus();
						return;	
					}
					device.setDeviceCount(Integer.parseInt(ET_deviceCount.getText().toString()));
					/*验证获取设备描述*/ 
					if(ET_deviceDesc.getText().toString().equals("")) {
						Toast.makeText(DeviceAddActivity.this, "设备描述输入不能为空!", Toast.LENGTH_LONG).show();
						ET_deviceDesc.setFocusable(true);
						ET_deviceDesc.requestFocus();
						return;	
					}
					device.setDeviceDesc(ET_deviceDesc.getText().toString());
					/*调用业务逻辑层上传实验设备信息*/
					DeviceAddActivity.this.setTitle("正在上传实验设备信息，稍等...");
					String result = deviceService.AddDevice(device);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_devicePhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_devicePhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_devicePhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_devicePhoto.setImageBitmap(booImageBm);
				this.iv_devicePhoto.setScaleType(ScaleType.FIT_CENTER);
				this.device.setDevicePhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_devicePhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_devicePhoto.setImageBitmap(bm); 
				this.iv_devicePhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			device.setDevicePhoto(filename); 
		}
	}
}
