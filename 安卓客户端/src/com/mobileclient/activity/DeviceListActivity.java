package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Device;
import com.mobileclient.service.DeviceService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.DeviceSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DeviceListActivity extends Activity {
	DeviceSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int deviceId;
	/* ʵ���豸����ҵ���߼������ */
	DeviceService deviceService = new DeviceService();
	/*�����ѯ����������ʵ���豸����*/
	private Device queryConditionDevice;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.device_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DeviceListActivity.this, DeviceQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		 
		
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("ʵ���豸��ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DeviceListActivity.this, DeviceAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionDevice = (Device)extras.getSerializable("queryConditionDevice");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionDevice = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new DeviceSimpleAdapter(DeviceListActivity.this, list,
	        					R.layout.device_list_item,
	        					new String[] { "deviceName","labObj","devicePhoto","devicePrice","deviceCount" },
	        					new int[] { R.id.tv_deviceName,R.id.tv_labObj,R.id.iv_devicePhoto,R.id.tv_devicePrice,R.id.tv_deviceCount,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(deviceListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int deviceId = Integer.parseInt(list.get(arg2).get("deviceId").toString());
            	Intent intent = new Intent();
            	intent.setClass(DeviceListActivity.this, DeviceDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("deviceId", deviceId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener deviceListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭ʵ���豸��Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ʵ���豸��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ʵ���豸��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�豸id
			deviceId = Integer.parseInt(list.get(position).get("deviceId").toString());
			Intent intent = new Intent();
			intent.setClass(DeviceListActivity.this, DeviceEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("deviceId", deviceId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��ʵ���豸��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�豸id
			deviceId = Integer.parseInt(list.get(position).get("deviceId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(DeviceListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = deviceService.DeleteDevice(deviceId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯʵ���豸��Ϣ */
			List<Device> deviceList = deviceService.QueryDevice(queryConditionDevice);
			for (int i = 0; i < deviceList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("deviceId",deviceList.get(i).getDeviceId());
				map.put("deviceName", deviceList.get(i).getDeviceName());
				map.put("labObj", deviceList.get(i).getLabObj());
				/*byte[] devicePhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ deviceList.get(i).getDevicePhoto());// ��ȡͼƬ����
				BitmapFactory.Options devicePhoto_opts = new BitmapFactory.Options();  
				devicePhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(devicePhoto_data, 0, devicePhoto_data.length, devicePhoto_opts); 
				devicePhoto_opts.inSampleSize = photoListActivity.computeSampleSize(devicePhoto_opts, -1, 100*100); 
				devicePhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap devicePhoto = BitmapFactory.decodeByteArray(devicePhoto_data, 0, devicePhoto_data.length, devicePhoto_opts);
					map.put("devicePhoto", devicePhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("devicePhoto", HttpUtil.BASE_URL+ deviceList.get(i).getDevicePhoto());
				map.put("devicePrice", deviceList.get(i).getDevicePrice());
				map.put("deviceCount", deviceList.get(i).getDeviceCount());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
