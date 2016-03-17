package com.umframework.view.flat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.umframework.R;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class FlatButton extends Button implements Attributes.AttributeChangeListener
{
	private Attributes attributes;

	// default values of specific attributes
	private int bottom = 0;

	public FlatButton(Context context)
	{
		super(context);
		init(null);
	}

	public FlatButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(attrs);
	}

	public FlatButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(attrs);
	}

	@SuppressWarnings("deprecation")
	private void init(AttributeSet attrs)
	{
		// saving padding values for using them after setting background
		// drawable
		final int paddingTop = getPaddingTop();
		final int paddingRight = getPaddingRight();
		final int paddingLeft = getPaddingLeft();
		final int paddingBottom = getPaddingBottom();

		if (attributes == null)
			attributes = new Attributes(this, getResources());

		if (attrs != null)
		{
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.fl_FlatButton);

			// getting common attributes
			int customTheme = a.getResourceId(R.styleable.fl_FlatButton_fl_theme, Attributes.DEFAULT_THEME);
			attributes.setThemeSilent(customTheme, getResources());

			attributes.setFontFamily(a.getString(R.styleable.fl_FlatButton_fl_fontFamily));
			attributes.setFontWeight(a.getString(R.styleable.fl_FlatButton_fl_fontWeight));
			attributes.setFontExtension(a.getString(R.styleable.fl_FlatButton_fl_fontExtension));

			attributes.setTextAppearance(a.getInt(R.styleable.fl_FlatButton_fl_textAppearance, Attributes.DEFAULT_TEXT_APPEARANCE));
			attributes.setRadius(a.getDimensionPixelSize(R.styleable.fl_FlatButton_fl_cornerRadius, Attributes.DEFAULT_RADIUS_PX));

			// getting view specific attributes
			bottom = a.getDimensionPixelSize(R.styleable.fl_FlatButton_fl_blockButtonEffectHeight, bottom);

			a.recycle();
		}

		// creating normal state drawable
		ShapeDrawable normalFront = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		normalFront.getPaint().setColor(attributes.getColor(2));

		ShapeDrawable normalBack = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		normalBack.getPaint().setColor(attributes.getColor(1));

		normalBack.setPadding(0, 0, 0, bottom);

		Drawable[] d =
		{ normalBack, normalFront };
		LayerDrawable normal = new LayerDrawable(d);

		// creating pressed state drawable
		ShapeDrawable pressedFront = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		pressedFront.getPaint().setColor(attributes.getColor(1));

		ShapeDrawable pressedBack = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		pressedBack.getPaint().setColor(attributes.getColor(0));
		if (bottom != 0)
			pressedBack.setPadding(0, 0, 0, bottom / 2);

		Drawable[] d2 =
		{ pressedBack, pressedFront };
		LayerDrawable pressed = new LayerDrawable(d2);

		// creating disabled state drawable
		ShapeDrawable disabledFront = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		disabledFront.getPaint().setColor(attributes.getColor(3));

		ShapeDrawable disabledBack = new ShapeDrawable(new RoundRectShape(attributes.getOuterRadius(), null, null));
		disabledBack.getPaint().setColor(attributes.getColor(2));

		Drawable[] d3 =
		{ disabledBack, disabledFront };
		LayerDrawable disabled = new LayerDrawable(d3);

		StateListDrawable states = new StateListDrawable();

		states.addState(new int[]
		{ android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
		states.addState(new int[]
		{ android.R.attr.state_focused, android.R.attr.state_enabled }, pressed);
		states.addState(new int[]
		{ android.R.attr.state_enabled }, normal);
		states.addState(new int[]
		{ -android.R.attr.state_enabled }, disabled);

		setBackgroundDrawable(states);
		setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

		if (attributes.getTextAppearance() == 1)
			setTextColor(attributes.getColor(0));
		else if (attributes.getTextAppearance() == 2)
			setTextColor(attributes.getColor(3));
		else
			setTextColor(Color.WHITE);

		// check for IDE preview render
		if (!this.isInEditMode())
		{
			Typeface typeface = FlatUI.getFont(getContext(), attributes);
			if (typeface != null)
				setTypeface(typeface);
		}
	}

	public Attributes getAttributes()
	{
		return attributes;
	}

	@Override
	public void onThemeChange()
	{
		init(null);
	}
}
