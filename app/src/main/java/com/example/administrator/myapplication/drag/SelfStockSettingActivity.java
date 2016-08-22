package com.example.administrator.myapplication.drag;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.Model;
import com.example.administrator.myapplication.R;

public class SelfStockSettingActivity extends AppCompatActivity{

	private List<Model> dataList = new ArrayList<Model>();// 数据源
	private DragListAdapter mDragListAdapter = null;// 适配器
	private DragListView dragListView;// 自定义组件

	private TextView txtComplete;

	private int mStartPosition, mEndPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_stock_setting);
		findViewById(R.id.invitate_back1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		initView();
//		NetInterface.requestOptionalShareList(mHandler, WHAT_REFRESH_VIDEO);
//		showBusyDialog();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		initData();
		dragListView = (DragListView) findViewById(R.id.other_drag_list);
		mDragListAdapter = new DragListAdapter(this, dataList);
		dragListView.setAdapter(mDragListAdapter);
		dragListView.setDragOnItemClickListener(new DragListView.DragOnItemClickListener() {
			@Override
			public void onItemClickListener(int position) {
			}
		});
		txtComplete = (TextView) findViewById(R.id.txt_complete);

		txtComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		mDragListAdapter.setOnItemRemoveListener(new DragListAdapter.OnItemRemoveListener() {

			@Override
			public void onItemRemove(int postion, Model stock) {
				// TODO Auto-generated method stub
//				if (isNetworkAvailble()) {
//					remove(stock.getCode());
					mDragListAdapter.removeItem(postion);
//				} else {
//					Toast.makeText(getApplicationContext(), "无可用网络",
//							Toast.LENGTH_LONG).show();
//				}
			}
		});

//		mDragListAdapter.setOnItemsSwitchListener(new DragListAdapter.OnItemsSwitchListener() {
//
//			@Override
//			public void onItemsSwitch(int startPosition, int endPosition) {
//				if (startPosition == endPosition) {
//					return;
//				}
////				if (isNetworkAvailble()) {
//					if (endPosition >= 0 && endPosition <= dataList.size() - 1
//							&& startPosition >= 0
//							&& startPosition <= dataList.size() - 1) {
//
//						Model stock = dataList.get(startPosition);
//
////						if (stock != null) {
////							switcwPos(stock.getCode(), endPosition);
//							mStartPosition = startPosition;
//							mEndPosition = endPosition;
////						}
//
////					}
//
//				} else {
//					Toast.makeText(getApplicationContext(), "无可用网络",
//							Toast.LENGTH_LONG).show();
//					mDragListAdapter.exchangeCopy(0, endPosition,
//							startPosition, true);
//				}
//
//			}
//		});

	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		// 数据结果
		dataList = MainActivity.initData;
	}

	private static final int WHAT_REMOVE = 100;
	private static final int WHAT_SWITCH = 101;
	private final int WHAT_REFRESH_VIDEO = 1000;
