package com.mobileclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("�ֻ��ͻ���-������");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        AnimationSet set = new AnimationSet(false);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(500);
        set.addAnimation(animation);
        
        animation = new TranslateAnimation(1, 13, 10, 50);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new RotateAnimation(30,10);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        animation = new ScaleAnimation(5,0,2,0);
        animation.setDuration(300);
        set.addAnimation(animation);
        
        LayoutAnimationController controller = new LayoutAnimationController(set, 1);
        
        gridview.setLayoutAnimation(controller);
        
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // �̳�BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	
    	LayoutInflater inflater;
    	
    	// ������
        private Context mContext;
        
        // ͼƬ��Դ����
        private Integer[] mThumbIds = {
                R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon,R.drawable.operateicon
        };
        private String[] menuString = {"ʵ���ҹ���","ʵ����״̬����","�༶����","ѧ������","��ʦ����","ʵ��γ̹���","�γ�ʵ�����","����Ϣ����","ʵ���豸����"};

        // ���췽��
        public ImageAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);
        }
        // �������
        public int getCount() {
            return mThumbIds.length;
        }
        // ��ǰ���
        public Object getItem(int position) {
            return null;
        }
        // ��ǰ���id
        public long getItemId(int position) {
            return 0;
        }
        // ��õ�ǰ��ͼ
        public View getView(int position, View convertView, ViewGroup parent) { 
        	View view = inflater.inflate(R.layout.gv_item, null);
			TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
			ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);  
			tv.setText(menuString[position]); 
			iv.setImageResource(mThumbIds[position]); 
			  switch (position) {
				case 0:
					// ʵ���ҹ��������
					view.setOnClickListener(labInfoLinstener);
					break;
				case 1:
					// ʵ����״̬���������
					view.setOnClickListener(latStateLinstener);
					break;
				case 2:
					// �༶���������
					view.setOnClickListener(classInfoLinstener);
					break;
				case 3:
					// ѧ�����������
					view.setOnClickListener(studentLinstener);
					break;
				case 4:
					// ��ʦ���������
					view.setOnClickListener(teacherLinstener);
					break;
				case 5:
					// ʵ��γ̹��������
					view.setOnClickListener(courseLinstener);
					break;
				case 6:
					// �γ�ʵ����������
					view.setOnClickListener(courseTestLinstener);
					break;
				case 7:
					// ����Ϣ���������
					view.setOnClickListener(weekInfoLinstener);
					break;
				case 8:
					// ʵ���豸���������
					view.setOnClickListener(deviceLinstener);
					break;

			 
				default:
					break;
				} 
			return view; 
        }
       
    }
    
    OnClickListener labInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ����ʵ���ҹ���Activity
			intent.setClass(MainMenuActivity.this, LabInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener latStateLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ����ʵ����״̬����Activity
			intent.setClass(MainMenuActivity.this, LatStateListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener classInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����༶����Activity
			intent.setClass(MainMenuActivity.this, ClassInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener studentLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ����ѧ������Activity
			intent.setClass(MainMenuActivity.this, StudentListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener teacherLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ������ʦ����Activity
			intent.setClass(MainMenuActivity.this, TeacherListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener courseLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ����ʵ��γ̹���Activity
			intent.setClass(MainMenuActivity.this, CourseListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener courseTestLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����γ�ʵ�����Activity
			intent.setClass(MainMenuActivity.this, CourseTestListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener weekInfoLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Ϣ����Activity
			intent.setClass(MainMenuActivity.this, WeekInfoListActivity.class);
			startActivity(intent);
		}
	};
    OnClickListener deviceLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// ����ʵ���豸����Activity
			intent.setClass(MainMenuActivity.this, DeviceListActivity.class);
			startActivity(intent);
		}
	};


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "���µ���");  
		menu.add(0, 2, 2, "�˳�"); 
		return super.onCreateOptionsMenu(menu); 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//���µ��� 
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//�˳�
			System.exit(0);  
		} 
		return true; 
	}
}
