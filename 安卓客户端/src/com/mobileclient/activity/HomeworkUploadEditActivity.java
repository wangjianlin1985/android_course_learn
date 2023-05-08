package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.HomeworkUpload;
import com.mobileclient.service.HomeworkUploadService;
import com.mobileclient.domain.HomeworkTask;
import com.mobileclient.service.HomeworkTaskService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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

public class HomeworkUploadEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_uploadId;
	// 声明作业标题下拉框
	private Spinner spinner_homeworkTaskObj;
	private ArrayAdapter<String> homeworkTaskObj_adapter;
	private static  String[] homeworkTaskObj_ShowText  = null;
	private List<HomeworkTask> homeworkTaskList = null;
	/*作业标题管理业务逻辑层*/
	private HomeworkTaskService homeworkTaskService = new HomeworkTaskService();
	// 声明提交的学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*提交的学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明作业文件图片框控件
	private ImageView iv_homeworkFile;
	private Button btn_homeworkFile;
	protected int REQ_CODE_SELECT_IMAGE_homeworkFile = 1;
	private int REQ_CODE_CAMERA_homeworkFile = 2;
	// 声明提交时间输入框
	private EditText ET_uploadTime;
	// 声明批改结果文件图片框控件
	private ImageView iv_resultFile;
	private Button btn_resultFile;
	protected int REQ_CODE_SELECT_IMAGE_resultFile = 3;
	private int REQ_CODE_CAMERA_resultFile = 4;
	// 声明批改时间输入框
	private EditText ET_pigaiTime;
	// 声明是否批改输入框
	private EditText ET_pigaiFlag;
	// 声明评语输入框
	private EditText ET_pingyu;
	protected String carmera_path;
	/*要保存的上传的作业信息*/
	HomeworkUpload homeworkUpload = new HomeworkUpload();
	/*上传的作业管理业务逻辑层*/
	private HomeworkUploadService homeworkUploadService = new HomeworkUploadService();

	private int uploadId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.homeworkupload_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑上传的作业信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_uploadId = (TextView) findViewById(R.id.TV_uploadId);
		spinner_homeworkTaskObj = (Spinner) findViewById(R.id.Spinner_homeworkTaskObj);
		// 获取所有的作业标题
		try {
			homeworkTaskList = homeworkTaskService.QueryHomeworkTask(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int homeworkTaskCount = homeworkTaskList.size();
		homeworkTaskObj_ShowText = new String[homeworkTaskCount];
		for(int i=0;i<homeworkTaskCount;i++) { 
			homeworkTaskObj_ShowText[i] = homeworkTaskList.get(i).getTitle();
		}
		// 将可选内容与ArrayAdapter连接起来
		homeworkTaskObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, homeworkTaskObj_ShowText);
		// 设置图书类别下拉列表的风格
		homeworkTaskObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_homeworkTaskObj.setAdapter(homeworkTaskObj_adapter);
		// 添加事件Spinner事件监听
		spinner_homeworkTaskObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				homeworkUpload.setHomeworkTaskObj(homeworkTaskList.get(arg2).getHomeworkId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_homeworkTaskObj.setVisibility(View.VISIBLE);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的提交的学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置图书类别下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				homeworkUpload.setStudentObj(studentList.get(arg2).getStudentNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		iv_homeworkFile = (ImageView) findViewById(R.id.iv_homeworkFile);
		/*单击图片显示控件时进行图片的选择*/
		iv_homeworkFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HomeworkUploadEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_homeworkFile);
			}
		});
		btn_homeworkFile = (Button) findViewById(R.id.btn_homeworkFile);
		btn_homeworkFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_homeworkFile.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_homeworkFile);  
			}
		});
		ET_uploadTime = (EditText) findViewById(R.id.ET_uploadTime);
		iv_resultFile = (ImageView) findViewById(R.id.iv_resultFile);
		/*单击图片显示控件时进行图片的选择*/
		iv_resultFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HomeworkUploadEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_resultFile);
			}
		});
		btn_resultFile = (Button) findViewById(R.id.btn_resultFile);
		btn_resultFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_resultFile.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_resultFile);  
			}
		});
		ET_pigaiTime = (EditText) findViewById(R.id.ET_pigaiTime);
		ET_pigaiFlag = (EditText) findViewById(R.id.ET_pigaiFlag);
		ET_pingyu = (EditText) findViewById(R.id.ET_pingyu);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		uploadId = extras.getInt("uploadId");
		/*单击修改上传的作业按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (!homeworkUpload.getHomeworkFile().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						HomeworkUploadEditActivity.this.setTitle("正在上传图片，稍等...");
						String homeworkFile = HttpUtil.uploadFile(homeworkUpload.getHomeworkFile());
						HomeworkUploadEditActivity.this.setTitle("图片上传完毕！");
						homeworkUpload.setHomeworkFile(homeworkFile);
					} 
					/*验证获取提交时间*/ 
					if(ET_uploadTime.getText().toString().equals("")) {
						Toast.makeText(HomeworkUploadEditActivity.this, "提交时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_uploadTime.setFocusable(true);
						ET_uploadTime.requestFocus();
						return;	
					}
					homeworkUpload.setUploadTime(ET_uploadTime.getText().toString());
					if (!homeworkUpload.getResultFile().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						HomeworkUploadEditActivity.this.setTitle("正在上传图片，稍等...");
						String resultFile = HttpUtil.uploadFile(homeworkUpload.getResultFile());
						HomeworkUploadEditActivity.this.setTitle("图片上传完毕！");
						homeworkUpload.setResultFile(resultFile);
					} 
					/*验证获取批改时间*/ 
					if(ET_pigaiTime.getText().toString().equals("")) {
						Toast.makeText(HomeworkUploadEditActivity.this, "批改时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_pigaiTime.setFocusable(true);
						ET_pigaiTime.requestFocus();
						return;	
					}
					homeworkUpload.setPigaiTime(ET_pigaiTime.getText().toString());
					/*验证获取是否批改*/ 
					if(ET_pigaiFlag.getText().toString().equals("")) {
						Toast.makeText(HomeworkUploadEditActivity.this, "是否批改输入不能为空!", Toast.LENGTH_LONG).show();
						ET_pigaiFlag.setFocusable(true);
						ET_pigaiFlag.requestFocus();
						return;	
					}
					homeworkUpload.setPigaiFlag(Integer.parseInt(ET_pigaiFlag.getText().toString()));
					/*验证获取评语*/ 
					if(ET_pingyu.getText().toString().equals("")) {
						Toast.makeText(HomeworkUploadEditActivity.this, "评语输入不能为空!", Toast.LENGTH_LONG).show();
						ET_pingyu.setFocusable(true);
						ET_pingyu.requestFocus();
						return;	
					}
					homeworkUpload.setPingyu(ET_pingyu.getText().toString());
					/*调用业务逻辑层上传上传的作业信息*/
					HomeworkUploadEditActivity.this.setTitle("正在更新上传的作业信息，稍等...");
					String result = homeworkUploadService.UpdateHomeworkUpload(homeworkUpload);
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
	    homeworkUpload = homeworkUploadService.GetHomeworkUpload(uploadId);
		this.TV_uploadId.setText(uploadId+"");
		for (int i = 0; i < homeworkTaskList.size(); i++) {
			if (homeworkUpload.getHomeworkTaskObj() == homeworkTaskList.get(i).getHomeworkId()) {
				this.spinner_homeworkTaskObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < studentList.size(); i++) {
			if (homeworkUpload.getStudentObj().equals(studentList.get(i).getStudentNumber())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		byte[] homeworkFile_data = null;
		try {
			// 获取图片数据
			homeworkFile_data = ImageService.getImage(HttpUtil.BASE_URL + homeworkUpload.getHomeworkFile());
			Bitmap homeworkFile = BitmapFactory.decodeByteArray(homeworkFile_data, 0, homeworkFile_data.length);
			this.iv_homeworkFile.setImageBitmap(homeworkFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_uploadTime.setText(homeworkUpload.getUploadTime());
		byte[] resultFile_data = null;
		try {
			// 获取图片数据
			resultFile_data = ImageService.getImage(HttpUtil.BASE_URL + homeworkUpload.getResultFile());
			Bitmap resultFile = BitmapFactory.decodeByteArray(resultFile_data, 0, resultFile_data.length);
			this.iv_resultFile.setImageBitmap(resultFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_pigaiTime.setText(homeworkUpload.getPigaiTime());
		this.ET_pigaiFlag.setText(homeworkUpload.getPigaiFlag() + "");
		this.ET_pingyu.setText(homeworkUpload.getPingyu());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_homeworkFile  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_homeworkFile.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_homeworkFile.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_homeworkFile.setImageBitmap(booImageBm);
				this.iv_homeworkFile.setScaleType(ScaleType.FIT_CENTER);
				this.homeworkUpload.setHomeworkFile(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_homeworkFile && resultCode == Activity.RESULT_OK) {
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
				this.iv_homeworkFile.setImageBitmap(bm); 
				this.iv_homeworkFile.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			homeworkUpload.setHomeworkFile(filename); 
		}
		if (requestCode == REQ_CODE_CAMERA_resultFile  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_resultFile.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_resultFile.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_resultFile.setImageBitmap(booImageBm);
				this.iv_resultFile.setScaleType(ScaleType.FIT_CENTER);
				this.homeworkUpload.setResultFile(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_resultFile && resultCode == Activity.RESULT_OK) {
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
				this.iv_resultFile.setImageBitmap(bm); 
				this.iv_resultFile.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			homeworkUpload.setResultFile(filename); 
		}
	}
}
