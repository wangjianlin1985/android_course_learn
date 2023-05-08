package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.HomeworkUpload;
import com.mobileclient.service.HomeworkUploadService;
import com.mobileclient.domain.HomeworkTask;
import com.mobileclient.service.HomeworkTaskService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
public class HomeworkUploadDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_uploadId;
	// 声明作业标题控件
	private TextView TV_homeworkTaskObj;
	// 声明提交的学生控件
	private TextView TV_studentObj;
	// 声明作业文件图片框
	private ImageView iv_homeworkFile;
	// 声明提交时间控件
	private TextView TV_uploadTime;
	// 声明批改结果文件图片框
	private ImageView iv_resultFile;
	// 声明批改时间控件
	private TextView TV_pigaiTime;
	// 声明是否批改控件
	private TextView TV_pigaiFlag;
	// 声明评语控件
	private TextView TV_pingyu;
	/* 要保存的上传的作业信息 */
	HomeworkUpload homeworkUpload = new HomeworkUpload(); 
	/* 上传的作业管理业务逻辑层 */
	private HomeworkUploadService homeworkUploadService = new HomeworkUploadService();
	private HomeworkTaskService homeworkTaskService = new HomeworkTaskService();
	private StudentService studentService = new StudentService();
	private int uploadId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.homeworkupload_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看上传的作业详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_uploadId = (TextView) findViewById(R.id.TV_uploadId);
		TV_homeworkTaskObj = (TextView) findViewById(R.id.TV_homeworkTaskObj);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		iv_homeworkFile = (ImageView) findViewById(R.id.iv_homeworkFile); 
		TV_uploadTime = (TextView) findViewById(R.id.TV_uploadTime);
		iv_resultFile = (ImageView) findViewById(R.id.iv_resultFile); 
		TV_pigaiTime = (TextView) findViewById(R.id.TV_pigaiTime);
		TV_pigaiFlag = (TextView) findViewById(R.id.TV_pigaiFlag);
		TV_pingyu = (TextView) findViewById(R.id.TV_pingyu);
		Bundle extras = this.getIntent().getExtras();
		uploadId = extras.getInt("uploadId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HomeworkUploadDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    homeworkUpload = homeworkUploadService.GetHomeworkUpload(uploadId); 
		this.TV_uploadId.setText(homeworkUpload.getUploadId() + "");
		HomeworkTask homeworkTaskObj = homeworkTaskService.GetHomeworkTask(homeworkUpload.getHomeworkTaskObj());
		this.TV_homeworkTaskObj.setText(homeworkTaskObj.getTitle());
		Student studentObj = studentService.GetStudent(homeworkUpload.getStudentObj());
		this.TV_studentObj.setText(studentObj.getName());
		byte[] homeworkFile_data = null;
		try {
			// 获取图片数据
			homeworkFile_data = ImageService.getImage(HttpUtil.BASE_URL + homeworkUpload.getHomeworkFile());
			Bitmap homeworkFile = BitmapFactory.decodeByteArray(homeworkFile_data, 0,homeworkFile_data.length);
			this.iv_homeworkFile.setImageBitmap(homeworkFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_uploadTime.setText(homeworkUpload.getUploadTime());
		byte[] resultFile_data = null;
		try {
			// 获取图片数据
			resultFile_data = ImageService.getImage(HttpUtil.BASE_URL + homeworkUpload.getResultFile());
			Bitmap resultFile = BitmapFactory.decodeByteArray(resultFile_data, 0,resultFile_data.length);
			this.iv_resultFile.setImageBitmap(resultFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_pigaiTime.setText(homeworkUpload.getPigaiTime());
		this.TV_pigaiFlag.setText(homeworkUpload.getPigaiFlag() + "");
		this.TV_pingyu.setText(homeworkUpload.getPingyu());
	} 
}
