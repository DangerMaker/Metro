package com.example.administrator.myapplication.drag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myapplication.Model;
import com.example.administrator.myapplication.R;

/***
 * 自定义可拖拽ListView适配器
 */
@SuppressLint("UseSparseArrays")
public class DragListAdapter extends BaseAdapter {

	private Context mContext; // 上下文
	public List<Model> dataList;// 数据源
	private Map<Integer, MyLinerLay> lins = new HashMap<Integer, MyLinerLay>();// 数据源
	private List<Model> copyList = new ArrayList<Model>();
	private int mInvisilePosition = -1;// 用来标记不可见Item的位置
	private boolean isChanged = true;// 是否发生改变标识
	private boolean mShowItem = false;// 是否显示拖拽Item的内容标识
	/**
	 * 移动方向的标记，-1为默认值，0表示向下移动，1表示向上移动
	 */
	private int mLastFlag = -1;
	private int mHeight;
	@SuppressWarnings("unused")
	private int mDragPosition = -1; // 拖动位置
	@SuppressWarnings("unused")
	private boolean isSameDragDirection = true;// 是否为相同方向拖动的标记

	/** DragListAdapter构造方法 */
	public DragListAdapter(Context context, List<Model> dataList) {
		this.mContext = context;
		this.dataList = dataList;
	}

	/** 获取Item总数 */
	@Override
	public int getCount() {
		return dataList.size();
	}

	/** 获取ListView中Item项 */
	@Override
	public MyLinerLay getItem(int position) {
		return lins.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 设置是否显示下降的Item
	 * 
	 * @param showItem
	 */
	public void showDropItem(boolean showItem) {
		this.mShowItem = showItem;
	}

	/**
	 * 设置不可见项的位置标记
	 * 
	 * @param position
	 */
	public void setInvisiblePosition(int position) {
		mInvisilePosition = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/***
		 * 在这里尽可能每次都进行实例化新的，这样在拖拽ListView的时候不会出现错乱.
		 * 具体原因不明，不过这样经过测试，目前没有发现错乱。虽说效率不高，但是做拖拽LisView足够了。
		 */
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.layout_self_stock_item_view, null);

		initItemView(position, convertView);// 初始化Item视图

		Model stock = dataList.get(position);
		TextView titleTv = (TextView) convertView.findViewById(R.id.txt_name);
		TextView txtCode = (TextView) convertView.findViewById(R.id.txt_code);
		TextView txtValue = (TextView) convertView.findViewById(R.id.txt_value);
		TextView txtIncrease = (TextView) convertView
				.findViewById(R.id.txt_increase);
		// 去掉市场代码
		txtCode.setText(stock.getMap().get("title").toString());

		txtIncrease.setText(stock.getMap().get("title").toString());
		titleTv.setText(stock.getMap().get("title").toString());

		lins.put(position, (MyLinerLay) convertView);
		if (isChanged) {// 判断是否发生了改变

			if (position == mInvisilePosition) {

				if (!mShowItem) {// 在拖拽过程不允许显示的状态下，设置Item内容隐藏

					// 因为item背景为白色，故而在这里要设置为全透明色防止有白色遮挡问题（向上拖拽）
					convertView.findViewById(R.id.drag_item_layout)
							.setBackgroundColor(0x0000000000);

					// 隐藏Item上面的内容
					int invis = View.INVISIBLE;
					convertView.findViewById(R.id.drag_item_image)
							.setVisibility(invis);
					convertView.findViewById(R.id.drag_item_close_layout)
							.setVisibility(invis);
					titleTv.setVisibility(invis);
					
					convertView.setVisibility(invis);
				}
			}

			if (mLastFlag != -1) {

				if (mLastFlag == 1) {

					if (position > mInvisilePosition) {
						Animation animation;
						animation = getFromSelfAnimation(0, -mHeight);
						convertView.startAnimation(animation);// 开启动画
					}

				} else if (mLastFlag == 0) {

					if (position < mInvisilePosition) {
						Animation animation;
						animation = getFromSelfAnimation(0, mHeight);
						convertView.startAnimation(animation);// 开启动画
					}
				}
			}
		}
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		// lins.clear();
	}

