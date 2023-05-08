package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.HomeworkTask;
import com.mobileclient.service.HomeworkTaskService;
import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class HomeworkTaskDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_homeworkId;
	// 声明老师控件
	private TextView TV_teacherObj;
	// 声明作业标题控件
	private TextView TV_title;
	// 声明作业内容控件
	private TextView TV_content;
	// 声明发布时间控件
	private TextView TV_addTime;
	/* 要保存的作业任务信息 */
	HomeworkTask homeworkTask = new HomeworkTask(); 
	/* 作业任务管理业务逻辑层 */
	private HomeworkTaskService homeworkTaskService = new HomeworkTaskService();
	private TeacherService teacherService = new TeacherService();
	private int homeworkId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.homeworktask_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看作业任务详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_homeworkId = (TextView) findViewById(R.id.TV_homeworkId);
		TV_teacherObj = (TextView) findViewById(R.id.TV_teacherObj);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		Bundle extras = this.getIntent().getExtras();
		homeworkId = extras.getInt("homeworkId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HomeworkTaskDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    homeworkTask = homeworkTaskService.GetHomeworkTask(homeworkId); 
		this.TV_homeworkId.setText(homeworkTask.getHomeworkId() + "");
		Teacher teacherObj = teacherService.GetTeacher(homeworkTask.getTeacherObj());
		this.TV_teacherObj.setText(teacherObj.getName());
		this.TV_title.setText(homeworkTask.getTitle());
		this.TV_content.setText(homeworkTask.getContent());
		this.TV_addTime.setText(homeworkTask.getAddTime());
	} 
}
