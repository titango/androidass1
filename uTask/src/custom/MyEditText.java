package custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {

	private Paint mPaint;
	int widthMsSize;
	int heightMsSize;

	// we need this constructor for LayoutInflater
	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(1);
		mPaint.setColor(Color.BLUE);

		System.out.println("constructor");
	}

	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		// Extract the Ms (MesaureSpec) parameters

		widthMsSize = MeasureSpec.getSize(widthMeasureSpec);

		heightMsSize = MeasureSpec.getSize(heightMeasureSpec);

		System.out.println("on measure");
		// Satisfy contract by calling setMeasuredDimension
		setMeasuredDimension(widthMsSize, heightMsSize);

	}

	protected void onDraw(Canvas canvas) {

		canvas.drawLine(5, heightMsSize - 10, widthMsSize - 5,
				heightMsSize - 10, mPaint); // draw underline

//		canvas.drawLine(8, heightMsSize - 10, 8, heightMsSize - 20, mPaint); // draw
//																				// left
//																				// corner
//
//		canvas.drawLine(widthMsSize - 8, heightMsSize - 10, widthMsSize - 8,
//				heightMsSize - 20, mPaint); // draw right corner

		super.onDraw(canvas);
	}
}