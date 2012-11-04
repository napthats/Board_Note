package com.napthats.android.everboard

import _root_.android.graphics.Canvas
import _root_.android.view.View
import _root_.android.content.Context
import _root_.android.util.AttributeSet
import _root_.android.os.Bundle
import _root_.android.content.res.TypedArray

class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) {
//class GameView(context: Context, attrs:AttributeSet, defStyle: Int) extends View(context, attrs, defStyle) {
//class GameView(context: Context) extends View(context) {
  //setMinimumWidth(200)
  //setMinimumHeight(10)

  override def onMeasure(width: Int, height: Int) {
    super.onMeasure(width, height)
    //setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight())
    //setMeasuredDimension(20000,100)
  }
  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)
  }
}
