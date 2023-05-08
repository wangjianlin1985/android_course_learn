package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Video;
import com.mobileclient.service.VideoService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class VideoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明视频资料标题输入框
	private EditText ET_title;
	// 声明所属章下拉框
	private Spinner spinner_chapterId;
	private ArrayAdapter<String> chapterId_adapter;
	private static  String[] chapterId_ShowText  = null;
	private List<Chapter> chapterList = null;
	/*所属章管理业务逻辑层*/
	private ChapterService chapterService = new ChapterService();
	// 声明文件路径输入框
	private EditText ET_path;
	// 声明添加时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的视频信息信息*/
	Video video = new Video();
	/*视频信息管理业务逻辑层*/
	private VideoService videoService = new VideoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.video_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加视频信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_chapterId = (Spinner) findViewById(R.id.Spinner_chapterId);
		// 获取所有的所属章
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
		// 设置下拉列表的风格
		chapterId_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_chapterId.setAdapter(chapterId_adapter);
		// 添加事件Spinner事件监听
		spinner_chapterId.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				video.setChapterId(chapterList.get(arg2).getId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_chapterId.setVisibility(View.VISIBLE);
		ET_path = (EditText) findViewById(R.id.ET_path);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加视频信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取视频资料标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(VideoAddActivity.this, "视频资料标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					video.setTitle(ET_title.getText().toString());
					/*验证获取文件路径*/ 
					if(ET_path.getText().toString().equals("")) {
						Toast.makeText(VideoAddActivity.this, "文件路径输入不能为空!", Toast.LENGTH_LONG).show();
						ET_path.setFocusable(true);
						ET_path.requestFocus();
						return;	
					}
					video.setPath(ET_path.getText().toString());
					/*验证获取添加时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(VideoAddActivity.this, "添加时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					video.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传视频信息信息*/
					VideoAddActivity.this.setTitle("正在上传视频信息信息，稍等...");
					String result = videoService.AddVideo(video);
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
	}
}
