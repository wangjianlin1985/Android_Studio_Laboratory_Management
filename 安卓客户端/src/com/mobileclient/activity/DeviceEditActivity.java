package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class DeviceEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����豸idTextView
	private TextView TV_deviceId;
	// �����豸���������
	private EditText ET_deviceName;
	// ��������ʵ����������
	private Spinner spinner_labObj;
	private ArrayAdapter<String> labObj_adapter;
	private static  String[] labObj_ShowText  = null;
	private List<LabInfo> labInfoList = null;
	/*����ʵ���ҹ���ҵ���߼���*/
	private LabInfoService labInfoService = new LabInfoService();
	// �����豸ͼƬͼƬ��ؼ�
	private ImageView iv_devicePhoto;
	private Button btn_devicePhoto;
	protected int REQ_CODE_SELECT_IMAGE_devicePhoto = 1;
	private int REQ_CODE_CAMERA_devicePhoto = 2;
	// �����豸�۸������
	private EditText ET_devicePrice;
	// �����豸���������
	private EditText ET_deviceCount;
	// �����豸���������
	private EditText ET_deviceDesc;
	protected String carmera_path;
	/*Ҫ�����ʵ���豸��Ϣ*/
	Device device = new Device();
	/*ʵ���豸����ҵ���߼���*/
	private DeviceService deviceService = new DeviceService();

	private int deviceId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.device_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭ʵ���豸��Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_deviceId = (TextView) findViewById(R.id.TV_deviceId);
		ET_deviceName = (EditText) findViewById(R.id.ET_deviceName);
		spinner_labObj = (Spinner) findViewById(R.id.Spinner_labObj);
		// ��ȡ���е�����ʵ����
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
		// ����ѡ������ArrayAdapter��������
		labObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labObj_ShowText);
		// ����ͼ����������б�ķ��
		labObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_labObj.setAdapter(labObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_labObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				device.setLabObj(labInfoList.get(arg2).getLabId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_labObj.setVisibility(View.VISIBLE);
		iv_devicePhoto = (ImageView) findViewById(R.id.iv_devicePhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_devicePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DeviceEditActivity.this,photoListActivity.class);
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		deviceId = extras.getInt("deviceId");
		/*�����޸�ʵ���豸��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�豸����*/ 
					if(ET_deviceName.getText().toString().equals("")) {
						Toast.makeText(DeviceEditActivity.this, "�豸�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_deviceName.setFocusable(true);
						ET_deviceName.requestFocus();
						return;	
					}
					device.setDeviceName(ET_deviceName.getText().toString());
					if (!device.getDevicePhoto().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						DeviceEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String devicePhoto = HttpUtil.uploadFile(device.getDevicePhoto());
						DeviceEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						device.setDevicePhoto(devicePhoto);
					} 
					/*��֤��ȡ�豸�۸�*/ 
					if(ET_devicePrice.getText().toString().equals("")) {
						Toast.makeText(DeviceEditActivity.this, "�豸�۸����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_devicePrice.setFocusable(true);
						ET_devicePrice.requestFocus();
						return;	
					}
					device.setDevicePrice(Float.parseFloat(ET_devicePrice.getText().toString()));
					/*��֤��ȡ�豸����*/ 
					if(ET_deviceCount.getText().toString().equals("")) {
						Toast.makeText(DeviceEditActivity.this, "�豸�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_deviceCount.setFocusable(true);
						ET_deviceCount.requestFocus();
						return;	
					}
					device.setDeviceCount(Integer.parseInt(ET_deviceCount.getText().toString()));
					/*��֤��ȡ�豸����*/ 
					if(ET_deviceDesc.getText().toString().equals("")) {
						Toast.makeText(DeviceEditActivity.this, "�豸�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_deviceDesc.setFocusable(true);
						ET_deviceDesc.requestFocus();
						return;	
					}
					device.setDeviceDesc(ET_deviceDesc.getText().toString());
					/*����ҵ���߼����ϴ�ʵ���豸��Ϣ*/
					DeviceEditActivity.this.setTitle("���ڸ���ʵ���豸��Ϣ���Ե�...");
					String result = deviceService.UpdateDevice(device);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    device = deviceService.GetDevice(deviceId);
		this.TV_deviceId.setText(deviceId+"");
		this.ET_deviceName.setText(device.getDeviceName());
		for (int i = 0; i < labInfoList.size(); i++) {
			if (device.getLabObj() == labInfoList.get(i).getLabId()) {
				this.spinner_labObj.setSelection(i);
				break;
			}
		}
		byte[] devicePhoto_data = null;
		try {
			// ��ȡͼƬ����
			devicePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + device.getDevicePhoto());
			Bitmap devicePhoto = BitmapFactory.decodeByteArray(devicePhoto_data, 0, devicePhoto_data.length);
			this.iv_devicePhoto.setImageBitmap(devicePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_devicePrice.setText(device.getDevicePrice() + "");
		this.ET_deviceCount.setText(device.getDeviceCount() + "");
		this.ET_deviceDesc.setText(device.getDeviceDesc());
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
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
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
