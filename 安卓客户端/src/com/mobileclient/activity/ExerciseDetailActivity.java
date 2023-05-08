package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Exercise;
import com.mobileclient.service.ExerciseService;
import com.mobileclient.domain.Chapter;
import com.mobileclient.service.ChapterService;
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
public class ExerciseDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_id;
	// 声明习题名称控件
	private TextView TV_title;
	// 声明所在章控件
	private TextView TV_chapterId;
	// 声明练习内容控件
	private TextView TV_content;
	// 声明加入时间控件
	private TextView TV_addTime;
	/* 要保存的习题信息信息 */
	Exercise exercise = new Exercise(); 
	/* 习题信息管理业务逻辑层 */
	private ExerciseService exerciseService = new ExerciseService();
	private ChapterService chapterService = new ChapterService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.exercise_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看习题信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_chapterId = (TextView) findViewById(R.id.TV_chapterId);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ExerciseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    exercise = exerciseService.GetExercise(id); 
		this.TV_id.setText(exercise.getId() + "");
		this.TV_title.setText(exercise.getTitle());
		Chapter chapterId = chapterService.GetChapter(exercise.getChapterId());
		this.TV_chapterId.setText(chapterId.getTitle());
		this.TV_content.setText(exercise.getContent());
		this.TV_addTime.setText(exercise.getAddTime());
	} 
}
