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
	// �������ذ�ť
	private Button btnReturn;
	// �����豸id�ؼ�
	private TextView TV_deviceId;
	// �����豸���ƿؼ�
	private TextView TV_deviceName;
	// ��������ʵ���ҿؼ�
	private TextView TV_labObj;
	// �����豸ͼƬͼƬ��
	private ImageView iv_devicePhoto;
	// �����豸�۸�ؼ�
	private TextView TV_devicePrice;
	// �����豸�����ؼ�
	private TextView TV_deviceCount;
	// �����豸�����ؼ�
	private TextView TV_deviceDesc;
	/* Ҫ�����ʵ���豸��Ϣ */
	Device device = new Device(); 
	/* ʵ���豸����ҵ���߼��� */
	private DeviceService deviceService = new DeviceService();
	private LabInfoService labInfoService = new LabInfoService();
	private int deviceId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.device_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴ʵ���豸����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    device = deviceService.GetDevice(deviceId); 
		this.TV_deviceId.setText(device.getDeviceId() + "");
		this.TV_deviceName.setText(device.getDeviceName());
		LabInfo labObj = labInfoService.GetLabInfo(device.getLabObj());
		this.TV_labObj.setText(labObj.getLabName());
		byte[] devicePhoto_data = null;
		try {
			// ��ȡͼƬ����
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
