package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.Chapter;
import com.mobileclient.service.ChapterService;
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

public class ExerciseEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_id;
	// 声明习题名称输入框
	private EditText ET_title;
	// 声明所在章下拉框
	private Spinner spinner_chapterId;
	private ArrayAdapter<String> chapterId_adapter;
	private static  String[] chapterId_ShowText  = null;
	private List<Chapter> chapterList = null;
	/*所在章管理业务逻辑层*/
	private ChapterService chapterService = new ChapterService();
	// 声明练习内容输入框
	private EditText ET_content;
	// 声明加入时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的习题信息信息*/
	Exercise exercise = new Exercise();
	/*习题信息管理业务逻辑层*/
	private ExerciseService exerciseService = new ExerciseService();

	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑习题信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_id = (TextView) findViewById(R.id.TV_id);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_chapterId = (Spinner) findViewById(R.id.Spinner_chapterId);
		// 获取所有的所在章
		try {
			chapterList = chapterService.QueryChapter(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int chapterCount = chapterList.size();
		chapterId_ShowText = new String[chapterCount];
		for(int i=0;i<chapterCount;i++) { 
			chapterId_ShowText[i] = chapterList.get(i).getTitle();
		}
		// 将可选内容与ArrayAdapter连接起来
		chapterId_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, chapterId_ShowText);
		// 设置图书类别下拉列表的风格
		chapterId_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_chapterId.setAdapter(chapterId_adapter);
		// 添加事件Spinner事件监听
		spinner_chapterId.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				exercise.setChapterId(chapterList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_chapterId.setVisibility(View.VISIBLE);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		/*单击修改习题信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取习题名称*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(ExerciseEditActivity.this, "习题名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					exercise.setTitle(ET_title.getText().toString());
					/*验证获取练习内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(ExerciseEditActivity.this, "练习内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					exercise.setContent(ET_content.getText().toString());
					/*验证获取加入时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(ExerciseEditActivity.this, "加入时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					exercise.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传习题信息信息*/
					ExerciseEditActivity.this.setTitle("正在更新习题信息信息，稍等...");
					String result = exerciseService.UpdateExercise(exercise);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    exercise = exerciseService.GetExercise(id);
		this.TV_id.setText(id+"");
		this.ET_title.setText(exercise.getTitle());
		for (int i = 0; i < chapterList.size(); i++) {
			if (exercise.getChapterId() == chapterList.get(i).getId()) {
				this.spinner_chapterId.setSelection(i);
				break;
			}
		}
		this.ET_content.setText(exercise.getContent());
		this.ET_addTime.setText(exercise.getAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