//	private NetResponseHandler2 mHandler = new NetResponseHandler2() {
//
//		@Override
//		public void receive(int arg0, boolean arg1, Intent arg2) {
//			// TODO Auto-generated method stub
//			switch (arg0) {
//			case WHAT_REFRESH_VIDEO:
//				dismissBusyDialog();
//				String list = arg2.getStringExtra("list");
//				if (!TextUtils.isEmpty(list)) {
//					String[] codes = list.split(",");
//					dataList.clear();
//					for (String code : codes) {
//						ItemStock stock = new ItemStock();
//						if (!TextUtils.isEmpty(code)) {
//							String[] msg = code.split("-");
//							stock.setCode(msg[0].toLowerCase());
//							if (msg.length >= 2) {
//								stock.setName(msg[1]);
//							}
//							if (msg.length == 3) {
//
//							}
//							dataList.add(stock);
//						}
//					}
//
//					// code数据和value等数据同时刷新
//					// mDragListAdapter.notifyDataSetChanged();
//					new MyAsyncTask().execute();
//				}
//				break;
//			case WHAT_REMOVE:
//				dismissBusyDialog();
//				if ("ez08.com.response".equalsIgnoreCase(arg2.getAction())) {
//					Toast.makeText(getApplicationContext(),
//							arg2.getStringExtra("msg"), Toast.LENGTH_LONG)
//							.show();
//				} else if ("ez08.znt.mystock.operate.p".equalsIgnoreCase(arg2
//						.getAction())) {
//					refreshData(arg2);
//				}
//				break;
//			case WHAT_SWITCH:
//				dismissBusyDialog();
//				if ("ez08.com.response".equalsIgnoreCase(arg2.getAction())) {
//					Toast.makeText(getApplicationContext(),
//							arg2.getStringExtra("msg"), Toast.LENGTH_LONG)
//							.show();
//				} else if ("ez08.znt.mystock.operate.p".equalsIgnoreCase(arg2
//						.getAction())) {
//					refreshData(arg2);
//				}
//
//				break;
//
//			default:
//				break;
//			}
//		}
//
//		public void timeout(int arg0) {
//			dismissBusyDialog();
//			Toast.makeText(getApplicationContext(), "请求超时", Toast.LENGTH_LONG)
//					.show();
//			if (arg0 == WHAT_SWITCH) {
//				mDragListAdapter.exchangeCopy(0, mStartPosition, mEndPosition,
//						false);
//			}
//		};
//
//		public void netConnectLost(int arg0) {
//			dismissBusyDialog();
//		};
//	};

	private void refreshData(Intent intent) {
		String list = intent.getStringExtra("list");
		dataList.clear();
		if (!TextUtils.isEmpty(list)) {
			String[] stocks = list.split(",");
			for (String code : stocks) {
				Model stock = new Model();
				String[] msg = code.split("-");
//				stock.setCode(msg[0].toLowerCase());
//				if (msg.length >= 2) {
//					stock.setName(msg[1]);
//				}
//				if (msg.length == 3) {
//
//				}
				dataList.add(stock);
			}

		}
		// mDragListAdapter.notifyDataSetChanged();
//		new MyAsyncTask().execute();
	}

//	private void remove(String secuCode) {
//		showBusyDialog();
//		NetInterface.selfStockManagment(secuCode, "-", -1, mHandler,
//				WHAT_REMOVE);
//	}
//
//	private void switcwPos(String secuCode, int newPos) {
//		showBusyDialog();
//		NetInterface.selfStockManagment(secuCode, ">", newPos, mHandler,
//				WHAT_SWITCH);
//		Log.e("", secuCode);
//	}

//	class MyAsyncTask extends AsyncTask<String, Integer, String> {
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			StockValueParse parser = new StockValueParse(dataList);
//			parser.parserResult(result);
//			mDragListAdapter.notifyDataSetChanged();
//
//		}
//
//		@Override
//		protected String doInBackground(String... arg0) {
//			// TODO Auto-generated method stub
//			String path = CompassApp.STOCK_VALUE_URL;
//			if (dataList.size() != 0) {
//				path = path + ",";
//			}
//			for (int i = 0; i < dataList.size(); i++) {
//				path = path + dataList.get(i).getCode();
//				if (i != dataList.size() - 1) {
//					path = path + ",";
//				}
//			}
//			String jsonString = HttpUtils.getJsonContent(path);
//			return jsonString;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//		}
//	};

//	ProgressDialog pDialog;

//	protected void showBusyDialog() {
//		pDialog = new ProgressDialog(this);
//		pDialog.setMessage("请稍候...");
//		pDialog.setCancelable(true);
//		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
//			pDialog.show();
//		} else {
//			runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					pDialog.show();
//				}
//			});
//		}
//	}
//
//	protected void dismissBusyDialog() {
//		if (pDialog != null) {
//			pDialog.dismiss();
//		}
//	}
}
