package com.example.administrator.myapplication.drag;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

public class MyLinerLay extends LinearLayout {

	private RelativeLayout rLayout;
	private View view;
	private int viewWidth = -1;
	public float move;
	private float start;
	public boolean isRight = true;
	private int sreenWidth;
	private float x;

	private ImageView mDel;

	public MyLinerLay(Context context) {
		super(context);
		init(context);
	}

	public MyLinerLay(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Service.WINDOW_SERVICE);
		sreenWidth = manager.getDefaultDisplay().getWidth();
		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (viewWidth == -1) {
					viewWidth = view.getWidth();
				}
				return true;
			}
		});

	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		if (isInEditMode()) {
			return;
		}
		mDel = (ImageView) findViewById(R.id.img_del);

		mDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// scrollTo(viewWidth, 0);
				showDel();
				isRight = false;
			}
		});
	}

	private void showDel() {
		ValueAnimator animator = ValueAnimator.ofInt(0, viewWidth);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// TODO Auto-generated method stub
				scrollTo( (Integer) arg0.getAnimatedValue(),0);
			}
		});
		animator.setDuration(300);

		animator.start();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		rLayout = (RelativeLayout) getChildAt(0);
		LayoutParams params = (LayoutParams) rLayout.getLayoutParams();
		params.width = sreenWidth;
		rLayout.setLayoutParams(params);
		view = getChildAt(1);
	}

	public void move(MotionEvent event) {
		x = event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("TAG", "move--DOWN");
			start = event.getRawX();
			break;
		case MotionEvent.ACTION_UP:
			Log.i("TAG", "move--up");
			float movea = x - start;
			if (movea != 0 && movea < -viewWidth / 2f) {
				scrollTo(viewWidth, 0);
				move = -viewWidth;
				// new MyAsyncTask().execute(viewWidth,1);
				isRight = false;
			}

			else {

				// new MyAsyncTask().execute(0,2);
				scrollTo(0, 0);
				move = 0;
				isRight = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			move = x - start;
			if (isRight) {
				if (move <= 0 && move >= -viewWidth)
					scrollTo(-(int) move, 0);
			} else {
				if (move >= 0 && move <= viewWidth)
					scrollTo(viewWidth - (int) move, 0);
			}
			/*
			 * else if(move<-viewWidth) move=-viewWidth; else if(move>0) move=0;
			 */
			break;
		}
	}

	public void getBack() {
		if (!isRight)
			new MyAsyncTask().execute();

	}

	private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {

			int i = 0;
			for (i = viewWidth; i >= 0; i -= 6) {
				publishProgress(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (i > 0)
				publishProgress(i);
			isRight = true;

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			scrollTo(values[0], 0);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}

	}

}