	/**
	 * 
	 * 初始化Item视图
	 * 
	 * @param convertView
	 */
	private void initItemView(final int position, final View convertView) {

		if (convertView != null) {
			// 设置对应的监听
			convertView.findViewById(R.id.drag_item_close_layout)
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// removeItem(position);// 删除指定的Item
							if (dataList != null && dataList.size() > position) {
								if (onItemRemoveListener != null) {
									onItemRemoveListener.onItemRemove(position,
											dataList.get(position));
								}
							}
						}
					});
		}
	}

	/**
	 * 删除指定的Item
	 * 
	 * @param pos
	 *            // 要删除的下标
	 */
	public void removeItem(int pos) {
		if (dataList != null && dataList.size() > pos) {
			// if (onItemRemoveListener != null) {
			// onItemRemoveListener.onItemRemove(pos, dataList.get(pos));
			// }
			dataList.remove(pos);
			lins.remove(pos);
			this.notifyDataSetChanged();// 刷新适配器
		}
	}

	/*	*//***
	 * 动态修改ListView的方位.
	 * 
	 * @param startPosition
	 *            点击移动的position
	 * @param endPosition
	 *            松开时候的position
	 */
	/*
	 * public void exchange1(int startPosition, int endPosition) { Object
	 * startObject = getItem(startPosition);
	 * 
	 * if (startPosition < endPosition) {// 向下拖拽 dataList.add(endPosition + 1,
	 * (String) startObject); dataList.remove(startPosition); } else {//
	 * 向上拖拽或者不动 dataList.add(endPosition, (String) startObject);
	 * dataList.remove(startPosition + 1); } isChanged = true; }
	 */

	/**
	 * 动态修改Item内容
	 * 
	 * @param startPosition
	 *            // 开始的位置
	 * @param endPosition
	 *            // 当前停留的位置
	 */
	public void exchangeCopy(int dowmPosition, int startPosition,
			int endPosition, boolean drop) {
		Object startObject = getCopyItem(startPosition);

		if (startPosition < endPosition) {// 向下拖拽
			copyList.add(endPosition + 1, (Model) startObject);
			copyList.remove(startPosition);
		} else {// 向上拖拽或者不动
			copyList.add(endPosition, (Model) startObject);
			copyList.remove(startPosition + 1);
		}
		isChanged = true;
		if (onItemsSwitchListener != null) {
			// onItemsSwitchListener.onItemsSwitch(startPosition, endPosition);

		}
	}

	public void dropItem(int startPosition, int endPosition) {
		if (onItemsSwitchListener != null) {
			onItemsSwitchListener.onItemsSwitch(startPosition, endPosition);
			Log.e("hhh", startPosition + ";;;;+" + endPosition);
		}
	}

	public interface OnItemRemoveListener {
		void onItemRemove(int postion, Model stock);
	}

	private OnItemRemoveListener onItemRemoveListener;
	private OnItemsSwitchListener onItemsSwitchListener;

	public interface OnItemsSwitchListener {
		void onItemsSwitch(int startPosition, int endPosition);
	}

	/**
	 * 删除监听
	 * 
	 * @param listener
	 */
	public void setOnItemRemoveListener(OnItemRemoveListener listener) {
		onItemRemoveListener = listener;
	}

	public void setOnItemsSwitchListener(OnItemsSwitchListener listener) {
		onItemsSwitchListener = listener;
	}

	/**
	 * 获取镜像(拖拽)Item项
	 * 
	 * @param position
	 * @return
	 */
	public Object getCopyItem(int position) {
		return copyList.get(position);
	}

	/**
	 * 添加拖动项
	 * 
	 * @param start
	 *            // 要进行添加的位置
	 * @param obj
	 */
	public void addDragItem(int start, Object obj) {
		dataList.remove(start);// 删除该项
		dataList.add(start, (Model) obj);// 添加删除项
	}

	public void copyList() {
		copyList.clear();// 清空集合
		for (Model str : dataList) {// 遍历数据源
			copyList.add(str);// 添加至拷贝集合
		}
	}

	public void pastList() {
		dataList.clear();// 清空集合
		for (Model str : copyList) {// 遍历拷贝集合
			dataList.add(str);// 添加至数据源集合
		}
	}

	/**
	 * 设置是否为相同方向拖动的标记
	 * 
	 * @param value
	 */
	public void setIsSameDragDirection(boolean value) {
		isSameDragDirection = value;
	}

	/**
	 * 设置拖动方向标记
	 * 
	 * @param flag
	 */
	public void setLastFlag(int flag) {
		mLastFlag = flag;
	}

	/**
	 * 设置高度
	 * 
	 * @param value
	 */
	public void setHeight(int value) {
		mHeight = value;
	}

	/**
	 * 设置当前拖动位置
	 * 
	 * @param position
	 */
	public void setCurrentDragPosition(int position) {
		mDragPosition = position;
	}

	/**
	 * 从自身出现的动画
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Animation getFromSelfAnimation(int x, int y) {
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, x,
				Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, y);// 平移动画
		translateAnimation
				.setInterpolator(new AccelerateDecelerateInterpolator());// 设置插值器
		translateAnimation.setFillAfter(true);
		translateAnimation.setDuration(1000);
		translateAnimation.setInterpolator(new AccelerateInterpolator());// 设置插值器
		return translateAnimation;
	}

}
